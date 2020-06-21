package no.difi.oauth2.utils.jwtgrantperformance;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

class  JwtKeystore {
    private X509Certificate certificate;
    private PrivateKey privateKey;

    X509Certificate getCertificate() {
        return certificate;
    }
    PrivateKey getPrivateKey() {
        return privateKey;
    }


    JwtKeystore ( X509Certificate certificate,PrivateKey privateKey ){
        this.certificate = certificate;
        this.privateKey = privateKey;
    }
}