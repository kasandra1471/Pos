package PosControl;

public class MyExcep extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int i;

	public MyExcep(int i) {
		this.i = i;
	}

	@Override
	public String getMessage() {
		return i + " 선택 없음.";
	}

}
