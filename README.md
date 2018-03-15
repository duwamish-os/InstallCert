InstallCaCert.kt
------------------

Usage:
--------

**1) Access server, and retrieve certificate (accept default certificate [1])**, make sure of expiry date

```
java -jar install-cacert.kotlin.jar [host]:[port]
```

**2) Extract certificate from created `jsse-cacerts` keystore**

```
keytool -exportcert -alias [host]-1 -keystore jssecacerts -storepass changeit -file [host].cer
```

**3) Import certificate into system trust-keystore(aka jre/lib/security/cacerts.jks)**

```
keytool -importcert -alias [host] -keystore [path to system keystore] -storepass changeit -file [host].cer
```

# Example:

STEP 1
------

```bash
java -jar install-cacert.kotlin.jar dialogflow.googleapis.com:443
=============================================================================================
Loading existing certKeyStore(Private keys) jssecacerts with passphrase changeit
Opening connection to dialogflow.googleapis.com:443............................................................
=============================================================================================
Starting TLS handshake with dialogflow.googleapis.com:443

No errors handshaking dialogflow.googleapis.com:443, server certificate is already trusted
=============================================================================================
=============================================================================================
Server sent 2 certificate(s):
 1 Subject CN=*.googleapis.com, O=Google Inc, L=Mountain View, ST=California, C=US
   Issuer  CN=Google Internet Authority G3, O=Google Trust Services, C=US
   sha1    74 73 b8 a0 b5 4e e0 a2 30 b4 45 d6 f1 28 7d 39 46 8d f9 db
   md5     2a c6 54 e0 3f 89 c8 60 25 d8 a7 fc 2a 85 a2 9a

 2 Subject CN=Google Internet Authority G3, O=Google Trust Services, C=US
   Issuer  CN=GlobalSign, O=GlobalSign, OU=GlobalSign Root CA - R2
   sha1    ee ac bd 0c b4 52 81 95 77 91 1e 1e 62 03 db 26 2f 84 a3 18
   md5     c4 b8 1c 95 68 1a ca cb 64 4c 07 70 ad d5 64 e1

=============================================================================================

Enter [or certificate-number] to add certificate to trusted keystore or 'q' to quit: [1/q]

[
[
  Version: V3
  Subject: CN=*.googleapis.com, O=Google Inc, L=Mountain View, ST=California, C=US
  Signature Algorithm: SHA256withRSA, OID = 1.2.840.113549.1.1.11

  Key:  Sun EC public key, 256 bits
  public x coord: 14203510807536917580937834427412093341435782280829826605480407218958895057327
  public y coord: 80953735086623440492101342189279885412356216005241442496895807618171218877777
  parameters: secp256r1 [NIST P-256, X9.62 prime256v1] (1.2.840.10045.3.1.7)
  Validity: [From: Tue Feb 20 09:20:01 CST 2018,
               To: Tue May 15 09:10:00 CDT 2018]
  Issuer: CN=Google Internet Authority G3, O=Google Trust Services, C=US
  SerialNumber: [    38030cf3 c2fb797a]

Certificate Extensions: 9
[1]: ObjectId: 1.3.6.1.5.5.7.1.1 Criticality=false
AuthorityInfoAccess [
  [
   accessMethod: caIssuers
   accessLocation: URIName: http://pki.goog/gsr2/GTSGIAG3.crt
,
   accessMethod: ocsp
   accessLocation: URIName: http://ocsp.pki.goog/GTSGIAG3
]
]

[2]: ObjectId: 2.5.29.35 Criticality=false
AuthorityKeyIdentifier [
KeyIdentifier [
0000: 77 C2 B8 50 9A 67 76 76   B1 2D C2 86 D0 83 A0 7E  w..P.gvv.-......
0010: A6 7E BA 4B                                        ...K
]
]

[3]: ObjectId: 2.5.29.19 Criticality=true
BasicConstraints:[
  CA:false
  PathLen: undefined
]

[4]: ObjectId: 2.5.29.31 Criticality=false
CRLDistributionPoints [
  [DistributionPoint:
     [URIName: http://crl.pki.goog/GTSGIAG3.crl]
]]

[5]: ObjectId: 2.5.29.32 Criticality=false
CertificatePolicies [
  [CertificatePolicyId: [1.3.6.1.4.1.11129.2.5.3]
[]  ]
  [CertificatePolicyId: [2.23.140.1.2.2]
[]  ]
]

[6]: ObjectId: 2.5.29.37 Criticality=false
ExtendedKeyUsages [
  serverAuth
]

[7]: ObjectId: 2.5.29.15 Criticality=true
KeyUsage [
  DigitalSignature
]

[8]: ObjectId: 2.5.29.17 Criticality=false
SubjectAlternativeName [
  DNSName: *.googleapis.com
  DNSName: *.clients6.google.com
  DNSName: *.cloudendpointsapis.com
  DNSName: cloudendpointsapis.com
  DNSName: googleapis.com
]

[9]: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: D7 1E 40 4F 0D DF 61 98   51 5C 24 C3 0D 13 B2 92  ..@O..a.Q\$.....
0010: F8 47 46 CD                                        .GF.
]
]

]
  Algorithm: [SHA256withRSA]
  Signature:
0000: 02 27 DD 62 5A 77 7F A9   EC 3F 36 5D EC C0 35 7F  .'.bZw...?6]..5.
0010: 46 A9 D0 E8 9A 21 75 70   9C 4B E5 1D E5 58 6D EA  F....!up.K...Xm.
0020: 08 C4 CB 00 A6 18 A3 5B   D9 48 53 CE C4 65 70 60  .......[.HS..ep`
0030: F6 17 DF 9C C1 C4 EC 8C   7B 36 CC E2 9D 22 35 A8  .........6..."5.
0040: 63 82 95 4E 91 10 1F 51   6E 4C DA 7D DE 69 43 85  c..N...QnL...iC.
0050: F6 85 8A 94 40 C3 50 1A   E7 F2 56 C8 8F 7B A7 0C  ....@.P...V.....
0060: A0 A3 AD CA E7 C8 AB 0B   DA 5E C0 DB 2F 5B 91 A8  .........^../[..
0070: 0B 6D 50 AF 94 41 61 82   E4 4B 25 A3 96 8C CA 25  .mP..Aa..K%....%
0080: 48 04 E2 4A 0D B0 A4 9E   DD 2A DA 59 F5 6B 6B 69  H..J.....*.Y.kki
0090: 4B 4D CC D3 9D 27 6B 28   AC 2C 56 AA 05 84 13 24  KM...'k(.,V....$
00A0: CA 5D 76 01 79 DE 87 80   5C BA 8A 85 63 8A 4C D4  .]v.y...\...c.L.
00B0: E0 D5 86 5A E1 B7 75 6F   BB DC B2 6F 52 83 60 04  ...Z..uo...oR.`.
00C0: D5 A2 7E 57 6D 7E 13 85   D9 E1 0F D9 32 C7 14 83  ...Wm.......2...
00D0: 99 A4 FF 1D F8 1A 56 31   33 D5 15 58 83 5E 5F FF  ......V13..X.^_.
00E0: FB 08 D6 C3 4E 09 56 8F   72 E0 24 C5 28 36 56 45  ....N.V.r.$.(6VE
00F0: BC B7 87 93 40 AA FF 4E   07 82 10 29 95 B2 BD A1  ....@..N...)....

]

Added certificate to cert-keystore 'jssecacerts' using alias 'dialogflow.googleapis.com-1'
===============================================================================
```

