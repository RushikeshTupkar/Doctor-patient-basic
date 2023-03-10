package com.geekster.Doctor.controller;

import com.geekster.Doctor.dao.DoctorRepository;
import com.geekster.Doctor.model.Doctor;
import com.geekster.Doctor.model.Patient;
import com.geekster.Doctor.service.PatientService;
import jakarta.annotation.Nullable;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.Timestamp;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientService service;

    @PostMapping(value = "/patient")
    public String savePatient(@RequestBody String patientRequest) {

        JSONObject json = new JSONObject(patientRequest);
        Patient patient = setPatient(json);
        service.savePatient(patient);

        return "Saved patient";

    }

    private Patient setPatient(JSONObject json) {

        Patient patient = new Patient();

        patient.setPatientId(json.getInt("patientId"));
        patient.setPatientName(json.getString("patientName"));
        patient.setAge(json.getInt("age"));
        patient.setPhoneNumber(json.getString("phoneNumber"));
        patient.setDiseaseType(json.getString("diseaseType"));
        patient.setGender(json.getString("gender"));

        Timestamp currTime = new Timestamp(System.currentTimeMillis());
        patient.setAdmitDate(currTime);

        String doctorId = json.getString("doctorId");
        Doctor doctor = doctorRepository.findById(Integer.valueOf(doctorId)).get();
        patient.setDoctorId(doctor);
        return patient;

    }
    @GetMapping(value = "/patient")
    public List<Patient> getPatients(@Nullable @RequestParam String doctorId,
                                     @Nullable @RequestParam String patientId) {
        return service.getPatient(doctorId,patientId);

    }
    @PutMapping("/updatePatient")
    public ResponseEntity<String> updatePatient(@RequestBody String patient){
        JSONObject jsonObject = new JSONObject(patient);
        service.updateDoctor(jsonObject);
        return new ResponseEntity<>("Patient updated with id - "+jsonObject.getInt("patientId"), HttpStatus.OK);
    }
    @DeleteMapping("/deletePatientWithId")
    public ResponseEntity<String> deletePatientWithId(@RequestParam("id") Integer id){
        service.deletePatient(id);
        return new ResponseEntity<>("Patient deleted with id - "+id,HttpStatus.OK);
    }
}
