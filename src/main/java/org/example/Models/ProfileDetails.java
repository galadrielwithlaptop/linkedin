package org.example.Models;

import java.awt.image.BufferedImage;
import java.util.UUID;

public class ProfileDetails {
    private BufferedImage profilePicture;
    private String Headline;
    private String summary;
    private String Experience;
    private String Education;
    private String Skills;
    private UUID accountUuid;

    public void setEducation(String education) {
        Education = education;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public void setProfilePicture(BufferedImage profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public void setSkills(String skills) {
        Skills = skills;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}


