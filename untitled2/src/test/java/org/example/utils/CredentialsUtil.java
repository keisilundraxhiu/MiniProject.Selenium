package org.example.utils;

import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CredentialsUtil {
    private static final String CREDENTIALS_FILE_PATH = "src/test/resources/credentials.json";

    public static String getEmail() {
        return getCredential("email");
    }

    public static String getPassword() {
        return getCredential("password");
    }

    private static String getCredential(String key) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(CREDENTIALS_FILE_PATH)));
            JSONObject json = new JSONObject(content);
            return json.getString(key);
        } catch (IOException e) {
            throw new RuntimeException("Error reading credentials file", e);
        }
    }
}
//Shtojmë një klasë ndihmëse për të lexuar këtë file: per credentials.json
