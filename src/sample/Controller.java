package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;

public class Controller implements Initializable {

    @FXML private Label cnt;
    @FXML private TextArea input_box;
    @FXML ComboBox<String> cmb;
    @FXML private Hyperlink btn_dic;
//  @FXML private WebView webView;

    // 입력한 글자 수를 카테고리별로 최대 글자수와 함께 출력하고
    // 결과창으로 넘겨주기
    @FXML
    public void handleAction(ActionEvent event) {
        byte[] bytes = input_box.getText().getBytes();
        int byteLength = bytes.length;
        String strLength = Integer.toString(byteLength);

        cnt.setText(strLength);

        if(cmb.getValue() == "자율활동") {
            cnt.setText(strLength + " / 500자");
        } else if(cmb.getValue() == "행동특성 및 종합의견") {
            cnt.setText(strLength + " / 500자");
        } else if(cmb.getValue() == "진로활동") {
            cnt.setText(strLength + " / 700자");
        } else if(cmb.getValue() == "동아리") {
            cnt.setText(strLength + " / 500자");
        }
    }

    // .txt 파일로 저장
    @FXML
    private void saveFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TEXT files(*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(null);
        try {
            FileWriter writer = null;
            if(file != null) {
                writer = new FileWriter(file);
                writer.write(input_box.getText().replaceAll("\n", "\r\n"));
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // "사전찾기" 하이퍼링크 글씨를 누르면 네이버 사전 웹 페이지가 열림
    public void dicAction(ActionEvent event) {
        String addr = "https://ko.dict.naver.com/#/main";
        Process process = null;
        String[] cmd = new String[]{"rundll32", "url.dll", "FileProtocolHandler", addr};
        String str = null;
        try {
            process = new ProcessBuilder(cmd).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 화면 전환 및 웹 뷰
    public void checkAction(ActionEvent event) {
        Stage stage = (Stage) input_box.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Check.fxml"));
        try {
            Parent root = loader.load();
            CheckController controller = loader.getController();
            controller.setText(input_box.getText());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }

//        WebEngine webEngine = webView.getEngine();
//        webEngine.load("http://www.naver.com");
    }

    // initialize, 콤보박스 폰트 / 카테고리 설정 및 입출력 박스 가로 크기 고정
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmb.setStyle("-fx-font: 12px \"NanumSquare Regular\";");
        cmb.getItems().addAll("자율활동", "행동특성 및 종합의견", "진로활동", "동아리");
        input_box.setWrapText(true);
    }
}