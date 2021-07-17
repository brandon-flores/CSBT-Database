package com.loginform.asa;

import com.database.Employee;
import com.database.FirebaseDB;
import com.google.firebase.database.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestMain {
    public static void main(String[] args) throws IOException {
        FirebaseDB.initFirebase();

        System.out.println("1");
        DatabaseReference db;
        db = FirebaseDatabase.getInstance().getReference();

        DatabaseReference farmerRef = db.child("Employees");

        Map<String, Employee> farmers = new HashMap<>();
        farmerRef.setValue(new Employee("fert", "ferter", "fertest", "fertester", "Cashier", true));

       // farmerRef.setValueAsync(farmers);

        System.out.println("2");
        farmerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("3");
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Employee farmer = snap.getValue(Employee.class);
                    System.out.println(farmer.getUsername());
                }
                System.out.println("4");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("fail");
            }
        });
    }
}
