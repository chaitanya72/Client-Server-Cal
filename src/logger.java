//Chaitanya Krishna Lanka
//1001675459
import java.awt.*;
import java.net.*;
import java.io.*;
import java.lang.*;

public class logger {
    //Thsi Class Contains Methods to Read and Write from File
    //This Class Is Used to Display the number of clients connected to Server at Real Time



    //BufferedReader br = null;
    //BufferedWriter b=null;

    //To Read The Number in the file
    //Returns The array of experssions in the log.
    public synchronized static String[] read(String filename)
    {   String expression="";
        String[] exparray=new String[100];
        BufferedReader br = null;
        BufferedWriter b=null;

        int i=0;
        try {
            br = new BufferedReader(new FileReader(filename));
            while((expression=br.readLine())!=null) {
               // expression = br.readLine();
                //System.out.println(expression);
                exparray[i]=expression;
                i=i+1;

            }
            //System.out.println(number);
        }catch(Exception e){ }
        return exparray;
    }

    //To Increment the number in a file
    /*public void increment()
    {
        try {
            String number;
            number = read();
            int num = Integer.parseInt(number);
            num = num + 1;
            write(Integer.toString(num));
        }catch(Exception e){ }
    }
    //To decrement the number in a file
    public void decrement(){
        try {
            String number;
            number = read();
            int num = Integer.parseInt(number);
            num = num - 1;
            write(Integer.toString(num));
        }catch(Exception e){ }
    }*/

    //To write into a file
    public synchronized static void write(String experssion, String filename)
    {
        BufferedReader br = null;
        BufferedWriter b=null;

        try {
            b = new BufferedWriter(new FileWriter(filename,true));
            //number= br.readLine();
            //b.append(experssion);
            b.write(experssion);
            b.newLine();
            //b.append(experssion);
            //System.out.println(number);
        }catch(Exception e){ }
        finally {
            try {
                b.close();
            }catch (Exception e){ }
        }
    }

    //This is used to set the value in the file to zero when the server starts
    public synchronized static void set(String number,String filename)
    {
        BufferedReader br = null;
        BufferedWriter b=null;

        try {
            b = new BufferedWriter(new FileWriter(filename));
            //number= br.readLine();
            b.write(number);
            //System.out.println(number);
        }catch(Exception e){ }
        finally {
            try {
                b.close();
            }catch (Exception e){ }
        }
    }

    public synchronized static void clearLog(String filename)
    {
        BufferedReader br = null;
        BufferedWriter b=null;

        try {
            b = new BufferedWriter(new FileWriter(filename));
            //number= br.readLine();
            b.write("");
            //System.out.println(number);
        }catch(Exception e){ }
        finally {
            try {
                b.close();
            }catch (Exception e){ }
        }
    }

    public synchronized static void write_line(String number,String filename)
    {
        BufferedReader br = null;
        BufferedWriter b=null;
        try {
            b = new BufferedWriter(new FileWriter(filename));
            //number= br.readLine();
            b.write(number);
            //System.out.println(number);
        }catch(Exception e){ }
        finally {
            try {
                b.close();
            }catch (Exception e){ }
        }
    }

    public synchronized static String read_line(String filename)
    {
        BufferedReader br = null;
        BufferedWriter b=null;
        String number="";
        try {
            br = new BufferedReader(new FileReader("localvalue.txt"));
            number= br.readLine();
            //System.out.println(number);
        }catch(Exception e){ }
        return number;
    }

    public synchronized static void append_operation(TextArea ht)
    {
        String[] exparray=read("userlogoperations.txt");
        int i=0;
        while(exparray[i]!=null)
        {
            if(exparray[i+1]!=null)
            {
            ht.append("("+exparray[i]+")"+ " + ");
            }else{
                ht.append("("+exparray[i]+")");
            }
            i=i+1;
        }
        ht.append("\n");

    }



}







