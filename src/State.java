import java.util.*;

public class State implements Comparable<State>
{
	private int f, h, g;
	private State father;
	private int totalTime;
	private boolean torch;
	public ArrayList<Integer> WrongSide = new ArrayList<Integer>();
    public ArrayList<Integer> RightSide = new ArrayList<Integer>();
	public static int tt;// ayto prepei na dinetai apo ton xrhsth
	public State() 
	{
		this.f = 0;
		this.h = 0;
		this.g = 0;
		this.father = null;
		this.totalTime = 0;
		this.torch=false;
	}
	
	// copy constructor
	public State(State s)
	{
		this.f = s.f;
        this.h = s.h;
        this.g = s.g;
        this.father = s.father;
        this.totalTime = s.totalTime; 
		this.WrongSide=new ArrayList<>(s.WrongSide);
		this.RightSide=new ArrayList<>(s.RightSide);
		this.torch=s.torch;
		// create a state similar with s...
	}
	
	public int getF() 
	{
		return this.f;
	}
	
	public int getG() 
	{
		return this.g;
	}
	
	public int getH() 
	{
		return this.h;
	}
	
	public State getFather()
	{
		return this.father;
	}
	
	public void setF(int f)
	{
		this.f = f;
	}
	
	public void setG(int g)
	{
		this.g = g;
	}
	
	public void setH(int h)
	{
		this.h = h;
	}
	
	public void setFather(State f)
	{
		this.father = f;
	}
	
	public int getTotalTime() 
	{
		return this.totalTime;
	}
	
	public void setTotalTime(int time)
	{
		this.totalTime = time;
	}

	public void setTorch(boolean t){
		this.torch=t;
	}

	public boolean getTorch(){
		return this.torch;
	}
	
	public void evaluate() 
	{
		int max;
		if (WrongSide.size()==0){
			max=0;
		}else{
		max=WrongSide.get(0);
		for(int i=1; i<(this.WrongSide.size()); i++){
			if (max<this.WrongSide.get(i)) max=this.WrongSide.get(i);
		}
		}
		this.h=(max);// h eyretikh einai o megistos xronos pou exei apomeinei sthn wrong side
		this.f=(this.getTotalTime()+this.h);//calculate f=g+h
	}
	
	public void print() {
		if (RightSide.size()==0) System.out.print("\nNobody is on the right side of the bridge! ");
		else{
			System.out.print("\nTimes of the people that are on the right side: ");// an einai 0 na emfanizw None
			for(int i=0; i<RightSide.size(); i++){
              	System.out.print(RightSide.get(i)+" ");
			}
		}
		if (WrongSide.size()==0) System.out.print("\nNobody left on the wrong side of the bridge! ");
		else{
		System.out.print("\nTimes of the people that still need to pass the bridge: ");
		for(int i=0; i<WrongSide.size(); i++){
                System.out.print(WrongSide.get(i)+" ");
		}
		}
		System.out.println("\nTotal time until now: "+this.getTotalTime());
	}// emfanizw to state pou vriskomai dhladh poso atoma einai se poia pleura kai ton xrono mexri stigmhs
	
	public ArrayList<State> getChildren() {
		ArrayList<State> children = new ArrayList<>();
        State child;

		
			if (this.torch){// an einai lampa einia true vrisketai sthn swsth pleura kai prepei na epistrafei apo ena atomo
				for (int i=0 ; i<this.RightSide.size(); i++){
					child = new State(this);
					if (child.cross(i)){
				//if(heuristic > 0) 
						child.evaluate();
						child.setFather(this);
            			children.add(child);
					}
				 }// dhmiourgw oles tis pithanes katastaseis gia epistrofhs lampas kai prosthetw sthn lista childern 
		    
		}else{		
			for (int i=0 ; i<this.WrongSide.size()-1; i++){
                for(int j=i+1; j<this.WrongSide.size(); j++){
					
					child = new State(this);
					if (child.cross(i,j)){
						//if(heuristic > 0) 
						child.evaluate();
						child.setFather(this);
        	    		children.add(child);
					}

				}
		}// ama h lampa einai sthn lathos pleura dhmiourgw oles tis pithanes katastaseis diasxishs gia dyo atoma kai prosthetw sth children 
	}
		
		
		if (this.totalTime>tt){
			System.out.println("Couldn't found a solution with total time less or equal to "+ tt);
			
			System.exit(0);//*/
		}
		return children;
	}


	private boolean cross(int i){
		 
		
		
		this.g=(RightSide.get(i));
		this.totalTime=(this.g+this.getTotalTime());
		this.WrongSide.add(this.RightSide.get(i));
		this.RightSide.remove(i);
		
		this.setTorch(false);
		return true;
		

	}// dhmiourgw thn nea katastash twn pinakwn gia thn epistrofh ths lampas sthn wrong side gia to time sth thesh i kai metavalw thn thesh ths lampas

	private boolean cross(int i,int j){
		
		
		this.g=(Math.max(WrongSide.get(i),WrongSide.get(j)));
		
		this.setTotalTime(this.totalTime+ this.g);
		
					 
		this.RightSide.add(WrongSide.get(i));
        this.RightSide.add(WrongSide.get(j));
		this.WrongSide.remove(j);
	    this.WrongSide.remove(i);// prepei na metavalw kai ton xrono 
		this.setTorch(true);
		return true;			 

	}// omoiws gia ta dyo atoma pou diasxizoun thn gefyra gia na pane sthn swsth pleura
	
	public boolean isFinal() {
		if (this.totalTime>tt) {
			System.out.println("Couldn't find solution it time less than "+tt);
			System.exit(0);
		}
		
		return (WrongSide.size()==0 && this.getH()==0 );
	}// elegxw an h katastash einai telikh
	
	@Override
	public boolean equals(Object obj) 
	{
		if (this.WrongSide.size()!=((State) obj).WrongSide.size()) return false;
    	if (this.RightSide.size() != ((State) obj).RightSide.size()) return false;
    	if (this.totalTime != ((State) obj).totalTime) return false;
		for (int i=0; i<this.WrongSide.size(); i++){
			if (!((State) obj).WrongSide.contains(this.WrongSide.get(i)))
				return false;
			}
		for (int i = 0; i < this.RightSide.size(); i++) {
        	if (!((State) obj).RightSide.contains(this.RightSide.get(i))) {
            	return false;
        	}	
		
		}
		return true;
	}// elegxw an duo state einai idia
	
	@Override
    public int hashCode(){
		return this.totalTime+this.WrongSide.size()+this.f;
	}	
	@Override
    public int compareTo(State s)
    {
        return Integer.compare(this.f, s.getF()); // compare based on the F
    }
}