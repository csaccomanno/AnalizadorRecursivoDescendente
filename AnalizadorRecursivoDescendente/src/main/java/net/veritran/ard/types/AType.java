package net.veritran.ard.types;

import net.veritran.ard.exception.ARDException;
import net.veritran.ard.utils.C;

public abstract class AType implements Type {

	public static final int OBJ_TYPE_NUMBER  = 0x01;
	public static final int OBJ_TYPE_BOOLEAN = 0x02;
	public static final int OBJ_TYPE_STRING  = 0x03;
	
	
	protected int getOperatorType(String op) throws ARDException {
		
		if (op==null || op.length()==0)
			throw new ARDException("getOperatorType. Se llamo al metodo con el tipo de operacion Nula o Vacia. Ope: " + op);

		
		if ( op.equals(C.OPE_MAYOR) )
			return C.I_MAYOR;		
		else if ( op.equals(C.OPE_MAYOR_IGUAL) )
			return C.I_MAYOR_IGUAL;
		
		else if ( op.equals(C.OPE_MENOR) )
			return C.I_MENOR;		
		else if ( op.equals(C.OPE_MENOR_IGUAL) )
			return C.I_MENOR_IGUAL;
		
		else if ( op.equals(C.OPE_DISTINTO))
			return C.I_DISTINTO;		 
		else if ( op.equals(C.OPE_IGUAL))
			return C.I_IGUAL;
		
		else if ( op.equals(C.OPE_SUMA))
			return C.I_SUMA;
		else if ( op.equals(C.OPE_RESTA))
			return C.I_RESTA;
		else if ( op.equals(C.OPE_MULTIPLICACION))
			return C.I_MULTIPLICACION;
		else if ( op.equals(C.OPE_DIVISION))
			return C.I_DIVISION;
		else if ( op.equals(C.OPE_PORCENTAJE))
			return C.I_PORCENTAJE;
		else if ( op.equals(C.OPE_EXPONENTE))
			return C.I_EXPONENTE;
		
		else if ( op.equals(C.OPE_LOGIC_AND))
			return C.I_LOGIC_AND;
		else if ( op.equals(C.OPE_LOGIC_OR))
			return C.I_LOGIC_OR;
		else if ( op.equals(C.OPE_LOGIC_NOT))
			return C.I_LOGIC_NOT;

		throw new ARDException("getOperatorType. Se llamo al metodo con un tipo de operacion no soportada. Ope: " + op);
	}

	
}
