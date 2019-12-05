package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Controller;
import util.WindowUtils;

import javax.annotation.Resource;

/**
 * @author super lollipop
 * @date 19-12-5
 */
@Controller
public class RegisterController {

    /**
     * "返回"Label图标
     */
    @FXML
    private Label labBackIcon;

    /**
     * "关闭"Label图标
     */
    @FXML
    private Label labCloseIcon;

    /**
     * "账号"的TextField组件
     */
    @FXML
    private TextField tfAccountID;

    /**
     * "清除"账号的图标
     */
    @FXML
    private Label labClearIcon;

    /**
     * "密码"的TextField组件
     */
    @FXML
    private PasswordField pfPassword;

    @FXML
    /**注册信息反馈的Label组件*/
    private Label labRegisterInformation;

    /**
     * "注册"按钮组件
     */
    @FXML
    private Button btnRegister;

    /**
     * 注入窗体根容器（BorderPane）的控制类
     */
    @Resource
    MainController mainController;

    /**
     * 注入window工具类
     */
    @Resource
    private WindowUtils windowUtils;

    /**
     * 注入“导航去登录、注册”面板的控制器Controller
     */
    @Resource
    private NavigateLoginOrRegisterController navigateLoginOrRegisterController;

    public void initialize() {
        labClearIcon.setVisible(false);  //初始化为不可见
        btnRegister.setMouseTransparent(true); //初始化不可以点击
        btnRegister.setOpacity(0.8);           //初始化不透明度为0.8

        Platform.runLater(() -> {
            btnRegister.requestFocus();         //"登录"按钮请求聚焦
        });

        //"清除"账号的图标的显示时机
        tfAccountID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!tfAccountID.getText().equals("")) {
                labClearIcon.setVisible(true);
                if (!pfPassword.getText().equals("")) {
                    btnRegister.setMouseTransparent(false);
                    btnRegister.setOpacity(1);
                }

            } else {
                labClearIcon.setVisible(false);
            }
        });
        //"清除"账号的图标的显示时机
        tfAccountID.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == true && !tfAccountID.getText().equals("")) {
                labClearIcon.setVisible(true);
            } else {
                labClearIcon.setVisible(false);
            }
        }));

        //设置"注册"按钮的可点击性
        pfPassword.textProperty().addListener(((observable1, oldValue1, newValue1) -> {
            if (!pfPassword.getText().equals("") && !tfAccountID.getText().equals("")) {
                btnRegister.setMouseTransparent(false);
                btnRegister.setOpacity(1);
            } else {
                btnRegister.setMouseTransparent(true);
                btnRegister.setOpacity(0.8);
            }
        }));

    }

    /**
     * "返回"Label图标鼠标点击事件处理
     */
    @FXML
    public void onClickedBackIcon(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {  //鼠标左击
            labBackIcon.getScene().setRoot(navigateLoginOrRegisterController.getNavigateLoginOrRegister());  //设置根容器为"登录、注册的导航容器"
        }
    }

    /**
     * "关闭"Label图标鼠标点击事件处理
     */
    @FXML
    public void onClickedCloseIcon(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {  //鼠标左击
            labCloseIcon.getScene().getWindow().hide();      //关闭窗口
            windowUtils.releaseBorderPane(mainController.getBorderPane());  //释放中间的面板，可以接受鼠标事件和改变透明度
        }

    }

    /**
     * "清除"账号的图标点击事件处理
     */
    @FXML
    public void onClickedClearIcon(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            tfAccountID.setText("");
            btnRegister.setMouseTransparent(true);
            btnRegister.setOpacity(0.8);
        }
    }

    /**
     * "注册"按钮点击事件处理
     */
    @FXML
    public void onClickedRegisterButton(ActionEvent actionEvent) {
    }
}
