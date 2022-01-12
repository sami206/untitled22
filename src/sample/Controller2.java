package sample;

import com.jcraft.jsch.*;
import com.sun.xml.internal.bind.WhiteSpaceProcessor;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Controller2 {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private  TextField txtNP;

    @FXML
    private  TextField txtIPPC;

    @FXML
    private  TextField txtIPPR;
    @FXML
    private Button sabtn;
    @FXML
    private Button srbtn;
    @FXML
    private Button upbtn;
    @FXML
    private RadioButton hp_p;

    @FXML
    private RadioButton p_s;

    @FXML
    private RadioButton ep_p;

    @FXML
    private Label lab_RB;



    @FXML
    public Label lab;
    public void setName(String name) {
        lab.setText(name);
    }
    @FXML
    void srbtn(ActionEvent event) {
        ToggleGroup group = new ToggleGroup();
        hp_p.setToggleGroup(group);
        p_s.setToggleGroup(group);
        ep_p.setToggleGroup(group);
        RadioButton rb = (RadioButton) group.getSelectedToggle();
        if (rb == hp_p || rb == ep_p) {

            File fileXD = new File("xdodelivery.cfg");
            try {
                Scanner scanner = new Scanner(fileXD);
                String s1 = txtNP.getText();

                //now read the file line by line...
                int lineNum = 0;

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    //   String[] a=line.split(":",3);
                    lineNum++;
                    String[] a = line.split("\"", line.length());
                    for (String s : a) {
                        if (s.equals(s1)) {
                            System.out.println("ho hum, i found it on line " + lineNum);
                            String sline;
                            try (Stream<String> lines = Files.lines(Paths.get("xdodelivery.cfg"))) {
                                sline = lines.skip(lineNum + 3).findFirst().get();
                                System.out.println(sline);
                                String[] c = sline.split(":", line.length());
                                System.out.println(c[1].substring(2));
                                txtIPPR.setText(c[1].substring(2));


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }


                }
            } catch (FileNotFoundException e) {

                //handle this
            }


            File file = new File("sami_print.cfg");

            try {
                Scanner scanner = new Scanner(file);
                String s1 = txtNP.getText();

                //now read the file line by line...
                int lineNum = 0;

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    //   String[] a=line.split(":",3);
                    lineNum++;
                    String[] a = line.split(":", line.length());
                    if (a[0].equals(s1)) {
                        System.out.println("ho hum, i found it on line " + lineNum);
                        txtIPPC.setText(a[3]);


                    }

                }
            } catch (FileNotFoundException e) {

                //handle this
            }


        }else if (rb == p_s){
            File file = new File("sami_print.cfg");

            try {
                Scanner scanner = new Scanner(file);
                String s1 = txtNP.getText();

                //now read the file line by line...
                int lineNum = 0;

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    //   String[] a=line.split(":",3);
                    lineNum++;
                    String[] a = line.split(":", line.length());
                    if (a[0].equals(s1)) {
                        System.out.println("ho hum, i found it on line " + lineNum);
                        txtIPPC.setText(a[3]);
                        txtIPPR.setText("Its a Print Server ");

                    }

                }
            } catch (FileNotFoundException e) {

                //handle this
            }
        }else {
            lab_RB.setText("Please select the type of printer !!!!");

        }
    }
    @FXML
    void InTxt(InputMethodEvent event) {
        Controller controller=new Controller();
        if (controller.getbtnNum()==1){
            lab.setText("Add Printer");
        }else {
            lab.setText("Replace Printer");
        }

    }




    @FXML
    void savebtn(ActionEvent event) throws IOException {
        ToggleGroup group = new ToggleGroup();
        hp_p.setToggleGroup(group);
        p_s.setToggleGroup(group);
        ep_p.setToggleGroup(group);
        RadioButton rb = (RadioButton)group.getSelectedToggle();

        if(rb == hp_p){
            if(isExsit()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("this printer is already exist");
               // alert.setContentText("This is content text.");
                alert.showAndWait();

                txtIPPC.setText("this printer is already exist");

            }else {
                String extraLine = "<server name=\"" + txtNP.getText() + "\" type=\"ipp_printer\">\n" +
                        "         <filterType>PDF to PostScript</filterType>\n" +
                        "         <encType>none</encType>\n" +
                        "         <filter>oracle.xdo.delivery.filter.PDF2PSFilterImpl</filter>\n" +
                        "         <uri>http://" + txtIPPR.getText() + ":631/</uri>\n" +
                        "         <proxyAuthType>none</proxyAuthType>\n" +
                        "         <authType>none</authType>\n" +
                        "      </server>";
                addIntoXDO(extraLine);
                addIntoSA();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Success");
                alert.setHeaderText("The printer :" + txtNP.getText() + "has been added");
                // alert.setContentText("This is content text.");
                alert.showAndWait();
            }
        }else if(rb == p_s){
            if(isExsit()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("this printer is already exist");
                // alert.setContentText("This is content text.");
                alert.showAndWait();

                txtIPPC.setText("this printer is already exist");

            }else {
                String extraLine = "<server name=\"" + txtNP.getText() + "\" type=\"ipp_printer\">\n" +
                        "         <filterType>PDF to PostScript</filterType>\n" +
                        "         <encType>none</encType>\n" +
                        "         <filter>oracle.xdo.delivery.filter.PDF2PSFilterImpl</filter>\n" +
                        "         <uri>http://10.0.132.100/printers/" + txtNP.getText() + "/.printer</uri>\n" +
                        "         <proxyAuthType>none</proxyAuthType>\n" +
                        "         <authType>none</authType>\n" +
                        "      </server>";
                addIntoXDO(extraLine);
                addIntoSA();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Success");
                alert.setHeaderText("The printer :" + txtNP.getText() + "has been added");
                // alert.setContentText("This is content text.");
                alert.showAndWait();
            }
        }else if (rb == ep_p){
            if(isExsit()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("this printer is already exist");
                // alert.setContentText("This is content text.");
                alert.showAndWait();

                txtIPPC.setText("this printer is already exist");

            }else {
                String extraLine = "<server name=\"" + txtNP.getText() + "\" type=\"ipp_printer\">\n" +
                        "         <filterType>PDF to PostScript</filterType>\n" +
                        "         <encType>none</encType>\n" +
                        "         <filter>oracle.xdo.delivery.filter.PDF2PSFilterImpl</filter>\n" +
                        "         <uri>http://" + txtIPPR.getText() + ":631/ipp/print</uri>\n" +
                        "         <proxyAuthType>none</proxyAuthType>\n" +
                        "         <authType>none</authType>\n" +
                        "      </server>";
                addIntoXDO(extraLine);
                addIntoSA();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Success");
                alert.setHeaderText("The printer :" + txtNP.getText() + "has been added");
                // alert.setContentText("This is content text.");
                alert.showAndWait();
            }
        }else {
         //   lab_RB.setText("Please select the type of printer !!!!");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Please select the type of printer !");
            //alert.setContentText("This is content text.");
            alert.showAndWait();
        }


    }
    void  addIntoSA(){
        try {
            String user = "thin";
            String pass = "thin123";
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            String host = "10.100.251.40";

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



            if(isExsit()) {

                txtIPPC.setText("this printer is already exist");

            }else {
                String str =txtNP.getText()+":Y:550:"+txtIPPC.getText()+":"+txtNP.getText()+":NA:NA:NA:1";
                BufferedWriter writer = new BufferedWriter(new FileWriter("sami_print.cfg", true));
                writer.newLine();
                //writer.append(' ');
                writer.append(str);
                writer.close();
                channelSftp.put("C:\\Users\\sami-\\untitled2\\sami_print.cfg","/u/oracle/bank/gmx/sami_print.cfg");


            }
            System.out.println("Session connected: "+session.isConnected());
            channelSftp.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException | IOException e) {
            e.printStackTrace();
        }
    }
    void addIntoXDO(String txt){
        try{
            String user = "thin";
            String pass = "thin123";
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            String host = "10.100.251.146";

            JSch jSch = new JSch();
            Session session = jSch.getSession(user, host);
            session.setPassword(pass);
            session.setConfig(config);
            session.connect();

            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            // Vector fileList = channelSftp.ls("/u/oracle/bank/gmx");
            channelSftp.connect();
            channelSftp.cd("/u01/BEA/user_projects/domains/bifoundation_domain/bidata/components/bipublisher/repository/Admin/Delivery");
            channelSftp.get("/u01/BEA/user_projects/domains/bifoundation_domain/bidata/components/bipublisher/repository/Admin/Delivery/xdodelivery.cfg","xdodelivery.cfg");

            Path path = Paths.get("C:\\Users\\sami-\\untitled2\\xdodelivery.cfg");
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            int position =8;


            lines.add(position, txt);
            Files.write(path, lines, StandardCharsets.UTF_8);
            channelSftp.put("C:\\Users\\sami-\\untitled2\\xdodelivery.cfg","/u01/BEA/user_projects/domains/bifoundation_domain/bidata/components/bipublisher/repository/Admin/Delivery/xdodelivery.cfg");

            System.out.println("Session connected: "+session.isConnected());
            channelSftp.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException | IOException e) {
            e.printStackTrace();
        }
    }

    boolean isExsit(){

        File fileXD = new File("xdodelivery.cfg");
        String s1= txtNP.getText();
        try {
            Scanner scanner = new Scanner(fileXD);


            //now read the file line by line...
            int lineNum = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //   String[] a=line.split(":",3);
                lineNum++;
                String[] a = line.split("\"", line.length());
                for (String s : a) {
                    if (s.equals(s1)) {
                        System.out.println("ho hum, i found it on line " +lineNum);
                        txtIPPC.setText(a[3]);
                        return true;


                    }
                }


            }
        } catch (FileNotFoundException e) {

            //handle this
        }

        File file = new File("sami_print.cfg");

        try {
            Scanner scanner = new Scanner(file);
            s1=txtNP.getText();

            //now read the file line by line...
            int lineNum = 0;

            while (scanner.hasNextLine()) {
                String  line = scanner.nextLine();
                //   String[] a=line.split(":",3);
                lineNum++;
                String[] a=line.split(":",line.length());
                if(a[0].equals(s1)) {
                    System.out.println("ho hum, i found it on line " +lineNum);
                    txtIPPC.setText(a[3]);
                    return true;

                }

            }
        } catch(FileNotFoundException e) {

            //handle this
        }
        return false;

    }

    @FXML
    void upDate(ActionEvent event) {
        ToggleGroup group = new ToggleGroup();
        hp_p.setToggleGroup(group);
        p_s.setToggleGroup(group);
        ep_p.setToggleGroup(group);
        RadioButton rb = (RadioButton)group.getSelectedToggle();

        if(rb == hp_p) {
            replaceXDO();
            replaceSA ();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Updated");
            alert.setHeaderText("The printer has been updated.");
            //alert.setContentText("This is content text.");
            alert.showAndWait();
        }else if (rb == p_s){

            replaceSA ();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Updated");
            alert.setHeaderText("The printer has been updated.");
            //alert.setContentText("This is content text.");
            alert.showAndWait();

        }else if (rb == ep_p){
            replaceXDO();
            replaceSA ();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Updated");
            alert.setHeaderText("The printer has been updated.");
            //alert.setContentText("This is content text.");
            alert.showAndWait();
        }else {
            lab_RB.setText("Please select the type of printer !!!!");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Please select the type of printer !");
            //alert.setContentText("This is content text.");
            alert.showAndWait();
        }


    }
    void replaceXDO(){
        try {
            String user = "thin";
            String pass = "thin123";
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            String host = "10.100.251.146";

            JSch jSch = new JSch();
            Session session = jSch.getSession(user, host);
            session.setPassword(pass);
            session.setConfig(config);
            session.connect();

            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            // Vector fileList = channelSftp.ls("/u/oracle/bank/gmx");
            channelSftp.connect();
            channelSftp.cd("/u01/BEA/user_projects/domains/bifoundation_domain/bidata/components/bipublisher/repository/Admin/Delivery");
            channelSftp.get("/u01/BEA/user_projects/domains/bifoundation_domain/bidata/components/bipublisher/repository/Admin/Delivery/xdodelivery.cfg", "xdodelivery.cfg");

            File file = new File("xdodelivery.cfg");

            try {
                Scanner scanner = new Scanner(file);
                String s1 = txtNP.getText();

                //now read the file line by line...
                int lineNum = 0;

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    //   String[] a=line.split(":",3);
                    lineNum++;
                    String[] a = line.split("\"", line.length());
                    for (String s : a) {
                        if (s.equals(s1)) {
                            System.out.println("ho hum, i found it on line " + lineNum);
                            String sline;
                            try (Stream<String> lines = Files.lines(Paths.get("xdodelivery.cfg"))) {
                                sline = lines.skip(lineNum + 3).findFirst().get();
                                System.out.println(sline);
                                String[] c = sline.split(":", line.length());
                                System.out.println(c[1].substring(2));
                                Path path = Paths.get("xdodelivery.cfg");
                                Charset charset = StandardCharsets.UTF_8;
                                String content = new String(Files.readAllBytes(path), charset);
                                content = content.replaceAll(c[1].substring(2), txtIPPR.getText());
                                Files.write(path, content.getBytes(charset));
                                channelSftp.put("C:\\Users\\sami-\\untitled2\\xdodelivery.cfg", "/u01/BEA/user_projects/domains/bifoundation_domain/bidata/components/bipublisher/repository/Admin/Delivery/xdodelivery.cfg");
                            }

                        }
                    }


                }
            } catch (FileNotFoundException e) {

                //handle this
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Session connected: " + session.isConnected());
            channelSftp.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }
    void replaceSA (){
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
            File file = new File("sami_print.cfg");

            try {
                Scanner scanner = new Scanner(file);
                String s1=txtNP.getText();

                //now read the file line by line...
                int lineNum = 0;

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    //   String[] a=line.split(":",3);
                    lineNum++;
                    String[] a=line.split(":",line.length());
                    if(a[0].equals(s1)) {
                        System.out.println("ho hum, i found it on line " +lineNum);
                        Path path = Paths.get("sami_print.cfg");
                        Charset charset = StandardCharsets.UTF_8;
                        String content = new String(Files.readAllBytes(path), charset);
                        content = content.replaceAll(a[3], txtIPPC.getText());
                        Files.write(path, content.getBytes(charset));

                    }

                }
            } catch(FileNotFoundException e) {

                //handle this
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Session connected: "+session.isConnected());
            channelSftp.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void GBbtn(ActionEvent event) {
        try {

            //add you loading or delays - ;-)
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("sample.fxml")));
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }


    @FXML
    void initialize() {
        ToggleGroup group = new ToggleGroup();
        hp_p.setToggleGroup(group);
        p_s.setToggleGroup(group);
        ep_p.setToggleGroup(group);

        Controller controller=new Controller();
            //setName(String.valueOf(controller.getbtnNum()));
        if(controller.getbtnNum() == 1){
            setName("Add Printer");
            srbtn.setVisible(false);
            upbtn.setVisible(false);
        }else{
            sabtn.setVisible(false);
            setName("Replace Printer");
        }


        assert txtNP != null : "fx:id=\"txtNP\" was not injected: check your FXML file 'sample2.fxml'.";
        assert txtIPPC != null : "fx:id=\"txtIPPC\" was not injected: check your FXML file 'sample2.fxml'.";
        assert txtIPPR != null : "fx:id=\"txtIPPR\" was not injected: check your FXML file 'sample2.fxml'.";

    }
}
 /*try {

         //add you loading or delays - ;-)
         Node node = (Node) event.getSource();
         Stage stage = (Stage) node.getScene().getWindow();
         //stage.setMaximized(true);
         stage.close();
         Scene scene = new Scene(FXMLLoader.load(getClass().getResource("sample.fxml")));
         stage.setScene(scene);
         stage.show();

         } catch (IOException ex) {
         System.err.println(ex.getMessage());
         }*/