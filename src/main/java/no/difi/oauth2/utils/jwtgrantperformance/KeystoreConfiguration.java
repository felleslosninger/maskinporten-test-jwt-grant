package no.difi.oauth2.utils.jwtgrantperformance;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

@Data
@Configuration
@ConfigurationProperties(prefix = "keystore")
public class KeystoreConfiguration {

    private X509Certificate certificate;
    private PrivateKey privateKey;
    private String file;
    private String password;
    private String alias;
    private String keyPass;


    public X509Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }


    @Bean
    public JwtKeystore keyStoreProvider() {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream is =classLoader.getResourceAsStream(file)){
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(is, password.toCharArray());
            return  new JwtKeystore( (X509Certificate) keyStore.getCertificate(alias),(PrivateKey) keyStore.getKey(alias, keyPass.toCharArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
       return  null;
    }


}
