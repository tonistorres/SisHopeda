package exceptions;

public class ValidacaoException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8916895283914218760L;

	
	//construtor padr�o 
	public ValidacaoException(String msg) {
		super(msg);
	}
	
	
	//construtor sobrecarregado 
	public ValidacaoException(String msg, Exception exception) {
		super(msg,exception);
	}
	
}
