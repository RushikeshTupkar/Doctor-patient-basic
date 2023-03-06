package com.geekster.Doctor.service;

import com.geekster.Doctor.dao.DoctorRepository;
import com.geekster.Doctor.dao.PatientRepository;
import com.geekster.Doctor.model.Doctor;
import com.geekster.Doctor.model.Patient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepository repository;
    @Autowired
    private DoctorRepository doctorRepository;

    public void savePatient(Patient patient) {
        repository.save(patient);
    }

    public List<Patient> getPatient(@Nullable String doctorId, @Nullable String patientId) {
        List<Patient>patients = new ArrayList<>();
        List<Patient>onlyPAtient =  new ArrayList<>();
        if(null!=doctorId){
            patients = repository.findAll();
            for(Patient p:patients){
                if(p.getDoctorId().getDoctorId()==(Integer.parseInt(doctorId))){
                    onlyPAtient.add(p);
                }
            }return onlyPAtient;
        }
        else if(patientId!=null){
            List<Integer>ids = new ArrayList<>();
            ids.add(Integer.parseInt(patientId));
            patients = repository.findAllById(ids);
            return patients;
        }
        else if(doctorId==null && patientId==null){
            patients = repository.findAll();
            return patients;
        }
        return patients;
    }

    public void updateDoctor(JSONObject json) {
        Patient patient = repository.findById(json.getInt("patientId")).get();
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
        repository.save(patient);
    }

    public void deletePatient(Integer id) {
        repository.deleteById(id);
    }
}
