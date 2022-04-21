package net.veritran.ard.utils;

public class C {
	
	// Definicion para los OPERADORES (OPE_)
	//public static final String OPE_ASIGNACION  = "=";
	public static final String OPE_MAYOR       = ">";
	public static final String OPE_MAYOR_IGUAL = ">=";
	public static final String OPE_MENOR       = "<";
	public static final String OPE_MENOR_IGUAL = "<=";
	public static final String OPE_IGUAL       = "==";
	public static final String OPE_DISTINTO    = "!=";
	//public static final String OPE_NEGACION    = "!";

	public static final String OPE_SUMA        = "+";
	public static final String OPE_RESTA       = "-";
	public static final String OPE_MULTIPLICACION = "*";
	public static final String OPE_DIVISION    = "/";
	public static final String OPE_PORCENTAJE  = "%";
	public static final String OPE_EXPONENTE   = "^";
	
	public static final String OPE_LOGIC_AND   = "&&";
	public static final String OPE_LOGIC_OR    = "||";
	public static final String OPE_LOGIC_NOT   = "!";
	

	
	
	// Definicion para los TERMINOS (TER_)
	public static final String TER_SUMA  = "+";
	public static final String TER_RESTA = "-";
	
	// Definicion para los factores (FAC_)
	public static final String FAC_MULTIPLICACION = "*";
	public static final String FAC_DIVISION       = "/";
	public static final String FAC_PORCENTAJE     = "%";
	public static final String FAC_EXPONENTE      = "^";
	
	// Definicion para los factores UNARIOS
	public static final String UNARIO_POSITIVO = "+";
	public static final String UNARIO_NEGATIVO = "-";
	
	// Definicion para los parentesis
	public static final String PARENTESIS_IZQUIERDO = "(";
	public static final String PARENTESIS_DERECHO   = ")";
	
	// definicion para los operadores
	public static final int I_INVALIDO        = 0x00;
	
	//public static final int I_ASIGNACION      = 0x01;
	public static final int I_MAYOR           = 0x02;
	public static final int I_MAYOR_IGUAL     = 0x03;
	public static final int I_MENOR           = 0x04;
	public static final int I_MENOR_IGUAL     = 0x05;
	public static final int I_IGUAL           = 0x06;
	public static final int I_DISTINTO        = 0x07;
	public static final int I_NEGACION        = 0x08;
	public static final int I_SUMA            = 0x09;
	public static final int I_RESTA           = 0x0A;
	public static final int I_MULTIPLICACION  = 0x0B;
	public static final int I_DIVISION        = 0x0C;
	public static final int I_PORCENTAJE      = 0x0D;
	public static final int I_EXPONENTE       = 0x0E;
	
	// Las constantes tienen el mismo valor, pues los simbolos son los mismos. 
	public static final int I_UNARIO_POSITIVO = I_SUMA;
	public static final int I_UNARIO_NEGATIVO = I_RESTA;
	
	public static final int I_LOGIC_AND       = 0x12;
	public static final int I_LOGIC_OR        = 0x13;
	public static final int I_LOGIC_NOT       = 0x14;
	
	
}
