package com.my.exaple;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

public class LdapSearcher {

    private static final String EMPTY_RESULT = "empty_result";
    private static final String LDAP_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

    private static final String YOUR_COMPANY_LDAP_URL = "ldap.example.com";
    /**
     * PORT 636 used for HTTPS as standard port
     */
    private static final int PORT_FOR_HHTPS = 636;
    private static final String YOUR_COMPANY_DOMAIN_NAME = "example";

    /**
     * like in unix for HTTPS  :
     *  ldapsearch -x -H ldaps://ldap.YOUR_DOMAIN.com -b "dc=YOUR_DOMAIN,dc=com" uid=YOUR_LDAP_LOGIN |grep mail
     */
    private static final String YOUR_PROVIDER_URL_HTTPS_WITH_ATTRIBUTES =
            "ldaps://"
            + YOUR_COMPANY_LDAP_URL
            + ":" + PORT_FOR_HHTPS
            + "/dc=" + YOUR_COMPANY_DOMAIN_NAME
            + ",dc=com";

    /**
     * Find user email from user ldap using no SECURITY_AUTHENTICATION
     * @param ldapLogin
     * @return
     * @throws NamingException
     */
    public static String getUserEmailFromLdap(String ldapLogin) throws NamingException {
        Hashtable env = new Hashtable(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, YOUR_PROVIDER_URL_HTTPS_WITH_ATTRIBUTES);
        env.put(Context.SECURITY_AUTHENTICATION, "none");

        LdapContext ctx = new InitialLdapContext(env, null);
        ctx.setRequestControls(null);
        NamingEnumeration<?> namingEnum = ctx.search("", "(uid=" + ldapLogin + ")", getSimpleSearchControls());
        if (namingEnum.hasMore()) {
            SearchResult result = (SearchResult) namingEnum.next();
            Attributes attrs = result.getAttributes();
            namingEnum.close();
            return attrs.get("mail").toString();
        }
        namingEnum.close();
        return EMPTY_RESULT;
    }

    private static SearchControls getSimpleSearchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setTimeLimit(30000);
        return searchControls;
    }
}
