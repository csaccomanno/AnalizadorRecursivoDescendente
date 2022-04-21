package net.veritran.ard;

import java.util.Arrays;

import net.veritran.ard.exception.ARDException;
import net.veritran.ard.types.AType;
import net.veritran.ard.types.TBoolean;
import net.veritran.ard.types.TNumber;
import net.veritran.ard.types.TString;
import net.veritran.ard.types.Type;
import net.veritran.ard.utils.C;

public class ASRD {
	
	private char [] prog;	
	private String  token;
	private int     tipoTok;
	private int     pos=0;
	private int     length;
	

	private static final int _TOKEN_TIPO_NUMERO      = 0x01;
	private static final int _TOKEN_TIPO_CADENA      = 0x02;
	private static final int _TOKEN_TIPO_BOOLEANO    = 0x03;	
	private static final int _TOKEN_TIPO_DELIMITADOR = 0x04;
	private static final int _TOKEN_TIPO_OPERADOR    = 0x05;
	
	
	

	/*
	 * Analizador Recursivo Descendente
	 * 
	 * Precedencia de operadores:
	 * 
		Mayor:
                ( ) 
                + - (Unarios)
                ^
                * / %
                +  -
                <  <=  >  >=
		Menor:
                == !=
                ||  &&
	 */
	
	/**
	 * 
	 * @param exp
	 * @return
	 * @throws ARDException
	 */
	public static Object _eval(String exp) {
		
		try {
			
			ASRD p = new ASRD();
			
			return p.eval(exp);
			
		} catch (Exception ex) {
			return null;
		}
	}

	

	/**
	 * 
	 * @param exp	Expresion a evaluar
	 * @return	TRUE o FALSE (boolean)
	 */
	public static boolean evalBoolean(String exp) {
		
		try {
			
			Type r = (Type )ASRD._eval( exp );
			
			switch ( r.getObjectType() ) {
			
				case AType.OBJ_TYPE_NUMBER:
					
					if ( (Double)r.getValue() != 0 )
						return true;
					
					break;
					
				case AType.OBJ_TYPE_BOOLEAN:
					
					return (Boolean)r.getValue();
					
				case AType.OBJ_TYPE_STRING:
					
					String s = (String)r.getValue();
					if (s!=null && s.length()>0)
						return true;
					
					break;
			}
			
			return false;
			
		} catch ( Exception ex ) {
			return false;
		}
	}
	
	
	
	/**
	 * 
	 * @param exp
	 * @return
	 */
	public static double evalNumber(String exp) {
		
		try {
			
			Type r = (Type )ASRD._eval( exp );
			
			switch ( r.getObjectType() ) {
			
				case AType.OBJ_TYPE_NUMBER:
					return (Double)r.getValue();
					
				case AType.OBJ_TYPE_BOOLEAN:
					return ( (Boolean)r.getValue() ) ? 1: 0;
					
				case AType.OBJ_TYPE_STRING:					
					return Double.parseDouble( (String)r.getValue() );
			}
			
			return 0;
			
		} catch ( Exception ex ) {
			return -1;
		}
	}
	
	
	
	/**
	 * 
	 * @param exp
	 * @return
	 */
	public static String evalString(String exp) {
		
		try {
			
			Type r = (Type )ASRD._eval( exp );
			
			switch ( r.getObjectType() ) {
			
				case AType.OBJ_TYPE_NUMBER:
					return ((Double)r.getValue()).toString();
					
				case AType.OBJ_TYPE_BOOLEAN:
					return ((Boolean)r.getValue()).toString();
					
				case AType.OBJ_TYPE_STRING:					
					return (String)r.getValue();
			}
			
			return null;
			
		} catch ( Exception ex ) {
			return null;
		}
	}
	
	
	/**
	 * 
	 * 
	 * @param exp
	 * @return
	 * @throws ARDException
	 */
	public Object eval(String exp) throws ARDException {
		
		Type result = null;
		
		prog = exp.toCharArray();
		length = prog.length;
		
		obtenerToken();
		
		if (token == null || token.length() == 0)
			return null;
		
		result = evaluateExp_02();
		
		return result;

	}
	
	
	
	
	
	
	
	
	/**
	 * Evalua un   ||  &&
	 * 
	 * @param result
	 * @return
	 * @throws ARDException
	 */
	private Type evaluateExp_02() throws ARDException {
		
		Type rresult=null, temp=null;
		
		rresult = evaluateExp_03();

		while ( token.equals("||") || token.equals("&&")) {
			
			String t = new String(token);
			
			obtenerToken();
			
			temp = evaluateExp_03();

			rresult = rresult.evaluateOperation(temp, t);

		}
		
		return rresult;
	}

	