you will see `jssecacerts` created.

```
[guest@digitalassistant-test-c1 ~]$ ls -l
total 252948
-rw-r--r--  1 guest guest    968105 Mar 15 01:29 install-cacert.kotlin.jar
-rw-rw-r--  1 guest guest    114483 Mar 15 01:36 jssecacerts
```

STEP 2
------

```
keytool -exportcert -alias dialogflow.googleapis.com-1 -keystore jssecacerts -storepass changeit -file dialogflow.googleapis.com.cer

Certificate stored in file <dialogflow.googleapis.com.cer>
```

STEP 3
------

```
keytool -importcert -alias dialogflow.googleapis.com -keystore /usr/lib/jvm/java-6-sun-1.6.0.26/jre/lib/security/cacerts -storepass changeit -file dialogflow.googleapis.com.cer

Owner: CN=*.googleapis.com, O=Google Inc, L=Mountain View, ST=California, C=US
Issuer: CN=Google Internet Authority G3, O=Google Trust Services, C=US
Serial number: 38030cf3c2fb797a
Valid from: Tue Feb 20 09:20:01 CST 2018 until: Tue May 15 09:10:00 CDT 2018
Certificate fingerprints:
	 MD5:  2A:C6:54:E0:3F:89:C8:60:25:D8:A7:FC:2A:85:A2:9A
	 SHA1: 74:73:B8:A0:B5:4E:E0:A2:30:B4:45:D6:F1:28:7D:39:46:8D:F9:DB
	 SHA256: 3C:17:80:14:74:31:B3:58:66:D3:42:7D:37:90:A4:14:01:A1:AA:4C:CC:02:99:BD:FF:30:5D:95:B4:A1:EF:13
Signature algorithm name: SHA256withRSA
Subject Public Key Algorithm: 256-bit EC key
Version: 3

Extensions:

#1: ObjectId: 1.3.6.1.5.5.7.1.1 Criticality=false
AuthorityInfoAccess [
  [
   accessMethod: caIssuers
   accessLocation: URIName: http://pki.goog/gsr2/GTSGIAG3.crt
,
   accessMethod: ocsp
   accessLocation: URIName: http://ocsp.pki.goog/GTSGIAG3
]
]

#2: ObjectId: 2.5.29.35 Criticality=false
AuthorityKeyIdentifier [
KeyIdentifier [
0000: 77 C2 B8 50 9A 67 76 76   B1 2D C2 86 D0 83 A0 7E  w..P.gvv.-......
0010: A6 7E BA 4B                                        ...K
]
]

#3: ObjectId: 2.5.29.19 Criticality=true
BasicConstraints:[
  CA:false
  PathLen: undefined
]

#4: ObjectId: 2.5.29.31 Criticality=false
CRLDistributionPoints [
  [DistributionPoint:
     [URIName: http://crl.pki.goog/GTSGIAG3.crl]
]]

#5: ObjectId: 2.5.29.32 Criticality=false
CertificatePolicies [
  [CertificatePolicyId: [1.3.6.1.4.1.11129.2.5.3]
[]  ]
  [CertificatePolicyId: [2.23.140.1.2.2]
[]  ]
]

#6: ObjectId: 2.5.29.37 Criticality=false
ExtendedKeyUsages [
  serverAuth
]

#7: ObjectId: 2.5.29.15 Criticality=true
KeyUsage [
  DigitalSignature
]

#8: ObjectId: 2.5.29.17 Criticality=false
SubjectAlternativeName [
  DNSName: *.googleapis.com
  DNSName: *.clients6.google.com
  DNSName: *.cloudendpointsapis.com
  DNSName: cloudendpointsapis.com
  DNSName: googleapis.com
]

#9: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: D7 1E 40 4F 0D DF 61 98   51 5C 24 C3 0D 13 B2 92  ..@O..a.Q\$.....
0010: F8 47 46 CD                                        .GF.
]
]

Trust this certificate? [no]:  yes
Certificate was added to keystore
```

you will see cacerts updated with one entry. by default JDK truststore has 104 entries, which
becomes 105 once you add above cert.

Verify,

```
keytool -list -keystore jdk1.8.0_161/jre/lib/security/cacerts
```


Build and create artifact
-------------------------

```
gradle clean jar
```