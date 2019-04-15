package com.product.fcm.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Profile({"default", "docker-compose"})
public class FirebaseConfig {

    @Value("${fcm.firebase.app.url}")
    private String firebaseAppUrl;
    @Value("${fcm.firebase.path.serviceAccountKey}")
    private String firebasePathServiceAccountKey;

    @Bean
    public FirebaseApp getFirebaseApp() throws IOException {
        FileInputStream serviceAccount
                = new FileInputStream(firebasePathServiceAccountKey);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(firebaseAppUrl)
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        return FirebaseMessaging.getInstance(getFirebaseApp());
    }


}
