package com.geekster.Doctor.service;

import com.geekster.Doctor.dao.DoctorRepository;
import com.geekster.Doctor.model.Doctor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository repository;

    public Doctor saveDoctor(Doctor doctor) {
        String doctorName = doctor.getDoctorName();
        doctorName = "Dr. " + doctorName;
        doctor.setDoctorName(doctorName);
        return repository.save(doctor);
    }

    public List<Doctor> getDoctor(String doctorId) {

        List<Doctor> doctorList;

        if(null != doctorId) {
            doctorList = new ArrayList<>();
            doctorList.add(repository.findById(Integer.valueOf(doctorId)).get());
        } else {
            doctorList = repository.findAll();
        }
        return doctorList;
    }

    public Doctor getDoctorById(String doctorId) {

        return repository.findById(Integer.valueOf(doctorId)).get();
    }

    public void deleteDoctor(Integer id) {
        repository.deleteById(id);
    }

    public void updateDoctorDetails(JSONObject json) {
        Doctor doctor = repository.findById(json.getInt("doctorId")).get();

        String doctorName = json.getString("doctorName");
        doctor.setDoctorName(doctorName);

        String specializedIn = json.getString("specializedIn");
        doctor.setSpecializedIn(specializedIn);

        if(json.has("experience")) {
            String exp = json.getString("experience");
            doctor.setExperience(exp);
        }
        repository.save(doctor);
    }
}
