package net.veritran.ard.types;

import net.veritran.ard.exception.ARDException;
import net.veritran.ard.utils.C;

public class TNumber extends AType implements Type {
	
	private Double value = null; 

	
	
	public TNumber(double value) {
		super();
		this.value=value;
	}
	

	@Override
	public Type evaluateOperation(Type obj, String op) throws ARDException {
		
		int retval, iop = getOperatorType(op);
		

		switch(iop) {

		case C.I_MAYOR:
			retval = value.compareTo( (Double)(obj.getValue()) );
			return new TBoolean((retval>0)?true:false);
			
		case C.I_MAYOR_IGUAL:
			retval = value.compareTo( (Double)(obj.getValue()) );
			return new TBoolean((retval>=0)?true:false);
			
		case C.I_MENOR:
			retval = value.compareTo( (Double)(obj.getValue()) );
			return new TBoolean((retval<0)?true:false);
			
		case C.I_MENOR_IGUAL:
			retval = value.compareTo( (Double)(obj.getValue()) );
			return new TBoolean((retval<=0)?true:false);
			
		case C.I_IGUAL:
			return new TBoolean( value.equals(obj) );
			
		case C.I_DISTINTO:
			retval = value.compareTo( (Double)(obj.getValue()) );
			return new TBoolean((retval!=0)?true:false);
		
		// En este caso la cosntante de suma y resta, al tener los mismos simbolos
		// que los operadores unarios (+ y -), se debe discriminar en base al parametro obj
		// Ej: Si op == I_SUMA y obj == null  --->>> estamos en presencia de un oparador UNARIO.
		case C.I_SUMA:
			if (obj == null)	// Es un operador UNARIO POSITIVO (+)
				return new TNumber( value.doubleValue() );
			else 
				return new TNumber( value.doubleValue() + (Double)obj.getValue() );
			
		case C.I_RESTA:
			if (obj == null)	// Es un operador UNARIO NEGATIVO (-)
				return new TNumber( this.value*(-1) );
			else
				return new TNumber( value.doubleValue() - (Double)obj.getValue() );
			
		case C.I_MULTIPLICACION:
			return new TNumber( value.doubleValue() * (Double)obj.getValue() );
			
		case C.I_DIVISION:
			return new TNumber( value.doubleValue() / (Double)obj.getValue() );
			
		case C.I_PORCENTAJE:
			return new TNumber( value.doubleValue() % (Double)obj.getValue() );
			
		case C.I_EXPONENTE:
			return new TNumber( Math.pow(this.value, (Double)obj.getValue()) );
			
		/*case C.I_UNARIO_NEGATIVO:
			return new TNumber( this.value*(-1) );*/
			
		/*case C.I_UNARIO_POSITIVO:
			return this;*/
			
		case C.I_NEGACION:	// No es posible realizar ninguna operacion sobre este operador
			throw new ARDException("No es posible realizar ninguna operacion sobre el operador (negacion) ["+op+"]");
			
		default:
			throw new ARDException("TNumber: Se llamo al metodo evaluateOperation con un operador no reconocido. Value["+this.value+"]. Params {["+obj+"],["+op+"]}");
		}
	}



	@Override
	public Object getValue() {
		return this.value;
	}


	@Override
	public String toString() {
		return "TNumber [value=" + value + "]";
	}


	@Override
	public int getObjectType() {
		return OBJ_TYPE_NUMBER;
	}

}
