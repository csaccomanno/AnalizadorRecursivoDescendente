package net.veritran.ard;

import static org.junit.Assert.*;

import net.veritran.ard.exception.ARDException;
import net.veritran.ard.types.TBoolean;
import net.veritran.ard.types.TNumber;
import net.veritran.ard.types.TString;
import net.veritran.ard.types.Type;

import org.junit.Test;

public class ASRD_TEST {

	String exp;

	@Test
	public void test_00_SimpleExpresion() throws ARDException {

		exp = " 7 >  3 ";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( true,  b.getValue() );
		
	}

	@Test
	public void test_01_CadenasIgual() throws ARDException {
		
		exp = "'cadena 1' == 'cadena 2'";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( false,  b.getValue() );
	}

	@Test
	public void test_02_CadenasDistinto() throws ARDException {
		
		exp = "'cadena 1' != 'cadena 2'";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( true,  b.getValue() );
	}

	@Test
	public void test_03_CadenaS15() throws ARDException {
		
		exp = "'cadena 15' == 'cadena 15' ";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( true,  b.getValue() );
	}
	
	@Test
	public void test_04_Menor() throws ARDException {
		
		exp = "7+1<2^-4";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( false,  b.getValue() );
	}
	
	@Test
	public void test_05_ExponenteNegativo() throws ARDException {
		
		exp = "7+1<2^(-4)";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( false,  b.getValue() );
	}

	@Test
	public void test_06_Bool_Cadena() throws ARDException {
		
		exp = " 6 > 10 / 2  && 'Cad' == 'Cad' ";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( true,  b.getValue() );
	}
	
	@Test
	public void test_07_Num_Cadena_AND() throws ARDException {
		
		exp = " (6>(10/2))  && 'Cad' == 'Cad'";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( true,  b.getValue() );
	}

	@Test
	public void test_08_EspaciosAlFinal() throws ARDException {
		
		exp = " 9  >  10  /  2  +  3    ";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( true,  b.getValue() );
	}
	
	
	@Test
	public void test_09_AritmeticaMayor() throws ARDException {
		
		exp = "  9  >  10/  2  + 3";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( true,  b.getValue() );
	}
	
	@Test
	public void test_10_MultiplesExpresiones() throws ARDException {
		
		exp = "1>2 && 3<=4 || 5 != 6 && 7>=8 ";
		//      F  &&  T   ||   T   &&  F
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( false,  b.getValue() );
	}
	
	@Test
	public void test_11_Booleano() throws ARDException {
		
		exp = "True && False";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( false,  b.getValue() );
	}
	
	@Test
	public void test_12_BooleanoParentesis() throws ARDException {
		
		exp = "  (  false  &&false  ) || (True&&  FALsE )  ";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( false,  b.getValue() );
	}
	
	@Test
	public void test_13_BooleanoParentesisDobles() throws ARDException {
		
		exp = "  (  (false  &&false  )||(True  &&  FALsE ))   ";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( false,  b.getValue() );
	}
	
	@Test
	public void test_13_BooleanoTripe() throws ARDException {
		
		//        False      ||       False     ||    True
		exp = "(TRUE&&False )|| ( FALSE&& faLse)||(True&& TrUE)  ";  
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( true,  b.getValue() );
	}
	
	@Test
	public void test_13_1_BooleanoTripe() throws ARDException {
		
		//        False      ||       False     ||    True
		exp = "(3>2&&False )|| ( FALSE&& faLse)||(True&& TrUE)  ";  
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( true,  b.getValue() );
	}
	
	@Test
	public void test_14_UnarioNegativo() throws ARDException {
		
		exp = "2- -3";  
		TNumber b = (TNumber )executeAndPrint(exp);
		assertEquals( 5.0,  b.getValue() );
	}
	
	
	@Test
	public void test_15_UnarioPositivo() throws ARDException {
		
		exp = "2- +3";  
		TNumber b = (TNumber )executeAndPrint(exp);
		assertEquals( -1.0,  b.getValue() );
	}
	
	
	@Test
	public void test_15_DecimalesComasYPuntos() throws ARDException {
		
		exp = " 1.5 + +3.4 ";  
		TNumber b = (TNumber )executeAndPrint(exp);
		assertEquals( 4.9,  b.getValue() );
	}
	
	@Test
	public void test_15_DecimalesComasYPuntosJuntos() throws ARDException {
		
		exp = "5.5-+ 3.4 ";  
		TNumber b = (TNumber )executeAndPrint(exp);
		assertEquals( 2.1,  b.getValue() );
	}
	
	
	@Test
	public void test_16_Logico() throws ARDException {
		
		exp = " trUe && 'si' == 'si' ";
		TBoolean b = (TBoolean )executeAndPrint(exp);
		assertEquals( true,  b.getValue() );
	}

	
	@Test
	public void test_17_SoloCadena() throws ARDException {
		
		exp = " 'Solo una cadena' ";
		TString b = (TString )executeAndPrint(exp);
		assertEquals( "Solo una cadena",  b.getValue() );
	}
	
	
	@Test
	public void test_18_AritmericoPrecedente() throws ARDException {
		
		exp = "2+4*2-15/3+1.5";
		TNumber b = (TNumber )executeAndPrint(exp);
		assertEquals( 6.5,  b.getValue() );
	}
	
	private Type executeAndPrint(String exp) throws ARDException {		
		Type o = (Type )ASRD._eval( exp  );
		System.out.println( "Expresion [" + exp + "]: [" + o + "]");
		return o;
	}
}
