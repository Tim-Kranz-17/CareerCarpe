package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.UUID;

import Handlers.DatabaseHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Tables.InternshipManager;
import Tables.InternshipListings;
import Data.Administrator;
import Data.Application;
import Data.Employer;
import Data.Internship;
import Data.Resume;
import Data.Student;
import Data.User;
class InternshipManagerTest {
        private Internship internship = InternshipListings.getInstance();
        private ArrayList<Internship> internshipList = DatabaseHandler.getAllInternships();

        @Test
        void testGetSize() {
            assertEquals(11, internshipList.size());
        }
        @Test
        void addTestInternship() {
            Internship internship = new Internship ("INTERNSHIP-f843cffc-1475-460f-8664-5f37573a6fad", "EMPLOYER-04d41cce-20dc-4dbb-966d-26a99bfc3437", "Learn to code for Good Will", "Good Will Junior Developer DBA", 20.5, 7, 8, null, null, true);
            DatabaseHandler.setInternship(internship);
            ArrayList<Internship> internshipList = DatabaseHandler.getAllInternships();
            assertEquals(12, internshipList.size());
        }
        @Test
        void deleteInternship() {
            DatabaseHandler.deleteInternship("INTERNSHIP-f843cffc-1475-460f-8664-5f37573a6fad");
            ArrayList<Internship> internshipList = DatabaseHandler.getAllInternships();
            assertEquals(11, internshipList.size());
        }
        @Test 
        void getInternshipDisplay() {
            Internship internship = new Internship ("INTERNSHIP-f843cffc-1475-460f-8664-5f37573a6fad", "EMPLOYER-04d41cce-20dc-4dbb-966d-26a99bfc3437", "Learn to code for Good Will", "Good Will Junior Developer DBA", 20.5, 7, 8, null, null, true);
            assertEquals(true, internship.getDisplayed());
        }
        @Test 
        void getPayRateCorrect() {
            Internship internship = new Internship ("INTERNSHIP-f843cffc-1475-460f-8664-5f37573a6fad", "EMPLOYER-04d41cce-20dc-4dbb-966d-26a99bfc3437", "Learn to code for Good Will", "Good Will Junior Developer DBA", 20.5, 7, 8, null, null, true);
            assertEquals(20.5, internship.getPayRate());
        }
        @Test 
        void changeDisplay() {
            Internship internship = new Internship ("INTERNSHIP-f843cffc-1475-460f-8664-5f37573a6fad", "EMPLOYER-04d41cce-20dc-4dbb-966d-26a99bfc3437", "Learn to code for Good Will", "Good Will Junior Developer DBA", 20.5, 7, 8, null, null, true);
            internship.setDisplayed(false);
            assertEquals(false, internship.getDisplayed());
        }
    }