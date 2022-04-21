package net.veritran.ard.types;

import net.veritran.ard.exception.ARDException;

public interface Type {
	
	public Type evaluateOperation(Type obj, String op) throws ARDException;
	
	public Object getValue();
	
	public int getObjectType();
	

	
}
