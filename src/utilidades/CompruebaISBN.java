package utilidades;

import excepciones.IsbnException;

public class CompruebaISBN {

	public static boolean compruebaISBN(String isbn) {
		boolean correcto = true;
		if(isbn.length() == 0) correcto = false;
		if(isbn.length() != 9) correcto = false;
        int isbnNum = 0, resto=0;

		 for (int x=0, cont=3; x<isbn.length()-1; x++){
	            if(cont%2==0){
	                isbnNum+=Integer.parseInt(String.valueOf(isbn.charAt(x)))*3;
	            }
	            else{
	                isbnNum+=Integer.parseInt(String.valueOf(isbn.charAt(x)));
	            }
	            cont++;
	        }
	        resto=isbnNum%10;
	        resto=(10-resto);
	        if(resto==10) resto=0;

	        if(resto!=Integer.parseInt(String.valueOf(isbn.charAt(12)))){
	        	correcto = false;
	        }
		return correcto;
	}

}
