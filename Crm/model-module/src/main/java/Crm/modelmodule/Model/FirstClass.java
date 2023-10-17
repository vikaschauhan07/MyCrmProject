package Crm.modelmodule.Model;


public class FirstClass {

	private int ui;
	public int getUi() {
		return ui;
	}
	public void setUi(int ui) {
		this.ui = ui;
	}
	public int getUi2() {
		return ui2;
	}
	public void setUi2(int ui2) {
		this.ui2 = ui2;
	}
	private int ui2;
	
	public FirstClass(int ui, int ui2) {
		
		this.ui = ui;
		this.ui2 = ui2;
	}
	@Override
	public String toString() {
		return "FirstClass [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	public int numH() {
		return this.ui + this.ui2;
	}

	
}
