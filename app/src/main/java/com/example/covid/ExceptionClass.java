package com.example.covid;

import androidx.annotation.NonNull;

class IDDoesNotExistException extends Exception {
    @NonNull
    @Override
    public String toString() {
        return "ID does next exist";
    }
}

class PasswordDoesNotMatchException extends Exception {
    @NonNull
    @Override
    public String toString() {
        return "Password does not match";
    }
}

class DetailsHasNotBeenUpdatedException extends Exception {
    @NonNull
    @Override
    public String toString() {
        return "Details has not been updated\nPlease update full details";
    }
}

class DetailsHasBeenUpdatedException extends Exception {
    @NonNull
    @Override
    public String toString() {
        return "Details has already been updated";
    }
}

class Patient {
    public String id;
    public String password;

    public String status;
    public String hospital;

    public String age;
    public String gender;

    public String diabetes;
    public String kidney;
    public String heart;
    public String respiratory;
    public String pregnant;

    public String aayushmaan;
    public String aarogya;
    public String insurance;

    public String fever;
    public String breathlessness;
    public String pneumonia;

    public String oximeter_reading;

    public String home_isolation;
    public String updated;

    Patient(String id, String password) {
        this.id = id;
        this.password = password;

        this.status = "Home isolation";
        this.hospital = "";

        this.age = "0";
        this.gender = "male";

        this.diabetes = "false";
        this.kidney = "false";
        this.heart = "false";
        this.respiratory = "false";
        this.pregnant = "false";

        this.aayushmaan = "false";
        this.aarogya = "false";
        this.insurance = "false";

        this.fever = "false";
        this.breathlessness = "false";
        this.pneumonia = "false";

        this.oximeter_reading = "99";

        this.home_isolation = "true";
        this.updated = "false";
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDiabetes(boolean diabetes) {
        this.diabetes = String.valueOf(diabetes);
    }

    public void setKidney(boolean kidney) {
        this.kidney = String.valueOf(kidney);
    }

    public void setHeart(boolean heart) {
        this.heart = String.valueOf(heart);
    }

    public void setRespiratory(boolean respiratory) {
        this.respiratory = String.valueOf(respiratory);
    }

    public void setPregnant(boolean pregnant) {
        this.pregnant = String.valueOf(pregnant);
    }

    public void setAayushmaan(boolean aayushmaan) {
        this.aayushmaan = String.valueOf(aayushmaan);
    }

    public void setAarogya(boolean aarogya) {
        this.aarogya = String.valueOf(aarogya);
    }

    public void setInsurance(boolean insurance) {
        this.insurance = String.valueOf(insurance);
    }
}

class Hospital {
    public String id;
    public String name;
    public String location;

    public String government;

    public String general;
    public String oxygen;
    public String ventilator;

    public String general_occupied;
    public String oxygen_occupied;
    public String ventilator_occupied;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public void setOxygen(String oxygen) {
        this.oxygen = oxygen;
    }

    public void setVentilator(String ventilator) {
        this.ventilator = ventilator;
    }

    public void setGeneral_occupied(String general_occupied) {
        this.general_occupied = general_occupied;
    }

    public void setOxygen_occupied(String oxygen_occupied) {
        this.oxygen_occupied = oxygen_occupied;
    }

    public void setVentilator_occupied(String ventilator_occupied) {
        this.ventilator_occupied = ventilator_occupied;
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public String getFreeGeneral() {
        return String.valueOf(Integer.parseInt(this.general)-Integer.parseInt(this.general_occupied));
    }

    public String getFreeOxygen() {
        return String.valueOf(Integer.parseInt(this.oxygen)-Integer.parseInt(this.oxygen_occupied));
    }

    public String getFreeVentilator() {
        return String.valueOf(Integer.parseInt(this.ventilator)-Integer.parseInt(this.ventilator_occupied));
    }
}