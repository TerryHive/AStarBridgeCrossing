import java.util.ArrayList;
import java.util.Collections;

public class Main
{
    public static void main(String[] args)
    { 
        State initialState=new State();
          if (args.length < 1) {
            System.out.println("Not valid infromation");
            return;
        }//elegxw an exei dwthei arithmos sto command line

        int dimension = Integer.parseInt(args[0]);

        
        if (args.length != dimension + 1) {
            System.out.println("Please provide the correct number of values for the array.");
            return;
        }// prepei o 1os arithmos na isoutai me to plhthos tou pinaka+1 



        for (int i = 0; i < dimension-1; i++) {
            try {
                initialState.WrongSide.add(Integer.parseInt(args[i + 1]));// vazw olous tous xronous sto wrong side tou 
                                                                          //initial state enw sthn rightside den uparxei kaneis
            } catch (NumberFormatException e) {
                System.out.println("Please provide valid integer values as command line arguments.");
                return;
            }
        }

        State.tt=Integer.parseInt(args[dimension]);// total time that are path shouldt'n pass 
        

        SpaceSearcher searcher = new SpaceSearcher();


        State terminalState = searcher.Astar(initialState);
         
        if(terminalState == null) System.out.println("Could not find a solution.");
       
        else
        {
			// print the path from beggining to start.
            State temp = terminalState; // begin from the end.
            ArrayList<State> path = new ArrayList<>();
			path.add(terminalState);
            while(temp.getFather() != null) // if father is null, then we are at the root.
            {
                path.add(temp.getFather());
                temp = temp.getFather();
            }
			// reverse the path and print.
            Collections.reverse(path);
            for(State item: path)
            {
                item.print();
            }
            System.out.println();
            
        }
    }
}