public class ContactDTO {
 
	String mail;
	String sap;
	public String getSap() {
		return sap;
	}
	public void setSap(String sap) {
		this.sap = sap;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
 
	public String toString() {
		StringBuffer contactDTOStr = new StringBuffer("Person=[");
 
		contactDTOStr.append(" mail = " + mail);
		contactDTOStr.append(" ]");
		return contactDTOStr.toString();
	}
}