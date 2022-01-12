package sample;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

public class Controller {


    @FXML
    private Button AddP;

    @FXML
    private Button ReP;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public static int btnNum;



    public int getbtnNum() {
        return this.btnNum;
    }
    public void setNum( int NewbtnNum) {
        btnNum = NewbtnNum;
    }





    @FXML
    void addPrinter(ActionEvent event) {

        setNum(1);
        System.out.println(getbtnNum());
        try {

            //add you loading or delays - ;-)
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("sample2.fxml")));
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }



    }

    @FXML
    void rePrinter(ActionEvent event) {
        setNum(2);
        System.out.println(btnNum);
        try {

            //add you loading or delays - ;-)
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("sample2.fxml")));
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }
    void getSA(){
        try {
            String user = "thin";
            String pass = "thin123";
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            String host = "10.100.251.30";

            JSch jSch = new JSch();
            Session session = jSch.getSession(user, host);
            session.setPassword(pass);
            session.setConfig(config);
            session.connect();

            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            // Vector fileList = channelSftp.ls("/u/oracle/bank/gmx");
            channelSftp.connect();
            channelSftp.cd("/u/oracle/bank/gmx");
            channelSftp.get("/u/oracle/bank/gmx/sami_print.cfg","sami_print.cfg");
            System.out.println("Session connected: "+session.isConnected());
            channelSftp.disconnect();
            session.disconnect();


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void getXD(){
        try {
            String user = "thin";
            String pass = "thin123";
            Properties config = new Properties();
            config.put("StrictHostKeyChecking","no");
            String host = "10.100.251.146";
            JSch jSch = new JSch();
            Session session = jSch.getSession(user,host);
            session.setPassword(pass);
            session.setConfig(config);
            session.connect();
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.cd("/u01/BEA/user_projects/domains/bifoundation_domain/bidata/components/bipublisher/repository/Admin/Delivery");
            channelSftp.get("/u01/BEA/user_projects/domains/bifoundation_domain/bidata/components/bipublisher/repository/Admin/Delivery/xdodelivery.cfg","xdodelivery.cfg");
            System.out.println("Session connected: "+session.isConnected());
            channelSftp.disconnect();
            session.disconnect();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        getSA();
        getXD();


    }
}
