import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
 
public final class getAllLeafsExample {
 
	public static void main(String[] args) {
		//Resource resource = new ClassPathResource("/SpringLDAPClient/src/com/javaworld/sample/springldap.xml");
		//System.out.println(resource.toString());
		try {
			Resource resource = new ClassPathResource("springldap.xml");
			BeanFactory factory = new XmlBeanFactory(resource);
			System.out.println(factory.toString() + "\n");
 
			ContactDAO ldapContact = (LDAPContactDAO)factory.getBean("ldapContact");	
			List contactList =ldapContact.getAllContactNames();
			if(contactList != null){
				System.out.println(contactList.size());
				int count = 0;
				for( int i = 0 ; i < contactList.size(); i++){
					System.out.print("TIREA user --> " + contactList.get(i)+ "\n");
					count++;
				}
				System.out.println("\n" + count);
			}
		} catch (DataAccessException e) {
			System.out.println("Error occured " + e.getCause());
		}
	}
}