//Chaitanya Krishna Lanka
//1001675459
import java.util.Stack;
public class EvaluateString {
    public static float evaluate(String expression)
    {
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Float> values = new Stack<Float>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++)
        {
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // Current token is a number, push it to stack for numbers
            if ((tokens[i] >= '0' && tokens[i] <= '9')|| tokens[i]=='.')
            {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9')||tokens[i]=='.'))
                    sbuf.append(tokens[i++]);
                values.push(Float.parseFloat(sbuf.toString()));
            }

            // Current token is an opening brace, push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(tokens[i]);

                // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }

            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/')
            {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        // Top of 'values' contains result, return it
        return values.pop();
    }

    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public static float applyOp(char op, float b, float a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

    public static String parserString(String str)
    {
        char[] tokens = str.toCharArray();
        String experssion="";
        for(int i=0;i<(tokens.length);i++)
        {
            if(i<(tokens.length-1)) {
                if (tokens[i + 1] == '+' || tokens[i + 1] == '*' || tokens[i + 1] == '-' || tokens[i + 1] == '/' || tokens[i + 1] == '(' || tokens[i + 1] == ')') {
                    experssion = experssion + tokens[i] + " " + tokens[i + 1] + " ";
                    i = i + 1;
                } else {
                    experssion = experssion + tokens[i];
                }
            }
            if(i==(tokens.length-1))
            {
                experssion = experssion + tokens[i];
            }
        }
        System.out.println(experssion);
        return experssion;
    }

    public static String removenegative(String str)
    {
        char[] tokens = str.toCharArray();
        String expression="";
        if(tokens[0]=='-'||tokens[0]=='+'||tokens[0]=='*'||tokens[0]=='/')
        {
            expression="0" + str;
        }
        else{
            return EvaluateString.parserString(str);
        }
        System.out.println(expression);
        return EvaluateString.parserString(expression);
    }

    public static boolean removealphabets(String str)
    {
        char[] tokens = str.toCharArray();
        String expression="";
        for(int i=0;i<tokens.length;i++)
        {
            if(tokens[i]=='1' || tokens[i]=='2' ||tokens[i]=='3' ||tokens[i]=='4' || tokens[i]=='5'||tokens[i]=='6'||tokens[i]=='7'||tokens[i]=='8'||tokens[i]=='9'||tokens[i]=='0'||tokens[i]=='+'||tokens[i]=='-'||tokens[i]=='*'||tokens[i]=='/'||tokens[i]=='('||tokens[i]==')'|| tokens[i]==' '|| tokens[i]=='.')
            {
                //return true;
            }else
            {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args)
    {
        System.out.println(EvaluateString.evaluate("10 + 2 * 6"));
        System.out.println(EvaluateString.evaluate("100 * 2 + 12"));
        System.out.println(EvaluateString.evaluate("100 * ( 2 + 12 )"));
        System.out.println(EvaluateString.evaluate("100 * ( 2 + 12 ) / 14"));
    }
}
