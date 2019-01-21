public class test {
    public void test1()
    {
        String[] exparray;
        logger.write("Hello This ","userlog.txt");
        exparray=logger.read("userlog.txt");
        int i=0;
        while(exparray[i]!=null) {
            System.out.println(exparray[i]);
            i=i+1;
        }
        logger.write("Nice","userlog.txt");
        logger.clearLog("userlog.txt");
        logger.clearLog("durableuserlog.txt");
    }

    public static void main(String[] args)
    {
        test t =new test();
        t.test1();
    }
}
