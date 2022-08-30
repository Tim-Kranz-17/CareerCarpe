package Tests;

import static org.junit.jupiter.api.Assertions.*;
import Handlers.DatabaseHandler;

import org.junit.jupiter.api.Test;

import Data.Administrator;
import Data.Application;
import Data.Employer;
import Data.Internship;
import Data.Resume;
import Data.Student;
import Data.User;

class DatabaseHandlerTests {

    private static final String ADMINISTRATOR_UUID = "ADMINISTRATOR-A0000000-A000-A000-A000-A00000000000"; 
    private static final String STUDENT_UUID = "STUDENT-3867a076-9c4a-474a-b6ee-58a097380d5a"; 
    private static final String EMPLOYER_UUID = "EMPLOYER-2db7b9b3-5c1a-4f5d-af69-41016f85c06b"; 
    private static final String APPLICATION_UUID = "APPLICATION-533afa48-9f36-4707-b70d-e22da9a2b968"; 
    private static final String RESUME_UUID = "RESUME-25ed2d24-93a8-417e-9356-3bd6b313f2d2"; 
    private static final String INTERNSHIP_UUID = "INTERNSHIP-f843cffc-1475-460f-8664-5f37573a6fad"; 

    @Test
    void loginValidCredentials() {
        assertEquals(STUDENT_UUID, DatabaseHandler.login("KRH10@email.sc.edu", "password"));
    }

    @Test
    void loginInvalidCredentials() {
        assertNotEquals(STUDENT_UUID, DatabaseHandler.login("TKranz@email.sc.edu", "password"));
    }

    @Test
    void getUserThatIsAdministrator() {
        User user = DatabaseHandler.getUser(STUDENT_UUID);
        assertEquals(user.getUuid(), STUDENT_UUID);
    }
    
    @Test
    void getUserThatIsEmployer() {
        User user = DatabaseHandler.getUser(EMPLOYER_UUID);
        assertEquals(user.getUuid(), EMPLOYER_UUID);
    }
    
    @Test
    void getUserThatIsStudent() {
        User user = DatabaseHandler.getUser(ADMINISTRATOR_UUID);
        assertEquals(user.getUuid(), ADMINISTRATOR_UUID);
    }

    @Test
    void checkGetAdministratorValid() {        
        Administrator administrator = DatabaseHandler.getAdministrator(ADMINISTRATOR_UUID);
        assertEquals(administrator.getUuid(), ADMINISTRATOR_UUID);
    }

    @Test
    void checkGetEmployerValid() {        
        Employer employer = DatabaseHandler.getEmployer(EMPLOYER_UUID);
        assertEquals(employer.getUuid(), EMPLOYER_UUID);
    }

    @Test
    void checkGetStudentValid() {        
        Student student = DatabaseHandler.getStudent(STUDENT_UUID);
        assertEquals(student.getUuid(), STUDENT_UUID);
    }

    @Test
    void checkGetApplicationValid() {        
        Application application = DatabaseHandler.getApplication(APPLICATION_UUID);
        assertEquals(application.getUuid(), APPLICATION_UUID);
    }

    @Test
    void checkGetResumeValid() {        
        Resume resume = DatabaseHandler.getResume(RESUME_UUID);
        assertEquals(resume.getUuid(), RESUME_UUID);
    }

    @Test
    void checkGetInternshipValid() {        
        Internship internship = DatabaseHandler.getInternship(INTERNSHIP_UUID);
        assertEquals(internship.getUuid(), INTERNSHIP_UUID);
    }
}

