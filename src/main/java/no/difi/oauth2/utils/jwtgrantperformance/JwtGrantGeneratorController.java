package no.difi.oauth2.utils.jwtgrantperformance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtGrantGeneratorController {

    @Autowired
    JwtGrantGeneratorService jwtGrantGeneratorService;

    @RequestMapping("/jwtgrant")
    public String jwtGrant(@RequestParam(value = "issuer") String issuer,
                                      @RequestParam(value = "audience") String audience,
                                      @RequestParam(value = "scope") String scope) throws Exception {

        return(jwtGrantGeneratorService.makeJwt(issuer,audience,scope));

    }
}
