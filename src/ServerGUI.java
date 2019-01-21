//Chaitanya Krishna Lanka
//1001675459
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
public class ServerGUI extends Frame implements ActionListener, Runnable{
    Frame mainframe;
    Label textlabel;
    Button start;
    Button exit;
    Button poll;
    static TextArea ta;
    static TextArea ht;
    static Label connections;
    static TextField connect;
    static volatile int exitcondition=1;  //Used to exit the infinite while loops when the exit is clicked
    public static volatile boolean is_poll=false;
    public static volatile int count_poll=0;
    public static volatile int count_user=0;
    public static int number=0;

    Readerwriter rw = new Readerwriter(); //This object is used to increment or decrement the number of Client active

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand()=="Poll")
        {
                float sum=0;
               ServerGUI.is_poll=true;
               String local=logger.read_line("localvalue.txt");
               String[] exparray1;
               exparray1=logger.read("userlogoperations.txt");
               int count_a=0;
               String operations="";
               while(exparray1[count_a]!=null)
               {
                   operations=operations+exparray1[count_a];
                   count_a=count_a+1;
               }
               String tobeadded=local+""+operations;


               //logger.write_line(tobeadded,"userdataserver.txt");
                tobeadded=EvaluateString.removenegative(tobeadded);
                float answer=EvaluateString.evaluate(tobeadded);
                System.out.println("The answer is "+answer);
               // String local1=logger.read_line("localvalue.txt");
                //sum=Float.parseFloat(local)+ answer;
                logger.write_line(Float.toString(answer),"localvalue.txt");


               ht.append(local+""+operations+"\n");
        }
        Thread tserver= new Thread(new ServerGUI());  //To create a Thread For the Server GUI
        if(e.getActionCommand().equals("Start Server")){

            logger.clearLog("userlogoperations.txt");
            logger.set("1","localvalue.txt");
            rw.set();  //Intially seting the nuber of client to zero.This method is defined in Readerwriter
            tserver.start();
        }
        if(e.getActionCommand().equals("Exit")){
            exitcondition=0;  //This will set the exitcontition to 0.It will exit the while loops
        }
    }
    //This thread is Used For the multithreading of the GUI
    //All buttons can be active at the very Same time
    @Override
    public void run() {
        ta.append("Server Start\n");  //Appending the String to text box
        System.out.println("Button Working"); //For Debugging
        exitcondition=1;    //To set it to one if its not already
        ServerSocket ss =null;
        Socket s = null;
        try {
            ss = new ServerSocket(5000);  //Creating New Socket
            while(exitcondition==1){  //This loop keeps the server running until the stop button is clicked
                s=ss.accept();   //Server listens for the Client to connect

                DataInputStream dis = new DataInputStream(s.getInputStream());    //Data Streams to communicate between the Sockets
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); //Data Streams to communicate between the Sockets
                //Creating a ClientHanlerGUI Thread and passing the socket address,Data Streams,the address of textarea,the label and the connections
                Thread t = new ClientHandlerGUI(s,dis,dos,ta,ht,connections);
                t.start();












            }
        } catch(Exception e){ }

    }

    public static void main(String[] args)
    {
            ServerGUI server= new ServerGUI();
            server.serverGUI();  //Initilizing the ServerGUI
    }
    //This method is used to buld the GUI
    public void serverGUI(){
        mainframe = new Frame("Server");
        mainframe.setSize(500,500);
        mainframe.setLayout(new FlowLayout());
        mainframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                //System.exit(0);
                dispose();
            }
        });
        start = new Button("Start Server");
        exit = new Button("Exit");
        poll = new Button("Poll");
        textlabel = new Label();
        ta=new TextArea();
        ht=new TextArea();
        connect=new TextField();
        connections=new Label("The Number of Connected Clients are 0");
        start.addActionListener(new ServerGUI());
        exit.addActionListener(new ServerGUI());
        poll.addActionListener(new ServerGUI());
        mainframe.add(ta);
        mainframe.add(start);
        mainframe.add(exit);
        mainframe.add(poll);
        mainframe.add(ht);
        mainframe.add(connections);
        mainframe.setVisible(true);
    }

    /*public void increment()
    {
        number=number + 1;
    }*/

    /*
    public void decrement()
    {
        number = number - 1;
    }*/

}

//Citation
//Introducing Threads in Socket Programming in Java
//https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
//I have taken the Architecture of the program and few code code snippets
