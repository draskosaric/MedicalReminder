package buildingandroidapps.com.medicalreminder.models;

import buildingandroidapps.com.medicalreminder.enums.MedicineTypes;

/**
 * Created by Drasko on 21.11.2014..
 */
public class Medicine {
    private String name;
    private MedicineTypes medicineType;
    private String dosage;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MedicineTypes getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(MedicineTypes medicineType) {
        this.medicineType = medicineType;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
