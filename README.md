
This project is simple example of how to get user data if you know user ldap using only java code.

The example based on unix command :
 
 **ldapsearch -x -H ldaps://ldap.YOUR_DOMAIN.com -b "dc=YOUR_DOMAIN,dc=com" uid=YOUR_LDAP_LOGIN |grep mail**
      
For example, get email of your user.

*To do this you have to know and enter next*:

1. HTTP or HTTPS (389 or 636)
2. YOUR_COMPANY_LDAP_URL (in this code: "ldap.example.com")
3. YOUR_COMPANY_DOMAIN_NAME (in this code: "example")


Fill free to use this code as you need.

Info about ldapsearch [here](https://en.wikipedia.org/wiki/Lightweight_Directory_Access_Protocol)