	/**
	 * Evalua un == !=
	 * @param result
	 * @return
	 * @throws ARDException
	 */
	private Type evaluateExp_03() throws ARDException {
		
		Type rresult = null, temp=null;
		
		
		rresult = evaluateExp_04();
		
		if ( token.equals("==") || token.equals("!=") ) {
			
			String t = new String(token);
			
			obtenerToken();
			temp = evaluateExp_04();

			return rresult.evaluateOperation(temp, t);
		}
		
		return rresult;
	}
	
	
	/**
	 * Evalua:  <   >   >=   <=
	 * 
	 * @param result
	 * @return
	 * @throws ARDException 
	 */
	private Type evaluateExp_04() throws ARDException {
		
		Type rresult = null, temp=null;
		
		
		rresult = evaluateExp_06();
		
		if (token.equals("<") || token.equals("<=") || token.equals(">") || token.equals(">=") ) {
			
			String t = new String(token);
			
			obtenerToken();
			temp = evaluateExp_06();

			return rresult.evaluateOperation(temp, t);
		}
		
		return rresult;
	}
	
	
	/**
	 * Suma o resta dos TERMINOS
	 * @throws ARDException 
	 * 
	 */
	private Type evaluateExp_06() throws ARDException {
		
		Type temp=null, rresult=null;
		String op;
		
		rresult = evaluateExp_08();
		
		while ( (op=token).equals(C.TER_SUMA) || op.equals(C.TER_RESTA) ) {
			
			obtenerToken();
			temp = evaluateExp_08();
			
			rresult = rresult.evaluateOperation(temp, op);
		}
		
		return rresult;
	}
	
	
	/**
	 * Multiplicar o dividir dos factores
	 * 
	 * @param result
	 * @return
	 * @throws ARDException 
	 */
	private Type evaluateExp_08() throws ARDException {
		
		Type rresult=null, temp=null;
		String op;
		
		rresult = evaluateExp_10();
		
		while ( (op=token).equals(C.FAC_MULTIPLICACION) || op.equals(C.FAC_DIVISION) || op.equals(C.FAC_PORCENTAJE) ) {
			
			obtenerToken();
			temp = evaluateExp_10();
			rresult = rresult.evaluateOperation(temp, op);
		}
		
		return rresult;
	}

	
	
	/**
	 * Procesar un Exponente
	 * 
	 * @param result
	 * @return
	 * @throws ARDException 
	 */
	private Type evaluateExp_10() throws ARDException {
		Type rresult=null, temp=null;
		String operator;
		
		rresult = evaluateExp_12();
		
		if ( token.equals(C.FAC_EXPONENTE) ) {
			
			operator = new String(token);			
			obtenerToken();			
			temp=evaluateExp_10();			
			rresult = rresult.evaluateOperation(temp, operator);
		}
		
		return rresult;
	}
	
	
	
	
	/**
	 * Evalua un + o - UNARIO
	 * @param result
	 * @return
	 * @throws ARDException 
	 */
	private Type evaluateExp_12() throws ARDException {
		String op="";
		Type rresult=null;
		
		if ( (tipoTok==_TOKEN_TIPO_OPERADOR) && token.equals(C.UNARIO_POSITIVO) || token.equals(C.UNARIO_NEGATIVO) ) {
			op = token;
			obtenerToken();
		}
		
		rresult = evaluateExp_14();
		
		if ( op.equals(C.UNARIO_NEGATIVO) ) {
			
			rresult = rresult.evaluateOperation(null, op);
		}
		
		return rresult;
	}
	
	
	
	
	
	/**
	 * Procesa una expresion entre parentesis
	 * 
	 * @param result
	 * @return
	 * @throws ARDException 
	 */
	private Type evaluateExp_14() throws ARDException {
		
		Type rresult=null;
		
		if (token.equals(C.PARENTESIS_IZQUIERDO)) {
			
			obtenerToken();
			
			rresult = evaluateExp_02();
			
			if ( ! token.equals(C.PARENTESIS_DERECHO))
				throw new ARDException("eval_exp6: Error en expresion. Falta parentesis derecho. Token:  " + token + ". tipoTok: " + tipoTok);
			
			obtenerToken();
			
		} else {
			rresult = atomo();
		}
		
		
		return rresult;
	}
	
	
	
	
	private Type atomo() throws ARDException {
		
		Type rresult=null;
		
		switch(tipoTok) {
		
		case _TOKEN_TIPO_NUMERO:
			
			rresult = new TNumber( Double.valueOf(token) );
			obtenerToken();
			break;
			
		case _TOKEN_TIPO_CADENA:
			
			rresult = new TString(token);
			obtenerToken();
			break;
			
		case _TOKEN_TIPO_BOOLEANO:
			
			rresult = new TBoolean(Boolean.parseBoolean(token));
			obtenerToken();
			break;
			
		default:
			throw new ARDException("atomo: Se llamo a la funcion con un tipo inexistente. Token:  [" + token + "] tipoTok: " + tipoTok);
		}
		
		return rresult;
	}
	
	
	
	

	
	
	
	

	
	/**
	 * 
	 * Obtiene el siguiente token y lo asigna a global "token"
	 * Incrementa el valor de pos
	 * 
	 */
	private void obtenerToken() {
		token="";
		tipoTok=0;
		
		
		while (  !isEOF() && isSpace(prog[pos]) ) ++pos;
		
		if ( isEOF() ) return;
		
		
		
		if ( isOperator() ) {				//	_TOKEN_TIPO_OPERADOR
			
			tipoTok=_TOKEN_TIPO_OPERADOR;
			
			do {
				token = token + prog[pos++];
				
				// Limitamos el tamano de los operadores a 2 caracteres!!
			} while ( ! isEOF() && token.length() < 2 && isLargeOperator() );	// Ya incrementamos pos en la asignacion; Por eso el "-1"			
			
			
		} else if ( isString(prog[pos]) ) {	// _TOKEN_TIPO_CADENA
			
			while ( prog[++pos] != '\'' ) 
				token = token + prog[pos];
			
			tipoTok = _TOKEN_TIPO_CADENA;
			pos++;
			
			
		} else if ( isBoolean(prog[pos]) ) {// _TOKEN_TIPO_BOOLEANO
			
			while ( ! isDelim() ) 
				token = token + prog[pos++];
			
			tipoTok = _TOKEN_TIPO_BOOLEANO;
			
			
		} else if ( isDigit(prog[pos]) ) {	//	_TOKEN_TIPO_NUMERO
			
			while ( ! isDelim() ) 
				token = token + prog[pos++];
			
			tipoTok = _TOKEN_TIPO_NUMERO;
		}
	}
	
	

