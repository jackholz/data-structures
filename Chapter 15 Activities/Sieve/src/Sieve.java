import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Set;


/**
 * A program that implements the sieve of Eratosthenes.
*/
public class Sieve
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Compute primes up to which integer?");
        int n = in.nextInt();

        // Your work goes here
        boolean billy=true;
        ArrayList<Integer> silly = new ArrayList<Integer>();
        silly.add(2);
        //Scanner scan = new Scanner(primes);
        for(int i=3;i<=n;i++ )
        {
            billy=false;
            for(int j=0;j<silly.size();j++)
            {
                if (i%silly.get(j)==0)
                {
                    billy=true;
                }
            }
            if (!billy)
            {
                silly.add(i);
            }
            
        }
        System.out.println("Here is a list of all prime integers up to the selected value "+silly);









    }
}
