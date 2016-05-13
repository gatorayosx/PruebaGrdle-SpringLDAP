import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.security.ldap.LdapUtils;
import org.springframework.security.ldap.ppolicy.PasswordPolicyAwareContextSource;
import org.springframework.security.ldap.ppolicy.PasswordPolicyControlExtractor;
import org.springframework.security.ldap.ppolicy.PasswordPolicyResponseControl;
import static org.springframework.ldap.query.LdapQueryBuilder.query;
 
public class LDAPContactDAO implements ContactDAO{
	private LdapTemplate ldapTemplate;
 
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}
 
	public List getAllContactNames() {
		
		PasswordPolicyAwareContextSource pp = new PasswordPolicyAwareContextSource("ldap://tireasol47.ral.tirea.es:390/o=TIREA,c=ES");
		try {
			ContextSource ctx = ldapTemplate.getContextSource();
			DirContext  DCctx = ctx.getContext("cn=Directory Manager", "tirea.tecno");
			PasswordPolicyResponseControl ctrl = PasswordPolicyControlExtractor.extractControl(DCctx);
	        if (ctrl != null) {
	            System.out.println(ctrl.getErrorStatus().getErrorCode());
	        }
	        //throw LdapUtils.convertLdapException(e);
			
	        LdapQuery query = query()
	                .attributes("cn", "ds-pwp-password-policy-dn", "ds-pwp-password-expiration-time")
	                .where("objectclass").is("person");
	        return ldapTemplate.search(query,
	                new AttributesMapper<String>() {
	                   public String mapFromAttributes(Attributes attrs) throws NamingException {
	                	   return getAttributeVal(attrs.get("cn")) +
	                			  getAttributeVal(attrs.get("ds-pwp-password-policy-dn")) +
	                			  getAttributeVal(attrs.get("ds-pwp-password-expiration-time"));
	                   }
	                });
		} catch (AuthenticationException e) {
			System.out.println("Authentication failed. Error is:" + e.getMessage());
		}
		catch (Exception e) {
			System.out.println("Authentication failed. Error is:" + e.getMessage());
			System.out.println("Cause is:" + e.getCause());
		}
		return null;
	}
 
	public String getAttributeVal(Attribute attr) throws NamingException{
		if(attr == null)
			return " ";
		else
			return (String) attr.get()+"|";
	}
	
	public List getContactDetails(String objectclass){
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectClass",objectclass));
		System.out.println("LDAP Query " + andFilter.encode());
		return ldapTemplate.search("", andFilter.encode(),new ContactAttributeMapper());
 
	}
}