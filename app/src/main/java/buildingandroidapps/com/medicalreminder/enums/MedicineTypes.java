package buildingandroidapps.com.medicalreminder.enums;

/**
 * Created by Drasko on 21.11.2014..
 */
public enum MedicineTypes {
    PILL(0), SYRUP(1), INJECTION(2);
    private int value;

    MedicineTypes(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
