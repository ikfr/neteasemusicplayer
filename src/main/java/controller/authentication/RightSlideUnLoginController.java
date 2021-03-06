package controller.authentication;

import application.SpringFXMLLoader;
import controller.main.CenterController;
import controller.main.MainController;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import util.StageUtils;
import util.WindowUtils;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
public class RightSlideUnLoginController {


    /**根容器BorderPane，见right-slide-unlogin.fxml文件*/
    @FXML
    private BorderPane borderPaneRoot;

    /**根容器BorderPane的右孩子容器显示内容的BorderPane*/
    @FXML
    private BorderPane borderPaneContent;

    /**右边显示”音乐”的可视化滑动容器*/
    private BorderPane visualBorderPane;

    /**注入窗体根容器（BorderPane）的中间容器的控制器*/
    @Resource
    CenterController centerController;

    /**注入Spring上下文工具类*/
    @Resource
    private ConfigurableApplicationContext applicationContext;

    /**注入窗体根容器（BorderPane）的控制类*/
    @Resource
    MainController mainController;

    private Stage loginOrRegisterStage;


    public BorderPane getBorderPaneRoot() {
        return borderPaneRoot;
    }

    public BorderPane getVisualBorderPane() {
        return visualBorderPane;
    }

    public Stage getLoginOrRegisterStage() {
        return loginOrRegisterStage;
    }

    public void initialize(){
        //宽高绑定
        borderPaneRoot.prefWidthProperty().bind(centerController.getBorderPane().widthProperty());
        borderPaneRoot.prefHeightProperty().bind(centerController.getBorderPane().heightProperty());

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.2),borderPaneRoot);
        borderPaneRoot.setTranslateX(310);
        translateTransition.setToX(0);
        translateTransition.play();

//        System.out.println(borderPaneContent.getMinWidth());
//        Timeline timeline = new Timeline();
//        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5),new KeyValue(borderPaneContent.minWidthProperty(),310)));
//        timeline.play();

        translateTransition.setOnFinished(event -> {
            ((Pane)borderPaneRoot.getCenter()).setOnMouseClicked(event1 -> {
                TranslateTransition translateTransitionOut = new TranslateTransition(Duration.seconds(0.2),borderPaneRoot);
                borderPaneRoot.setTranslateX(0);
                translateTransitionOut.setToX(310);
                translateTransitionOut.play();
                translateTransitionOut.setOnFinished(event2 -> {
                    centerController.getStackPane().getChildren().remove(1,centerController.getStackPane().getChildren().size());
                });
            });
        });
    }

    /**"登录"按钮的事件处理*/
    @FXML
    public void onClickedLoginButton(ActionEvent actionEvent) throws IOException {
        //创建登录stage部分
        Stage primaryStage = (Stage) borderPaneRoot.getScene().getWindow();
        FXMLLoader fxmlLoader = applicationContext.getBean(SpringFXMLLoader.class).getLoader("/fxml/authentication/navigate-login-register.fxml");
        loginOrRegisterStage = StageUtils.getStage(primaryStage,fxmlLoader.load());
        StageUtils.synchronizeCenter(primaryStage,loginOrRegisterStage);   //设置createMusicGroupStage对象居中到primaryStage
        WindowUtils.blockBorderPane(mainController.getBorderPane());         //设置borderPane不响应鼠标事件和改变透明度
        loginOrRegisterStage.show();

        if (centerController.getStackPane().getChildren().size() > 1){  //如果stackPane的容器大于1，移除掉
            centerController.getStackPane().getChildren().remove(1,centerController.getStackPane().getChildren().size());
        }
    }

    /**"关于"HBox的事件处理*/
    @FXML
    public void onClickedAbout(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getButton() == MouseButton.PRIMARY){
            FXMLLoader fxmlLoader = applicationContext.getBean(SpringFXMLLoader.class).getLoader("/fxml/authentication/right-about.fxml");
            visualBorderPane = (BorderPane) borderPaneRoot.getRight();
            borderPaneRoot.setRight(fxmlLoader.load());
        }
    }
}
