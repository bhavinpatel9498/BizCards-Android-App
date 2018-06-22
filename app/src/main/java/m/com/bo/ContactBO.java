package m.com.bo;

/**
 * Created by Bhavin on 24-Apr-18.
 */

public class ContactBO {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String company;
    private String deskphone;
    private String title;
    private String address;
    private String website;
    private String notes;

    public ContactBO()
    {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDeskphone() {
        return deskphone;
    }

    public void setDeskphone(String deskphone) {
        this.deskphone = deskphone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /*public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("BEGIN:USERPROFILE").append("|");
        //sb.append("VERSION:3.0").append("\n");
        if (firstName != null) {
            sb.append("firstName").append(":").append(firstName).append("|");
        }
        if (lastName != null) {
            sb.append("lastName").append(":").append(lastName).append("|");
        }
        if (email != null) {
            sb.append("email").append(":").append(email).append("|");
        }
        if (phone != null) {
            sb.append("phone").append(":").append(phone).append("|");
        }
        if (company != null) {
            sb.append("company").append(":").append(company).append("|");
        }
        if (deskphone != null) {
            sb.append("deskphone").append(":").append(deskphone).append("|");
        }
        if (title != null) {
            sb.append("title").append(":").append(title).append("|");
        }
        if (address != null) {
            sb.append("address").append(":").append(address).append("|");
        }
        if (website != null) {
            sb.append("website").append(":").append(website).append("|");
        }
        if (notes != null) {
            sb.append("notes").append(":").append(notes).append("|");
        }
        sb.append("END:USERPROFILE");
        return sb.toString();
    }
    */
}
