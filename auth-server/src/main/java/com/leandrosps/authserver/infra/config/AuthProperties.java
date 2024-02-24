package com.leandrosps.authserver.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "aw.auth")
public class AuthProperties {

    @Value("${aw.auth.provider-uri}")
    private String providerUri;

    private JksProperties jks;

    public static class JksProperties {

        @Value("${aw.auth.jks.keypass}")
        private String keypass;

        @Value("${aw.auth.jks.storepass}")
        private String storepass;

        @Value("${aw.auth.jks.alias}")
        private String alias;

        @Value("${aw.auth.jks.path}")
        private String path;

        public String getKeypass() {
            return keypass;
        }

        public void setKeypass(String keypass) {
            this.keypass = keypass;
        }

        public String getStorepass() {
            return storepass;
        }

        public void setStorepass(String storepass) {
            this.storepass = storepass;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

    }

    public String getProviderUri() {
        return providerUri;
    }

    public void setProviderUri(String providerUri) {
        this.providerUri = providerUri;
    }

    public JksProperties getJks() {
        return jks;
    }

    public void setJks(JksProperties jks) {
        this.jks = jks;
    }
}