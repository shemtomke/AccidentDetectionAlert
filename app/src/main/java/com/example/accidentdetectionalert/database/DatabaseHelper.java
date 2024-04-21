package com.example.accidentdetectionalert.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.accidentdetectionalert.models.Accident;
import com.example.accidentdetectionalert.models.Ambulance;
import com.example.accidentdetectionalert.models.EmergencyContact;
import com.example.accidentdetectionalert.models.Hospital;
import com.example.accidentdetectionalert.models.Police;
import com.example.accidentdetectionalert.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "detection_system.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Table
        db.execSQL("CREATE TABLE user (id INTEGER PRIMARY KEY, fullname TEXT, email TEXT, password TEXT, phonenumber TEXT, Role Text)");
        db.execSQL("CREATE TABLE accident (id INTEGER PRIMARY KEY, user_id TEXT, date_time TEXT, location TEXT, status TEXT, " +
                "FOREIGN KEY (user_id) REFERENCES user(id))");
        db.execSQL("CREATE TABLE emergencyContact (id INTEGER PRIMARY KEY, user_id TEXT, name TEXT, phoneNumber TEXT, " +
                "FOREIGN KEY (user_id) REFERENCES user(id))");
        db.execSQL("CREATE TABLE hospital (id INTEGER PRIMARY KEY, user_id TEXT, location TEXT, " +
                "FOREIGN KEY (user_id) REFERENCES user(id))");
        db.execSQL("CREATE TABLE police (id INTEGER PRIMARY KEY, user_id TEXT, location TEXT, " +
                "FOREIGN KEY (user_id) REFERENCES user(id))");
        db.execSQL("CREATE TABLE ambulance (id INTEGER PRIMARY KEY, user_id TEXT, hospital_id TEXT, location TEXT, " +
                "FOREIGN KEY (user_id) REFERENCES user(id), " +
                "FOREIGN KEY (hospital_id) REFERENCES hospital(id))");

        db.execSQL("INSERT INTO user (id, fullname, email, password, phonenumber, Role) VALUES (0, 'admin', 'admin@gmail.com', 'admin', '0', 'admin')");
        db.execSQL("INSERT INTO user (id, fullname, email, password, phonenumber, Role) VALUES (1, 'police', 'police@gmail.com', 'police', '911', 'police')");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    // User
    public void createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fullname", user.getFullName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("phonenumber", user.getPhoneNumber());
        values.put("role", user.getRole());
        db.insert("user", null, values);
        db.close();
    }
    public void updateUserById(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fullname", user.getFullName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("phonenumber", user.getPhoneNumber());
        values.put("role", user.getRole());
        db.update("user", values, "id=?", new String[]{String.valueOf(user.getUserId())});
        db.close();
    }
    public void deleteUserById(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("user", "id=?", new String[]{String.valueOf(userId)});
        db.close();
    }
    public User getUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("user", null, "id=?", new String[]{String.valueOf(userId)}, null, null, null);
        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
            cursor.close();
        }
        db.close();
        return user;
    }
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("user", null, "email=?", new String[]{email}, null, null, null);
        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
            cursor.close();
        }
        db.close();
        return user;
    }
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE Role = 'citizen'", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                userList.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return userList;
    }
    // Accident
    public void createAccident(Accident accident) {
        SQLiteDatabase db = this.getWritableDatabase();

        User user = accident.getUser();
        ContentValues values = new ContentValues();
        values.put("user_id", user.getUserId());
        values.put("date_time", accident.getDateTime());
        values.put("location", accident.getLocation());
        values.put("status", accident.getStatus());
        db.insert("accident", null, values);
        db.close();
    }
    public void updateAccidentStatus(String accidentId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);
        db.update("accident", values, "id = ?", new String[]{accidentId});
        db.close();
    }
    public List<Accident> getAllAccidentsWithUserDetails() {
        List<Accident> accidentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT accident.id AS accident_id, accident.user_id, accident.date_time, accident.location, " +
                "user.fullname, user.email, user.password, user.phonenumber, user.Role " +
                "FROM accident " +
                "INNER JOIN user ON accident.user_id = user.id";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int accidentIdIndex = cursor.getColumnIndex("accident_id");
                int userIdIndex = cursor.getColumnIndex("user_id");
                int dateTimeIndex = cursor.getColumnIndex("date_time");
                int locationIndex = cursor.getColumnIndex("location");
                int statusIndex = cursor.getColumnIndex("status");
                int fullnameIndex = cursor.getColumnIndex("fullname");
                int emailIndex = cursor.getColumnIndex("email");
                int passwordIndex = cursor.getColumnIndex("password");
                int phonenumberIndex = cursor.getColumnIndex("phonenumber");
                int roleIndex = cursor.getColumnIndex("Role");

                if (accidentIdIndex != -1 && userIdIndex != -1 && dateTimeIndex != -1 && locationIndex != -1
                        && fullnameIndex != -1 && emailIndex != -1 && passwordIndex != -1
                        && phonenumberIndex != -1 && roleIndex != -1) {
                    Accident accident = new Accident(
                            cursor.getInt(accidentIdIndex),
                            new User(
                                    cursor.getInt(userIdIndex),
                                    cursor.getString(fullnameIndex),
                                    cursor.getString(emailIndex),
                                    cursor.getString(passwordIndex),
                                    cursor.getString(phonenumberIndex),
                                    cursor.getString(roleIndex)
                            ),
                            cursor.getString(dateTimeIndex),
                            cursor.getString(locationIndex),
                            cursor.getString(statusIndex)
                            );
                    accidentList.add(accident);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return accidentList;
    }
    public List<Accident> getAccidentsForHospital(int hospitalId) {
        List<Accident> accidents = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT accident.id AS accident_id, accident.user_id, accident.date_time, accident.location, accident.status, " +
                "user.fullname, user.email, user.password, user.phonenumber, user.Role " +
                "FROM accident " +
                "INNER JOIN user ON accident.user_id = user.id " +
                "INNER JOIN hospital ON user.id = hospital.user_id " +
                "WHERE hospital.id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(hospitalId)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int accidentIdIndex = cursor.getColumnIndex("accident_id");
                int userIdIndex = cursor.getColumnIndex("user_id");
                int dateTimeIndex = cursor.getColumnIndex("date_time");
                int locationIndex = cursor.getColumnIndex("location");
                int statusIndex = cursor.getColumnIndex("status");
                int fullnameIndex = cursor.getColumnIndex("fullname");
                int emailIndex = cursor.getColumnIndex("email");
                int passwordIndex = cursor.getColumnIndex("password");
                int phonenumberIndex = cursor.getColumnIndex("phonenumber");
                int roleIndex = cursor.getColumnIndex("Role");

                if (accidentIdIndex != -1 && userIdIndex != -1 && dateTimeIndex != -1 && locationIndex != -1
                        && fullnameIndex != -1 && emailIndex != -1 && passwordIndex != -1
                        && phonenumberIndex != -1 && roleIndex != -1) {
                    Accident accident = new Accident(
                            cursor.getInt(accidentIdIndex),
                            new User(
                                    cursor.getInt(userIdIndex),
                                    cursor.getString(fullnameIndex),
                                    cursor.getString(emailIndex),
                                    cursor.getString(passwordIndex),
                                    cursor.getString(phonenumberIndex),
                                    cursor.getString(roleIndex)
                            ),
                            cursor.getString(dateTimeIndex),
                            cursor.getString(locationIndex),
                            cursor.getString(statusIndex)
                    );
                    accidents.add(accident);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return accidents;
    }
    public List<Accident> getAccidentsForUser(int userId) {
        List<Accident> accidents = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM accident WHERE user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int accidentIdIndex = cursor.getColumnIndex("id");
                int dateTimeIndex = cursor.getColumnIndex("date_time");
                int locationIndex = cursor.getColumnIndex("location");
                int statusIndex = cursor.getColumnIndex("status");

                if (accidentIdIndex != -1 && dateTimeIndex != -1 && locationIndex != -1 && statusIndex != -1) {
                    Accident accident = new Accident(
                            cursor.getInt(accidentIdIndex),
                            getUser(userId), // Retrieve the user object associated with the accident
                            cursor.getString(dateTimeIndex),
                            cursor.getString(locationIndex),
                            cursor.getString(statusIndex)
                    );
                    accidents.add(accident);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return accidents;
    }
    // Emergency Contact
    public void createEmergencyContact(EmergencyContact emergencyContact) {
        SQLiteDatabase db = this.getWritableDatabase();

        User user = emergencyContact.getUser();
        ContentValues values = new ContentValues();
        values.put("user_id", user.getUserId());
        values.put("name", emergencyContact.getFullName());
        values.put("phoneNumber", emergencyContact.getPhoneContact());
        db.insert("emergencyContact", null, values);
        db.close();
    }
    public void updateEmergencyContact(EmergencyContact emergencyContact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", emergencyContact.getFullName());
        values.put("phoneNumber", emergencyContact.getPhoneContact());

        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(emergencyContact.getId())};

        db.update("emergencyContact", values, whereClause, whereArgs);
        db.close();
    }
    public void deleteEmergencyContact(int emergencyContactId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(emergencyContactId)};

        db.delete("emergencyContact", whereClause, whereArgs);
        db.close();
    }
    public EmergencyContact getEmergencyContactById(int emergencyContactId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", "user_id", "name", "phoneNumber"};
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(emergencyContactId)};
        Cursor cursor = db.query("emergencyContact", columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            try {
                int idIndex = cursor.getColumnIndex("id");
                int userIdIndex = cursor.getColumnIndex("user_id");
                int nameIndex = cursor.getColumnIndex("name");
                int phoneNumberIndex = cursor.getColumnIndex("phoneNumber");

                if (cursor.moveToFirst() && idIndex >= 0 && userIdIndex >= 0 && nameIndex >= 0 && phoneNumberIndex >= 0) {
                    int userId = cursor.getInt(userIdIndex);
                    String name = cursor.getString(nameIndex);
                    String phoneNumber = cursor.getString(phoneNumberIndex);

                    User user = getUser(userId);
                    return new EmergencyContact(emergencyContactId, user, name, phoneNumber);
                }
            } finally {
                cursor.close();
            }
        }

        db.close();
        return null; // Return null if contact not found
    }
    public List<EmergencyContact> getEmergencyContactsByUserId(int userId) {
        List<EmergencyContact> emergencyContactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", "user_id", "name", "phoneNumber"};
        String selection = "user_id=?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query("emergencyContact", columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int phoneNumberIndex = cursor.getColumnIndex("phoneNumber");
            if (cursor.moveToFirst() && idIndex >= 0 && nameIndex >= 0 && phoneNumberIndex >= 0) {
                do {
                    int emergencyContactId = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    String phoneNumber = cursor.getString(phoneNumberIndex);

                    User user = getUser(userId);
                    EmergencyContact emergencyContact = new EmergencyContact(emergencyContactId, user, name, phoneNumber);
                    emergencyContactList.add(emergencyContact);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return emergencyContactList;
    }
    // Hospital
    public void createHospital(Hospital hospital) {
        SQLiteDatabase db = this.getWritableDatabase();

        User user = hospital.getUser();
        ContentValues values = new ContentValues();
        values.put("id", hospital.getHospitalId());
        values.put("user_id", user.getUserId());
        values.put("location", hospital.getLocation());
        db.insert("hospital", null, values);
        db.close();
    }
    public Hospital getHospitalById(int hospitalId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", "user_id", "location"};
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(hospitalId)};
        Cursor cursor = db.query("hospital", columns, selection, selectionArgs, null, null, null);
        Hospital hospital = null;
        if (cursor != null) {
            int idIndex = cursor.getColumnIndex("id");
            int userIdIndex = cursor.getColumnIndex("user_id");
            int locationIndex = cursor.getColumnIndex("location");
            if (cursor.moveToFirst() && idIndex >= 0 && userIdIndex >= 0 && locationIndex >= 0) {
                int userId = cursor.getInt(userIdIndex);
                String location = cursor.getString(locationIndex);

                // Retrieve the user object associated with the hospital
                User user = getUser(userId);
                hospital = new Hospital(hospitalId, user, location);
            }
            cursor.close();
        }
        db.close();
        return hospital;
    }
    public List<Hospital> getAllHospitals() {
        List<Hospital> hospitals = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", "user_id", "location"};
        Cursor cursor = db.query("hospital", columns, null, null, null, null, null);
        if (cursor != null) {
            int idIndex = cursor.getColumnIndex("id");
            int userIdIndex = cursor.getColumnIndex("user_id");
            int locationIndex = cursor.getColumnIndex("location");
            while (cursor.moveToNext()) {
                if (idIndex >= 0 && userIdIndex >= 0 && locationIndex >= 0) {
                    int hospitalId = cursor.getInt(idIndex);
                    int userId = cursor.getInt(userIdIndex);
                    String location = cursor.getString(locationIndex);
                    User user = getUser(userId);
                    hospitals.add(new Hospital(hospitalId, user, location));
                }
            }
            cursor.close();
        }
        db.close();
        return hospitals;
    }
    // Police
    public void createPolice(Police police) {
        SQLiteDatabase db = this.getWritableDatabase();

        User user = police.getUser();
        ContentValues values = new ContentValues();
        values.put("id", police.getPoliceId());
        values.put("user_id", user.getUserId());
        values.put("location", police.getLocation());
        db.insert("police", null, values);
        db.close();
    }
    public Police getPoliceById(int policeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", "user_id", "location"};
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(policeId)};
        Cursor cursor = db.query("police", columns, selection, selectionArgs, null, null, null);
        Police police = null;
        if (cursor != null) {
            int idIndex = cursor.getColumnIndex("id");
            int userIdIndex = cursor.getColumnIndex("user_id");
            int locationIndex = cursor.getColumnIndex("location");
            if (cursor.moveToFirst() && idIndex >= 0 && userIdIndex >= 0 && locationIndex >= 0) {
                int userId = cursor.getInt(userIdIndex);
                String location = cursor.getString(locationIndex);

                // Retrieve the user object associated with the police
                User user = getUser(userId);

                police = new Police(policeId, user, location);
            }
            cursor.close();
        }
        db.close();
        return police;
    }
    // Ambulance
    public void createAmbulance(Ambulance ambulance) {
        SQLiteDatabase db = this.getWritableDatabase();
        User user = ambulance.getUser();
        Hospital hospital = ambulance.getHospital();

        ContentValues values = new ContentValues();
        values.put("id", ambulance.getAmbulanceId());
        values.put("user_id", user.getUserId());
        values.put("hospital_id", hospital.getHospitalId());
        values.put("location", ambulance.getLocation());
        db.insert("ambulance", null, values);
        db.close();
    }
    public void updateAmbulance(Ambulance ambulance) {
        SQLiteDatabase db = this.getWritableDatabase();
        User user = ambulance.getUser();
        Hospital hospital = ambulance.getHospital();

        ContentValues values = new ContentValues();
        values.put("user_id", user.getUserId());
        values.put("hospital_id", hospital.getHospitalId());
        values.put("location", ambulance.getLocation());

        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(ambulance.getAmbulanceId())};

        db.update("ambulance", values, selection, selectionArgs);
        db.close();
    }

    public void deleteAmbulance(int ambulanceId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(ambulanceId)};
        db.delete("ambulance", selection, selectionArgs);
        db.close();
    }
    public List<Ambulance> getAllAmbulances() {
        List<Ambulance> ambulanceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ambulance", null);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int userIdIndex = cursor.getColumnIndex("user_id");
            int hospitalIdIndex = cursor.getColumnIndex("hospital_id");
            int locationIndex = cursor.getColumnIndex("location");

            do {
                int id = cursor.getInt(idIndex);
                int userId = cursor.getInt(userIdIndex);
                int hospitalId = cursor.getInt(hospitalIdIndex);
                String location = cursor.getString(locationIndex);

                // Assuming you have methods to get user and hospital based on their IDs
                User user = getUser(userId);
                Hospital hospital = getHospitalById(hospitalId);

                if (user != null && hospital != null) {
                    Ambulance ambulance = new Ambulance(id, user, hospital, location);
                    ambulanceList.add(ambulance);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return ambulanceList;
    }
    public List<Ambulance> getAmbulancesForHospital(int hospitalId) {
        List<Ambulance> ambulanceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", "user_id", "hospital_id", "location"};
        String selection = "hospital_id=?";
        String[] selectionArgs = {String.valueOf(hospitalId)};
        Cursor cursor = db.query("ambulance", columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex("id");
                int userIdIndex = cursor.getColumnIndex("user_id");
                int hospitalIdIndex = cursor.getColumnIndex("hospital_id");
                int locationIndex = cursor.getColumnIndex("location");

                if (idIndex >= 0 && userIdIndex >= 0 && hospitalIdIndex >= 0 && locationIndex >= 0) {
                    int id = cursor.getInt(idIndex);
                    int userId = cursor.getInt(userIdIndex);
                    String location = cursor.getString(locationIndex);

                    // Assuming you have a method to get a user and hospital based on their IDs
                    User user = getUser(userId);
                    Hospital hospital = getHospitalById(hospitalId);

                    if (user != null && hospital != null) {
                        Ambulance ambulance = new Ambulance(id, user, hospital, location);
                        ambulanceList.add(ambulance);
                    }
                }
            }
            cursor.close();
        }
        db.close();
        return ambulanceList;
    }
}
