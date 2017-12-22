#************EMERGENCY SMS SENDING PROGRAM*********************
#Sends SMS to preregistered numbers when bell fails to ring
# __AUTHOR__: VINEEL KUMAR
# __AUTHOR__: Varshaneya V
# VERSION:1.0
#************************************************************
import os
import time
import smtplib
import urllib2
import cookielib
import sys

#SMS sending code
print("Sending SMS...........................")
username = "<------your Way2sms ID------->"
passwd = "<--------your Way2sms Password-->"
message = "\n From intelliBell software: Bell at IP "+ sys.argv[1]+" did not ring. Cannot reach to that IP.\n\n"

# fill receivers' mobile nos (in double qoutes) to whom the sms needs to be sent
n=[]
for i in n:
    number =i
    message = "+".join(message.split(' '))
    url = 'http://site24.way2sms.com/Login1.action?'
    data = 'username='+username+'&password='+passwd+'&Submit=Sign+in'
    cj = cookielib.CookieJar()
    opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
    opener.addheaders = [('User-Agent','Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36')]
 
    try:
        usock = opener.open(url, data)
    except IOError:
        print ("Error while logging in.")
        sys.exit(1)
 
    jession_id = unicode(cj).split(u'~')[1].split(u' ')[0]
    send_sms_url = 'http://site24.way2sms.com/smstoss.action?'
    send_sms_data = 'ssaction=ss&Token='+jession_id+'&mobile='+number+'&message='+message+'&msgLen=136'
    opener.addheaders = [('Referer', 'http://site25.way2sms.com/sendSMS?Token='+jession_id)]

    try:
        sms_sent_page = opener.open(send_sms_url,send_sms_data)
    except IOError:
        print ("Error while sending message" )
        sys.exit(1)

    print ("SMS has been sent to " + number + ".")