package cop5556sp17;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import cop5556sp17.AST.Dec;


public class SymbolTable {
	
	HashMap<String,ArrayList<Symtable>> map=new HashMap<String,ArrayList<Symtable>>();
	Stack<Integer> scope_stack=new Stack<Integer>();
	int currentscope;
	int nextscope;
	
	
	//TODO  add fields

	/** 
	 * to be called when block entered
	 */
	public void enterScope(){
		//TODO:
		currentscope=nextscope++;
		scope_stack.push(currentscope);
		
		
	}
	
	
	/**
	 * leaves scope
	 */
	public void leaveScope(){
		//TODO: 
		scope_stack.pop();
		currentscope=scope_stack.peek();
		
	}
	
	public boolean insert(String ident, Dec dec){
		//TODO:  
		
		Symtable st=new Symtable(currentscope,dec);
		ArrayList<Symtable> list=new ArrayList<Symtable>();
		
		if(map.containsKey(ident))
		{
			list=map.get(ident);
			for(Symtable ele:list)
			{
				if(ele.scope==currentscope)
					return false;
			}
			
		}
		list.add(st);
		map.put(ident,list);
		return true;
	}
	
	public Dec lookup(String ident){
		//TODO: 
		if(!map.containsKey(ident))
			return null;
		
		Dec dec=null;
		ArrayList<Symtable> list = map.get(ident);
		if(list.size()==0)
			return null;
		
		for(int i=list.size()-1;i>=0;i--)
		{
			int scope = list.get(i).getScope();
			if(scope_stack.contains(scope))
			{
				dec = list.get(i).getDec();
				break;
			}
		}
		return dec;
	}
		
	public SymbolTable() {
		//TODO:  
		this.currentscope=0;
		this.nextscope=0;
		scope_stack.push(0);
	}


	@Override
	public String toString() {
		
		return map.values().toString();
	}
	
	class Symtable
	{
	int scope;
	Dec dec;
	
	public Symtable(int scope,Dec dec)
	{
		this.scope=scope;
		this.dec=dec;
	}
	
	public int getScope()
	{
		return this.scope;
	}
		
	public Dec getDec()
	{
		return this.dec;
	}
	}
	
	
	


}
