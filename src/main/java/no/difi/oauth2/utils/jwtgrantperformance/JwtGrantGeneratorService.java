package no.difi.oauth2.utils.jwtgrantperformance;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jose.util.Base64;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.*;

@Service
public class JwtGrantGeneratorService {

    public String makeJwt(String issuer, String audience, String scope) throws Exception {

        Configuration config = Configuration.load();

        List<Base64> certChain = new ArrayList<>();
        certChain.add(Base64.encode(config.getCertificate().getEncoded()));

        JWSHeader jwtHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
				.x509CertChain(certChain)
				.build();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .audience(audience)
                .issuer(issuer)
                .claim("scope", scope)
                .jwtID(UUID.randomUUID().toString()) // Must be unique for each grant
                .issueTime(new Date(Clock.systemUTC().millis())) // Use UTC time!
                .expirationTime(new Date(Clock.systemUTC().millis() + 120000)) // Expiration time is 120 sec.
                .build();

        JWSSigner signer = new RSASSASigner(config.getPrivateKey());
        SignedJWT signedJWT = new SignedJWT(jwtHeader, claims);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

}