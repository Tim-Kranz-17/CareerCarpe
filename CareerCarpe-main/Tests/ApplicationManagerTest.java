package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.UUID;

import Handlers.DatabaseHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Tables.ApplicationManager;
import Data.Administrator;
import Data.Application;
import Data.Employer;
import Data.Internship;
import Data.Resume;
import Data.Student;
import Data.Tableable;
import Data.User;

class ApplicationManagerTest {
    private String employerUuid = "EMPLOYER-04d41cce-20dc-4dbb-966d-26a99bfc3437";
    private ArrayList<Application> applicationList = DatabaseHandler.getApplicationsByEmployer(employerUuid);
    
    @Test
    void getAllApplications(){
        assertEquals(2,applicationList.size());
    }

    @Test
    void addApplication(){
        Application application = new Application("APPLICATION-533afa48-9f36-4707-b70d-e22da9a2b968", "STUDENT-3867a076-9c4a-474a-b6ee-58a097380d5a", "RESUME-f4a41fcc-0abe-454d-882a-324edcf91160", "INTERNSHIP-22b7fbcd-49d4-4cdf-a181-688e666c9672");
        DatabaseHandler.setApplication(application);
        ArrayList<Application> applicationList = DatabaseHandler.getApplicationsByEmployer(employerUuid);
        assertEquals(3, applicationList.size());
    }

    @Test
    void deleteApplication(){
        DatabaseHandler.deleteApplication("APPLICATION-533afa48-9f36-4707-b70d-e22da9a2b968");
        ArrayList<Application> applicationList = DatabaseHandler.getApplicationsByEmployer(employerUuid);
        assertEquals(2,applicationList.size());
    }

    @Test
    void getApplicationDisplay(){
        Application application = new Application("APPLICATION-533afa48-9f36-4707-b70d-e22da9a2b968", "STUDENT-3867a076-9c4a-474a-b6ee-58a097380d5a", "RESUME-f4a41fcc-0abe-454d-882a-324edcf91160", "INTERNSHIP-22b7fbcd-49d4-4cdf-a181-688e666c9672");
        assertEquals(" ", ((Tableable) application).toView());
    }

    @Test
    void getInternshipName(){
        assertEquals("Front End Javascript Developer 2", DatabaseHandler.getApplicationsByEmployer("EMPLOYER-04d41cce-20dc-4dbb-966d-26a99bfc3437"));
    }
}
