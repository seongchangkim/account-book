package com.account.accountbook.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    @Value("${app.firebase-project-id}")
    private String projectId;

    private StorageOptions storageOptions;

    @PostConstruct
    public void init(){

        try{
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream()))
                    .build();

            this.storageOptions = StorageOptions.newBuilder()
                    .setProjectId(projectId)
                    .setCredentials(
                            GoogleCredentials.fromStream(
                                    new ClassPathResource(firebaseConfigPath).getInputStream()
                            )
                    ).build();

            if(FirebaseApp.getApps().isEmpty()){
                FirebaseApp.initializeApp(options);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
