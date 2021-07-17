package com.database;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseDB {
    private static final String DATABASE_URL = "https://sbt-database-team-echo.firebaseio.com/";
    private static final String DATABASE_URL2 = "https://testicles-b025b.firebaseio.com/";
    private static DatabaseReference database;
    private static Map<String, Fee> feesMap = new HashMap<>();
    private static Map<String, Bus> busMap = new HashMap<>();
    private static FirebaseApp exitRFID;
    //private final FXMLAccountantWindowController controller;
    //private static ArrayList<Fee> feeslist = new ArrayList<>();


    public static void initFirebase() throws IOException {
        try {
            // [START initialize]
            FileInputStream serviceAccount = new FileInputStream("sbt-database-team-echo-firebase-adminsdk-idtwe-79746fb4e6.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);

            serviceAccount = new FileInputStream("testicles-b025b-firebase-adminsdk-7fg43-a204324cd8.json");
            FirebaseOptions secondaryAppConfig = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl(DATABASE_URL2)
                    .build();

            //FirebaseApp
            exitRFID = FirebaseApp.initializeApp(secondaryAppConfig, "exitRFID"); //needed for 2nd app call
            //database = FirebaseDatabase.getInstance(exitRFID).getReference();

            // [END initialize]
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: invalid service account credentials. See README.");
            System.out.println(e.getMessage());

            System.exit(1);
        }



//        DatabaseReference ref = database.child("Exit");
//        Exit exit = new Exit("51:5C:AA:89","loaded",LocalDate.now().toString());
//        ref.push().setValue(exit);


        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //database = FirebaseDatabase.getInstance().getReference();
        //startDataListener();
    }

    public static FirebaseApp getExitRFID() {
        return exitRFID;
    }
}
