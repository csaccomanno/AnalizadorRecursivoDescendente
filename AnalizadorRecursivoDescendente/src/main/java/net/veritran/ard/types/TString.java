package net.veritran.ard.types;

import net.veritran.ard.exception.ARDException;
import net.veritran.ard.utils.C;

public class TString extends AType implements Type {

	private String value;
	
	
	public TString(String value) {
		super();
		this.value=value;
	}

	
	
	@Override
	public Object getValue() {
		return value;
	}


	@Override
	public Type evaluateOperation(Type obj, String op) throws ARDException {
		
		//int iop = getOperatorType(op);
		String objString = (String)obj.getValue();
		
		int _result = this.value.compareTo( objString );

		switch( getOperatorType(op) ) {
		
			case C.I_DISTINTO:
				return new TBoolean( _result != 0 );
			case C.I_IGUAL:
				return new TBoolean( _result == 0 );
			case C.I_MAYOR:
				return new TBoolean( _result > 0 );
			case C.I_MAYOR_IGUAL:
				return new TBoolean( _result >= 0 );
			case C.I_MENOR:
				return new TBoolean( _result < 0 );
			case C.I_MENOR_IGUAL:
				return new TBoolean( _result <= 0 );
				
			default:
				throw new ARDException("TString: Se llamo al metodo evaluateOperation con un operador no reconocido. Value["+this.value+"]. Params {["+obj+"],["+op+"]}");

		}
	}



	@Override
	public String toString() {
		return "TString [value=" + value + "]";
	}



	@Override
	public int getObjectType() {
		return OBJ_TYPE_STRING;
	}

}