	/**
	 * Este metodo entiende que si el token actual (param c) comienza  
	 * con T/t(True) o F/f(False), entonces sera un objeto Booleano (TBoolean).
	 * 
	 * @param c
	 * @return
	 */
	private boolean isBoolean( char c ) {
		
		switch ( c ) {
			case 't':	case 'T':
			case 'f':	case 'F':
				return true;
		}
		
		return false;
	}

	
	/**
	 * Este metodo entiende que si el token actual (param c) comienza  
	 * con comillas simples ('), entonces sera un objeto Cadena (TString).
	 * 
	 * @param c
	 * @return
	 */
	private boolean isString( char c) {
		return (c=='\'')? true : false;
	}
	
	
	
	private boolean isDigit( char c ) {
		return Character.isDigit(c);
	}
	

	
	
	private boolean isLargeOperator() {
		
		if (pos==0) return false;
		
		// Evaluamos el caracter anterior para determinar 
		// si es un operador de caracter doble
		switch ( prog[pos-1] ) {
			case '=':	case '&':	case '|':	case '!':	case '<':	case '>':
				// Evaluamos si el caracter de la posicion actual 
				// es un operador
				switch ( prog[pos] ) {
					case '=':	case '&':	case '|':
						return true;
				}
		}
		
		return false;
	}
	
	
	private boolean isOperator() {
		
		if ( isEOF() ) return false;
		
		switch ( prog[pos] ) {
			case '+':	case '-':	case '*':	case '/':
			case '%':	case '=':	case '(':	case ')':
			case '^':	case '>':	case '<':	case '!':
			case '&':	case '|':
				return true;
			default:
				return false;
		}
	}
	
	private boolean isDelim() {
		
		if ( isEOF() ) return true;
		
		switch ( prog[pos] ) {
			case ' ':	case '+':	case '-':	case '/':	
			case '*':	case '%':	case '^':	case '=':
			case '(':	case ')':	case 9:		case '\n':
			case '\r':	case 0:		case '>':	case '<':
			case '!':	case '&':	case '|':
				return true;
			default:
				return false;
		}
	}
	
	
	/**
	 * 
	 * @return Devuelve True si se alcanzo el final de "prog" (Cadena a analizar)
	 */
	private boolean isEOF() {
		return pos>=length;
	}
	

	
	private boolean isSpace( char c ) {
		
		return ( c == ' ' )? true : false;
	}
	
	
	
	
	@Override
	public String toString() {
		String t, p;
		
		if ( isEOF() )
			p = "EOF";
		else p = "" + prog[pos];
		
		switch (tipoTok) {
			case _TOKEN_TIPO_DELIMITADOR:
				t = "DELIMITADOR(" + _TOKEN_TIPO_DELIMITADOR + ")";
				break;
			case _TOKEN_TIPO_NUMERO:
				t = "NUMERO(" + _TOKEN_TIPO_NUMERO + ")";
				break;
			case _TOKEN_TIPO_CADENA:
				t = "CADENA(" + _TOKEN_TIPO_CADENA + ")";
				break;
			case _TOKEN_TIPO_OPERADOR:
				t = "OPERADOR(" + _TOKEN_TIPO_OPERADOR + ")";
				break;
			case _TOKEN_TIPO_BOOLEANO:
				t = "BOOLEANO(" + _TOKEN_TIPO_BOOLEANO + ")";
				break;
			default:
				t = "_ERR(" + tipoTok + ")";
		}
		
		return "Length=["+ length + "]. Prog=" + Arrays.toString(prog) + "\nToken=[" + token
				+ "], TipoTok=[" + t + "], CarActual["+pos+"]=["+ p +"]";
	}
	
	
}
