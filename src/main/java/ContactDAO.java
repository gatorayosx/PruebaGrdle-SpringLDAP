import java.util.List;
 
public interface ContactDAO {
 
	public List getAllContactNames();
 
	public List getContactDetails(String commonName);
 
}