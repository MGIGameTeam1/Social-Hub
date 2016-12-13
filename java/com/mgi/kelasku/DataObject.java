package com.mgi.kelasku;

public class DataObject {
    public String groupName,groupDescription,other;
    public int groupDisplayPicture;

    public DataObject(String groupName, String groupDescription, String other, int groupDisplayPicture) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.other = other;
        this.groupDisplayPicture = groupDisplayPicture;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String grupName) {
        this.groupName = grupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getGroupDisplayPicture() {
        return groupDisplayPicture;
    }

    public void setGroupDisplayPicture(int groupDisplayPicture) {
        this.groupDisplayPicture = groupDisplayPicture;
    }
}
