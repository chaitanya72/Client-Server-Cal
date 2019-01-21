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
public class Client1GUI extends Frame implements ActionListener , Runnable{
    Frame mainframe;
    Label textlabel;
    static TextArea ctext;
    static TextArea etext;
    Button gen;
    Button exit;
    public static TextField number1;
    Button execute;
    float localvalue=1;
    static Label poll_label;
    static volatile int exitcondition=1;
    ServerGUI num= new ServerGUI();
    Thread t;
    Readerwriter rw = new Readerwriter();
    public void ClientGUI(){ /*To Initilize the GUI */
        mainframe = new Frame("Client 1");
        mainframe.setSize(570,500);
        mainframe.setLayout(new FlowLayout());
        mainframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                //System.exit(0);
                dispose();
            }
        });
        gen = new Button("Start");
        exit = new Button("Exit");
        number1 = new TextField("1",30);
        execute= new Button("Calculate");
        textlabel = new Label();
        poll_label =new Label("");
        ctext = new TextArea();
        etext = new TextArea();
        gen.addActionListener(new Client1GUI());
        exit.addActionListener(new Client1GUI());
        execute.addActionListener(new Client1GUI());
        //mainframe.add(textlabel);
        mainframe.add(number1);
        mainframe.add(execute);
        //mainframe.add(poll_label);
        mainframe.add(ctext);
        mainframe.add(gen);
        mainframe.add(exit);
        mainframe.add(etext);
        mainframe.add(poll_label);
        mainframe.setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(e.getActionCommand());
        t = new Thread(new Client1GUI());
        if(e.getActionCommand()=="Start"){

                //************
                   // ServerGUI.count_user=ServerGUI.count_user +1;
                //************
                rw.increment();  //When the button is clicked,The number of clients connected in server is incremented
                exitcondition=1;



                t.start();



        }else{
            if(e.getActionCommand()=="Calculate"){
                //int result =EvaluateString.evaluate(number.getText());
                String str=number1.getText().toString();
                String str2=str;
                //System.out.println(str);
                //System.out.println(EvaluateString.evaluate("10 + 20"));
                //str=str + " + " + localvalue;
                //str=EvaluateString.parserString(str);
                localvalue=Float.parseFloat(logger.read_line("localvalue.txt"));
                //**********
                char[] tokens = str.toCharArray();
                String expression="";
                if(tokens[0]=='-'||tokens[0]=='+'||tokens[0]=='*'||tokens[0]=='/')
                {
                    str=localvalue+str;
                    System.out.println(str);
                }
                else
                {
                    str=localvalue+"+"+str;
                    str2="+"+str2;
                    System.out.println("The else is"+str);
                }
                //**********

                //str=localvalue+str;
                str=EvaluateString.removenegative(str);
                boolean correct_input=EvaluateString.removealphabets(str);

                if(correct_input) {
                    poll_label.setText("");
                    System.out.println("The correct_input is true");
                    logger.write(str2,"userlogoperations.txt"); //Uncomment this
                    float result=0;
                    try {
                        result = EvaluateString.evaluate(str);
                    }catch(Exception divide)
                    {
                        etext.append("Division By Zero Error\n");
                        poll_label.setText("Division by Zero");
                        number1.setText("Division by zero has occured");
                        //number1.setText(Float.toString(localvalue));
                        System.out.println(divide);
                        return;
                    }
                    //System.out.println(result);

                    String value_server = logger.read_line("localvalue.txt");

                    //number1.setText(value_server);
                    //localvalue=Integer.parseInt(value_server);
                    //localvalue = result;
                    logger.write(Float.toString(result), "userlog.txt"); //Change the file names
                    number1.setText(Float.toString(result));
                    str = str + "=" + result + "\n";
                    logger.write(str, "durableuserlog.txt"); //Change the file names
                    ctext.append(str);
                    //number1.setText("Work");
                }else{
                    etext.append("The Stirng entered is incorrect Please Enter a correct string\n");
                    poll_label.setText("The String entered is incorrect Please enter a correct string");
                    number1.setText("Incorrect Format");
                    //number1.setText(Float.toString(localvalue));
                }

            }
            else{

                //**************
                   // ServerGUI.count_user=ServerGUI.count_user - 1;
                //**************
            rw.decrement(); //When the button is clicked, The number of clients connected in server is decremented


            exitcondition=0;



            return;
            }
        }
    }
    public static void main(String[] args){
        Client1GUI cl = new Client1GUI();

        cl.ClientGUI();
    }

    //Multi Threading in GUI.This allows all the buttons to be active at the same time.
    @Override
    public void run() {
        Socket s = null;
        PrintStream ps = null;
        int max = 15;
        int min = 5;
        int count=1;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        try {
            s = new Socket("127.0.0.1", 5000);
            //ps = new PrintStream(s.getOutputStream());
            while (exitcondition==1) {//This condition is used to exit the loop
                if(exitcondition==0)  // To make sure that the loop exits if exitcondition is change in between
                {
                    break;
                }else
                {dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());

                if(count==1) {
                    long pid = ProcessHandle.current().pid();
                    dos.writeUTF(Long.toString(pid));
                    count++;
                }
                    String poll_text;
                    poll_text=dis.readUTF();
                    if(poll_text.equals("Poll"))
                    {
                        //System.out.println("Entered into poll");
                        etext.append("The Server is Polling.Please Wait\n");
                        poll_label.setText("The server is Polled");
                        String poll_complete;
                        poll_complete=dis.readUTF();
                        //execute.setEnabled(false);
                        if(poll_complete.equals("Polled"))
                        {
                            System.out.println("In the Polled");
                            String value_server=logger.read_line("localvalue.txt");

                            number1.setText(value_server);
                            localvalue=Float.parseFloat(value_server);
                            etext.append("The server has polled and the Local Value is "+value_server+"\n");
                           // execute.setEnabled(true);
                        }

                        // number1.disable();
                    }


//                Random rand = new Random();
//                int num = rand.nextInt((max - min) + 1) + min;
//                dos.writeUTF(Integer.toString(num));
//
//                String text;
//                text=dis.readUTF();
//                text=text + "\n";
//                String[] body = text.split("\n\n");  //Used to Split the String from http
//                ctext.append(body[1]);  //After split the second element will be the content
//
//                System.out.println(text);
                }
            }


        }catch(Exception e1){System.out.println("Caught Here"); System.out.println(e1);
        }
        try {
            dis.close();
            dos.close();
        }catch(Exception e2){ }



    }
}

//Citation
//Introducing Threads in Socket Programming in Java
//https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
//I have taken the Architecture of the program and few code code snippets