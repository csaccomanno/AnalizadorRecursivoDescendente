package net.veritran.ard.types;

import net.veritran.ard.exception.ARDException;
import net.veritran.ard.utils.C;

public class TBoolean extends AType implements Type {

	private boolean value = false;
	
	
	/**
	 * @param bool
	 */
	public TBoolean(boolean booleanValue) {
		super();
		this.value = booleanValue;
	}



	@Override
	public Object getValue() {
		return this.value;
	}


	@Override
	public Type evaluateOperation(Type obj, String op) throws ARDException {
		
		Boolean objBoolean = (Boolean)obj.getValue();

		switch ( getOperatorType(op) ) {
		
			case C.I_LOGIC_AND:
				return new TBoolean( this.value && objBoolean);
				
			case C.I_LOGIC_OR:
				return new TBoolean( this.value || objBoolean);
				
			case C.I_LOGIC_NOT:	
				return new TBoolean( ! this.value );
				
			default:
				throw new ARDException("TBoolean: Se llamo al metodo evaluateOperation con un operador no reconocido. Value["+this.value+"]. Params {["+obj+"],["+op+"]}");
		}
		
	}



	@Override
	public String toString() {
		return "TBoolean [value=" + value + "]";
	}



	@Override
	public int getObjectType() {
		return OBJ_TYPE_BOOLEAN;
	}
	
	

}
