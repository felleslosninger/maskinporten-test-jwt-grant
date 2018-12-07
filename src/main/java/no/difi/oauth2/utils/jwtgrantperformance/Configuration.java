package no.difi.oauth2.utils.jwtgrantperformance;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Properties;

public class Configuration {

    private X509Certificate certificate;
    private PrivateKey privateKey;

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

    public static Configuration load() throws Exception {
        Configuration config = new Configuration();

        Properties props = readPropertyFile("./src/main/resources/application.properties");

        String keystoreFile = props.getProperty("keystore.file");
        String keystorePassword = props.getProperty("keystore.password");
        String keystoreAlias = props.getProperty("keystore.alias");
        String keystoreAliasPassword = props.getProperty("keystore.alias.password");

        loadCertificateAndKeyFromFile(config, keystoreFile, keystorePassword, keystoreAlias, keystoreAliasPassword);

        return config;
    }

    private static void loadCertificateAndKeyFromFile(Configuration config, String keyStoreFile, String keyStorePassword, String alias, String keyPassword) throws Exception {
        InputStream is = new FileInputStream(keyStoreFile);
        loadCertificate(config, is, keyStorePassword, alias, keyPassword);

    }

    private static void loadCertificate(Configuration config, InputStream is, String keystorePassword, String alias, String keyPassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(is, keystorePassword.toCharArray());

        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, keyPassword.toCharArray()); // Read from KeyStore
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);

        config.setCertificate(certificate);
        config.setPrivateKey(privateKey);
    }

    private static Properties readPropertyFile(String filename) throws Exception {
        Properties props = new Properties();

        InputStream inputStream = new FileInputStream(filename);
        if (inputStream != null) {
            props.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + filename + "' not found in the classpath");
        }

        return props;
    }

}
