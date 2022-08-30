package Handlers;

import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import Data.Administrator;
import Data.Application;
import Data.Datum;
import Data.Employer;
import Data.Internship;
import Data.DataTypes;
import Data.Resume;
import Data.Student;
import Data.User;

/*
* The DatabaseHandler Class which handles the formed database.
*/

public class DatabaseHandler
{   

    private static final Administrator ROOT = new Administrator(
        "ADMINISTRATOR-A0000000-A000-A000-A000-A00000000000", "ROOT", "undefined", "toor", "undefined", "undefined", "undefined", "ROOT"
    );

    private static final String USERS_DB = "Databases/users_db.json";
    private static final String INTERNSHIPS_DB = "Databases/internships_db.json";
    private static final String RESUMES_DB = "Databases/resumes_db.json";
    private static final String APPLICATIONS_DB = "Databases/applications_db.json";

    /**
     * 
     * the database checks and determines that the users email and password are apart of the database.
     * 
     * @param email, the email that the user inputs into the program.
     * @param password, the password that the user inputs into the program.
     */
    public static String login(String email, String password) {
        JSONArray jsonArray = DatabaseHandler.readJsonArrayFromFile(DataTypes.USER);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            if (jsonObject.get("email").equals(email) && jsonObject.get("password").equals(password)) {
                return jsonObject.get("uuid").toString();
            }
        }
        if (email.equals(ROOT.getEmail()) && password.equals(ROOT.getPassword())) {
            return ROOT.getUuid();
        }
        return "";
    }

    /**
     * checks to determine if the user is either an administrator, employer, or student.
     * @param user, a user who is signing into the program.
     */
    public static void signUp(User user) {
        if (user instanceof Administrator) {
            DatabaseHandler.setAdministrator((Administrator) user);
        } else if (user instanceof Employer) {
            DatabaseHandler.setEmployer((Employer) user);
        } else if (user instanceof Student) {
            DatabaseHandler.setStudent((Student) user);    
        } else {
            DatabaseHandler.invalidArgument("public void signUp(User user);");
        }
    }
    /**
     * depending on the uuid will return the user from the database handler.
     * @param uuid, the uuid which the database uses to retrieve specific users.
     */
    public static User getUser(String uuid) {
        DataTypes dataType = DatabaseHandler.getDataTypeFromUUID(uuid);
        switch (dataType) {
            case ADMINISTRATOR:
                return DatabaseHandler.getAdministrator(uuid);
            case EMPLOYER:
                return DatabaseHandler.getEmployer(uuid);
            case STUDENT:
                return DatabaseHandler.getStudent(uuid);
            default:
                throw new UnsupportedOperationException("unable to resolve " + uuid + " into valid user.");
        }
    }
    /**
     * get the administrator from the database using the uuid.
     * @param uuid, the uuid which the database uses to retrieve the administrator.
     */
    public static Administrator getAdministrator(String uuid) {
        return (Administrator) DatabaseHandler.getDatumFromDatabase(uuid);
    }

    /**
     * get the employer from the database using the uuid.
     * @param uuid, the uuid which the database uses to retrieve the employer.
     */
    public static Employer getEmployer(String uuid) {
        return (Employer) DatabaseHandler.getDatumFromDatabase(uuid);
    }

    /**
     * get the student from the database using the uuid.
     * @param uuid, the uuid which the database uses to retrieve the student.
     */
    public static Student getStudent(String uuid) {
        return (Student) DatabaseHandler.getDatumFromDatabase(uuid);

    }
    /**
     * gets internships from the database using the uuid.
     * @param uuid, the uuid which the database uses to retrieve internships.
     */
    public static Internship getInternship(String uuid) {
        return (Internship) DatabaseHandler.getDatumFromDatabase(uuid);
    }

    /**
     * gets the resume from the database using the uuid.
     * @param uuid, the uuid which the database uses to retrieve the resume.
     */
    public static Resume getResume(String uuid) {
        return (Resume) DatabaseHandler.getDatumFromDatabase(uuid);
    }

    /**
     * gets the application from the database using the uuid.
     * @param uuid, the uuid which the database uses to retrieve the application.
     */
    public static Application getApplication(String uuid) {
        return (Application) DatabaseHandler.getDatumFromDatabase(uuid);
    }

    /**
     * retrieves datum from the database.
     * @param uuid, get uuid which is used to retrieve the datum.
     */
    private static Datum getDatumFromDatabase(String uuid) {
        DataTypes dataType = DatabaseHandler.getDataTypeFromUUID(uuid);
        Datum returnValue = null;
        JSONArray jsonArray = DatabaseHandler.readJsonArrayFromFile(dataType);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            if (jsonObject.get("uuid").equals(uuid))
            {
                returnValue = createDatabaseObjectFromJson(jsonObject);
                break;
            }
        }
        if (uuid.equals(ROOT.getUuid())) return ROOT;
        if (returnValue == null) DatabaseHandler.objectNotFound(uuid);
        return returnValue;   
    }

    /**
     * set the administrator for the program.
     * @param administrator, the administrator to be set.
     */
    public static void setAdministrator(Administrator administrator) {
        JSONObject jsonObject = DatabaseHandler.makeJsonObjectFromUser(administrator);
        jsonObject.put("adminCreatedBy", administrator.getAdminCreatedBY());
        jsonObject.put("username", administrator.getUsername());
        DatabaseHandler.finalizeDatum(administrator, jsonObject, DataTypes.ADMINISTRATOR);
    }

    /**
     * sets the employer for the program.
     * @param employer, the employer to be set.
     */
    public static void setEmployer(Employer employer) {
        JSONObject jsonObject = DatabaseHandler.makeJsonObjectFromUser(employer);
        jsonObject.put("companyName", employer.getCompanyName());
        jsonObject.put("internships", employer.getInternships());
        DatabaseHandler.finalizeDatum(employer, jsonObject, DataTypes.EMPLOYER);
    }

    /**
     * sets the student for the program.
     * @param student, the employer to be set.
     */
    public static void setStudent(Student student) {
        JSONObject jsonObject = DatabaseHandler.makeJsonObjectFromUser(student);
        jsonObject.put("firstName", student.getFirstName());
        jsonObject.put("middleName", student.getMiddleName());
        jsonObject.put("lastName", student.getLastName());
        jsonObject.put("university", student.getUniversity());
        jsonObject.put("graduationDate", student.getGraduationDate());
        jsonObject.put("resumes", student.getResumes());
        jsonObject.put("applications", student.getApplications());
        DatabaseHandler.finalizeDatum(student, jsonObject, DataTypes.STUDENT);
    }

    /**
     * sets the internship for the program.
     * @param internship, the internship to be set.
     */
    public static void setInternship(Internship internship) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", internship.getUuid());
        jsonObject.put("employerUuid", internship.getEmployerUuid());
        jsonObject.put("title", internship.getTitle());
        jsonObject.put("description", internship.getDescription());
        jsonObject.put("payRate", internship.getPayRate());
        jsonObject.put("hoursPerWeek", internship.getHoursPerWeek());
        jsonObject.put("numberOfWeeks", internship.getNumberOfWeeks());
        jsonObject.put("applications", internship.getApplications());
        jsonObject.put("skills", internship.getSkills());
        jsonObject.put("displayed", internship.getDisplayed());
        DatabaseHandler.finalizeDatum(internship, jsonObject, DataTypes.INTERNSHIP);
    }

    /**
     * sets the application for the program.
     * @param application, the application to be set.
     */
    public static void setApplication(Application application) {           
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", application.getUuid());
        jsonObject.put("studentUuid", application.getStudentUuid());
        jsonObject.put("resumeUuid", application.getResumeUuid());
        jsonObject.put("internshipUuid", application.getInternshipUuid());
        DatabaseHandler.finalizeDatum(application, jsonObject, DataTypes.APPLICATION);
    }

    /**
     * sets the resume for the program.
     * @param resume, the resume to be set.
     */
    public static void setResume(Resume resume) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", resume.getUuid());
        jsonObject.put("studentUuid", resume.getStudentUuid());
        jsonObject.put("title", resume.getTitle());
        jsonObject.put("description", resume.getDescription());
        jsonObject.put("skills", resume.getSkills());
        jsonObject.put("experience", resume.getExperience());
        jsonObject.put("education", resume.getEducation());
        DatabaseHandler.finalizeDatum(resume, jsonObject, DataTypes.RESUME);
    }

    /**
     * finailizes te datum.
     * @param datum, the datum which is being finalized.
     * @param jsonObject, ?
     * @param dataType, ?
     * 
     */
    private static void finalizeDatum(Datum datum, JSONObject jsonObject, DataTypes dataType) {
        DatabaseHandler.checkAndUpdateUuid(jsonObject, dataType);
        DatabaseHandler.writeJsonObjectToFile(jsonObject, dataType);
        datum.setUuid(jsonObject.get("uuid").toString());
        DatabaseHandler.ensureObjectUUIDRelationsAreUpdated(datum, false);
    }

    /**
     * deletes a given user from the database
     * @param uuid, the uuid which is referenced in order to delete the given user.
     */
    public static void deleteUser(String uuid) {
        DatabaseHandler.delete(uuid);
    }    

    /**
     * deletes a given resume from the database
     * @param uuid, the uuid which is referenced in order to delete the given resume.
     */
    public static void deleteResume(String uuid) {
        DatabaseHandler.delete(uuid);
    }  

    /**
     * deletes a given application from the database
     * @param uuid, the uuid which is referenced in order to delete the given application.
     */
    public static void deleteApplication(String uuid) {
        DatabaseHandler.delete(uuid);
    } 

    /**
     * deletes a given internship from the database
     * @param uuid, the uuid which is referenced in order to delete the given internship.
     */
    public static void deleteInternship(String uuid) {
        DatabaseHandler.delete(uuid);
    } 

    /**
     * Goes through the jsonArray and deletes database objects.
     * @param uuid, the uuid which is being deleted 
     */
    private static void delete(String uuid) {
        DataTypes dataType = DatabaseHandler.getDataTypeFromUUID(uuid);
        JSONArray jsonArray = DatabaseHandler.readJsonArrayFromFile(dataType);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            String nextUuid = jsonObject.get("uuid").toString(); 
            if (nextUuid.equals(uuid))
            {
                Datum datum = DatabaseHandler.getDatumFromDatabase(uuid);
                jsonArray.remove(object);
                DatabaseHandler.writeJsonArrayToFile(jsonArray, dataType);
                DatabaseHandler.ensureObjectUUIDRelationsAreUpdated(datum, true);
                break;
            }
        }
    }

    /**
     * returns the users which are in the database.
     * @return returnValue, returns an Arraylist of users which are in the database.
     */
    public static ArrayList<User> getUsers() {
        ArrayList<User> returnValue = new ArrayList<User>();
        JSONArray jsonArray = DatabaseHandler.readJsonArrayFromFile(DataTypes.STUDENT);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            User user = DatabaseHandler.createUserFromJson(jsonObject);
            returnValue.add(user);   
        }
        return returnValue;    
    }

    /**
     * returns the internships which are in the database and which are also displayed.
     * @return returnValue, returns the internships to be displayed.
     */
    public static ArrayList<Internship> getDisplayedInternships() {
        ArrayList<Internship> returnValue = new ArrayList<Internship>();
        JSONArray jsonArray = DatabaseHandler.readJsonArrayFromFile(DataTypes.INTERNSHIP);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            Internship internship = DatabaseHandler.createInternshipFromJson(jsonObject);            
            if (internship.getDisplayed()) returnValue.add(internship);
        }
        return returnValue;
    }

    /**
     * returns an Array List of all internships.
     * @return returnValue, returns an arraylist of all the internships inside of the database.
     */
    public static ArrayList<Internship> getAllInternships() {
        ArrayList<Internship> returnValue = new ArrayList<Internship>();
        JSONArray jsonArray = DatabaseHandler.readJsonArrayFromFile(DataTypes.INTERNSHIP);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            Internship internship = DatabaseHandler.createInternshipFromJson(jsonObject);
            returnValue.add(internship);
        }
        return returnValue;
    }

    /**
     * returns an array list of all internships by a specific employer.
     * @param employerUuid, the employer uuid to referance for the internships
     * @return returnValue, an arraylist of all internships by employer.
     */
    public static ArrayList<Internship> getInternshipsByEmployer(String employerUuid) {
        ArrayList<Internship> returnValue = new ArrayList<Internship>();    
        Employer employer = DatabaseHandler.getEmployer(employerUuid);
        ArrayList<String> internshipUuids = employer.getInternships();
        for (String uuid : internshipUuids) {
            Internship internship = DatabaseHandler.getInternship(uuid);
            if (internship == null) {
                continue;
            } else {
                returnValue.add(internship);
            } 
        }    
        return returnValue;
    } 

    /**
     * returns an arrayList of all resumes by a given student
     * @param studentUuid, the student uuid to reference for the resumes.
     * @return returnValue, the arrayList of all resumes by a specific student.
     */
    public static ArrayList<Resume> getResumesByStudent(String studentUuid) {
        ArrayList<Resume> returnValue = new ArrayList<Resume>();    
        Student student = DatabaseHandler.getStudent(studentUuid);
        if (student == null) {
            System.err.println("Student with UUID " + studentUuid + " does not exist.");
            System.exit(1);
        }
        ArrayList<String> resumeUuids = student.getResumes();
        for (String uuid : resumeUuids) {
            Resume resume = DatabaseHandler.getResume(uuid);
            if (resume == null) continue;
            else returnValue.add(resume); 
        }
        return returnValue;
    } 

    /**
     * returns an arrayList of all applications be a given student
     * @param studentUuid, the student uuid id to reference for students applications.
     * @return returnValue, the arrayList of all applications for a specific student.
     */
    public static ArrayList<Application> getApplicationsByStudent(String studentUuid) {
        ArrayList<Application> returnValue = new ArrayList<Application>();    
        Student student = DatabaseHandler.getStudent(studentUuid);
        if (student == null) {
            System.err.println("Student with UUID " + studentUuid + " does not exist.");
            System.exit(1);
        }
        ArrayList<String> applicationUuids = student.getApplications();
        for (String uuid : applicationUuids) {
            Application application = DatabaseHandler.getApplication(uuid);
            if (application == null) {
                continue;
            } else {
                returnValue.add(application);
            } 
        }    
        return returnValue;
    } 

    /**
     * returns an array list of all applications for a specific internship.
     * @param internshipUuid, the uuid to reference for the internships.
     * @return returnValue, the array list of all applications for a specific internship
     */
    public static ArrayList<Application> getApplicationsByInternship(String internshipUuid) {
        ArrayList<Application> returnValue = new ArrayList<Application>();    
        Internship internship = DatabaseHandler.getInternship(internshipUuid);
        if (internship== null) {
            System.err.println("Internship with UUID " + internshipUuid + " does not exist.");
            System.exit(1);
        }
        ArrayList<String> applicationUuids = internship.getApplications();
        for (String uuid : applicationUuids) {
            Application application = DatabaseHandler.getApplication(uuid);
            if (application == null) {
                continue;
            } else {
                returnValue.add(application);
            } 
        }    
        return returnValue;
    } 

    /**
     * returns an array list of applications based on specific employers
     * @param employerUuid, the employer uuid which is referenced from the database.
     * @return returnValue, an array list of applications by employers
     */
    public static ArrayList<Application> getApplicationsByEmployer(String employerUuid) {
        ArrayList<Application> returnValue = new ArrayList<Application>();    
        Employer employer = DatabaseHandler.getEmployer(employerUuid);
        for (String internshipUuid : employer.getInternships()) {
            returnValue.addAll(DatabaseHandler.getApplicationsByInternship(internshipUuid));
        }
        return returnValue;   
    }

    /**
     * returns the uuid prefix.
     * @param, the datatype to determine which prefix to return
     * @return, the prefix to be returned depending on the datatype.
     * 
     */
    private static String getUUIDPrefix(DataTypes dataType) {
        switch (dataType) {
            case ADMINISTRATOR: return "ADMINISTRATOR";
            case EMPLOYER: return "EMPLOYER";
            case STUDENT: return "STUDENT";
            case INTERNSHIP: return "INTERNSHIP";
            case RESUME: return "RESUME";
            case APPLICATION: return "APPLICATION";
            default: DatabaseHandler.invalidArgument("private String getUUIDPrefix(DataTypes dataType);"); return null;
        }
    } 

    /**
     * gets the database depending on the datatype parameter
     * @param dataType, the data type which is being refernced.
     * @return the database depending on the datatype. For USER, ADMINISTRATOR, EMPLOYER and STUdeNT you 
     *  will return the USER database, or for INTERNSHIP, RESUME, or APPLICATION datatype you will return the 
     *  respective database for them.
     */
    private static String getDatabase(DataTypes dataType) {
        switch (dataType) {
            case USER:
            case ADMINISTRATOR:
            case EMPLOYER:
            case STUDENT: return USERS_DB;
            case INTERNSHIP: return INTERNSHIPS_DB; 
            case RESUME: return RESUMES_DB;
            case APPLICATION: return APPLICATIONS_DB;
            default: DatabaseHandler.invalidArgument("private String getDatabase(DataTypes dataType);"); return null;
        }
    }

    /**
     * gets the data type from the UUID
     * @param uuid, the uuid that the datatype is referencing.
     * @return, returns the datatype depending on the uuid defined in the param.
     */
    public static DataTypes getDataTypeFromUUID(String uuid) {
        String uuidPrefix = uuid.substring(0, uuid.indexOf("-"));
        switch (uuidPrefix) {
            case "ADMINISTRATOR": return DataTypes.ADMINISTRATOR;
            case "EMPLOYER": return DataTypes.EMPLOYER;
            case "STUDENT": return DataTypes.STUDENT;
            case "INTERNSHIP": return DataTypes.INTERNSHIP;
            case "RESUME": return DataTypes.RESUME;
            case "APPLICATION": return DataTypes.APPLICATION;
            default: DatabaseHandler.invalidArgument("private DataTypes getDataTypeFromUUID(String uuid);"); return null;                
        }
    }

    /**
     * reads a json array from a given file
     * @param dataType, the datatype which is being used to get a specific database.
     * @return returnValue, returns a json array
     */
    private static JSONArray readJsonArrayFromFile(DataTypes dataType) {       
        JSONArray returnValue = null;
        String database = getDatabase(dataType);

        try {
            JSONParser dbParser = new JSONParser();
            returnValue = (JSONArray) dbParser.parse(new FileReader(database));
        } catch (FileNotFoundException e) {
            System.err.println(database + " not found");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("unhandled IOException");
            e.printStackTrace();
            System.exit(1);
        } catch (ParseException e) {
            returnValue = new JSONArray();
        }
        return returnValue;
    }

    /**
     * writes a json object to file.
     * @param jsonObject, json object to write to file.
     * @param dataType, data type to read from file
     * 
     */
    private static void writeJsonObjectToFile(JSONObject jsonObject, DataTypes dataType) {
        JSONArray jsonArray = readJsonArrayFromFile(dataType);
        for (Object object: jsonArray) {
            JSONObject jObject = (JSONObject) object;
            if (jObject.containsKey("uuid") && jObject.get("uuid").toString().equals(jsonObject.get("uuid").toString())) {
                jsonArray.remove(jObject);
                break;
            } 
        }
        jsonArray.add(jsonObject);
        String database = getDatabase(dataType);
        //Write JSON file
        try (FileWriter dbWriter = new FileWriter(database)) {
            //We can write any JSONArray or JSONObject instance to the file
            dbWriter.write(jsonArray.toJSONString());
        } catch (IOException e) {
            System.err.println("Unhandled IOException Found");
            e.printStackTrace();
            System.exit(1);
        }
    }

     /**
      * writes json array to file.
     * @param jsonArray, the json array to write to file
     * @param dataType, the data type to get from the database.
     * 
     */
    private static void writeJsonArrayToFile(JSONArray jsonArray, DataTypes dataType) {
        String database = getDatabase(dataType);
        //Write JSON file
        try (FileWriter dbWriter = new FileWriter(database)) {
            //We can write any JSONArray or JSONObject instance to the file
            dbWriter.write(jsonArray.toJSONString());
        } catch (IOException e) {
            System.err.println("Unhandled IOException Found");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * makes json objet from user object.
     * @param user, the user to make the json object from
     * @return jsonObject, the json object to make.
     */
    private static JSONObject makeJsonObjectFromUser(User user) {
        JSONObject jsonObject = new JSONObject(); 
        jsonObject.put("uuid", user.getUuid());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("phoneNumber", user.getPhoneNumber());
        jsonObject.put("password", user.getPassword());
        jsonObject.put("city", user.getCity());
        jsonObject.put("state", user.getState());
        return jsonObject;
    }
    /**
     * creates database object from json.
     * @param jsonObject, json object to be created inside the database
     * @return, the databaseHandler object depending on the datatype defined 
     *  within the method from the json object.
     */
    private static Datum createDatabaseObjectFromJson(JSONObject jsonObject) {
        DataTypes dataType = DatabaseHandler.getDataTypeFromUUID(jsonObject.get("uuid").toString());
        switch (dataType) {
            case ADMINISTRATOR: return DatabaseHandler.createAdministratorFromJson(jsonObject);
            case EMPLOYER: return DatabaseHandler.createEmployerFromJson(jsonObject);
            case STUDENT: return DatabaseHandler.createStudentFromJson(jsonObject);
            case INTERNSHIP: return DatabaseHandler.createInternshipFromJson(jsonObject);
            case APPLICATION: return DatabaseHandler.createApplicationFromJson(jsonObject);
            case RESUME: return DatabaseHandler.createResumeFromJson(jsonObject);
            default: DatabaseHandler.invalidArgument("private Datum createDatabaseObjectFromJson(JSONObject jsonObject);"); return null;
        }
    }
    /**
     * creates user from json object.
     * @param jsonObject, the json object to create the user from.
     * @return a new user depending on the json object parameter.
     */
    private static User createUserFromJson(JSONObject jsonObject) {
        String uuid = jsonObject.get("uuid").toString();
        String email = jsonObject.get("email").toString();
        String phoneNumber = jsonObject.get("phoneNumber").toString();
        String password = jsonObject.get("password").toString();
        String city = jsonObject.get("city").toString();
        String state = jsonObject.get("state").toString();
        return new User(uuid, email, phoneNumber, password, city, state);
    }
    /**
     * creates administrator from json object.
     * @param jsonObject, the json object to create the administrator from.
     * @return a new administrator depending on the json object parameter. 
     */
    private static Administrator createAdministratorFromJson(JSONObject jsonObject) {
        String uuid = jsonObject.get("uuid").toString();
        String email = jsonObject.get("email").toString();
        String phoneNumber = jsonObject.get("phoneNumber").toString();
        String password = jsonObject.get("password").toString();
        String city = jsonObject.get("city").toString();
        String state = jsonObject.get("state").toString();
        String adminCreatedBy = jsonObject.get("adminCreatedBy").toString();
        String username = jsonObject.get("username").toString();        
        return new Administrator(uuid, email, phoneNumber, password, city, state, adminCreatedBy, username);
    }

    /**
     * creates employer from json object.
     * @param jsonObject, the json object to create the employer from.
     * @return a new employer depending on the json object parameter.
     */
    private static Employer createEmployerFromJson(JSONObject jsonObject) {
        String uuid = jsonObject.get("uuid").toString();
        String email = jsonObject.get("email").toString();
        String phoneNumber = jsonObject.get("phoneNumber").toString();
        String password = jsonObject.get("password").toString();
        String city = jsonObject.get("city").toString();
        String state = jsonObject.get("state").toString();
        String companyName = jsonObject.get("companyName").toString();
        ArrayList<String> internships = DatabaseHandler.createStringArrayListFromJson((JSONArray) jsonObject.get("internships"));
        return new Employer(uuid, email, phoneNumber, password, city, state, companyName, internships);
    }
    /**
     * creates student from json object.
     * @param jsonObject, the json object to create the student from.
     * @return a new student depending on the json object parameter.
     */
    private static Student createStudentFromJson(JSONObject jsonObject) {
        Student returnValue = null;
        String uuid = jsonObject.get("uuid").toString();
        String email = jsonObject.get("email").toString();
        String phoneNumber = jsonObject.get("phoneNumber").toString();
        String password = jsonObject.get("password").toString();
        String city = jsonObject.get("city").toString();
        String state = jsonObject.get("state").toString();
        String firstName = jsonObject.get("firstName").toString();
        String middleName = jsonObject.get("middleName").toString();
        String lastName = jsonObject.get("lastName").toString();
        String university = jsonObject.get("university").toString();
        String graduationDate = jsonObject.get("graduationDate").toString();
        ArrayList<String> resumes = DatabaseHandler.createStringArrayListFromJson((JSONArray) jsonObject.get("resumes"));
        ArrayList<String> applications = DatabaseHandler.createStringArrayListFromJson((JSONArray) jsonObject.get("applications"));
        returnValue = new Student(
            uuid, email, phoneNumber, password, city, state, firstName, middleName, lastName, university, graduationDate, resumes, applications
        );
        return returnValue;
    }
    /**
     * creates internship from json object.
     * @param jsonObject, the json object to create the internship from.
     * @return a new insternship depending on the json object parameter.
     */
    private static Internship createInternshipFromJson(JSONObject jsonObject) {
        String uuid = jsonObject.get("uuid").toString();
        String employerUuid = jsonObject.get("employerUuid").toString();
        String title = jsonObject.get("title").toString();
        String description = jsonObject.get("description").toString();
        double payRate = Double.parseDouble(jsonObject.get("payRate").toString());
        int hoursPerWeek = Integer.parseInt(jsonObject.get("hoursPerWeek").toString());
        int numberOfWeeks = Integer.parseInt(jsonObject.get("numberOfWeeks").toString());
        ArrayList<String> skills = DatabaseHandler.createStringArrayListFromJson((JSONArray) jsonObject.get("skills"));
        ArrayList<String> applications = DatabaseHandler.createStringArrayListFromJson((JSONArray) jsonObject.get("applications"));
        boolean displayed = Boolean.parseBoolean((jsonObject.get("displayed").toString()));
        return new Internship(uuid, employerUuid, title, description, payRate, hoursPerWeek, numberOfWeeks, skills, applications, displayed);
    }
    /**
     * creates resume from json object.
     * @param jsonObject, the json object to create the resume from.
     * @return a new resume depending on the json object parameter.
     */
    private static Resume createResumeFromJson(JSONObject jsonObject) {
        String uuid = jsonObject.get("uuid").toString();
        String studentUuid = jsonObject.get("studentUuid").toString();
        String title = jsonObject.get("title").toString();
        String description = jsonObject.get("description").toString();
        ArrayList<String> skills = DatabaseHandler.createStringArrayListFromJson((JSONArray) jsonObject.get("skills"));
        ArrayList<String> experience = DatabaseHandler.createStringArrayListFromJson((JSONArray) jsonObject.get("experience"));
        ArrayList<String> education = DatabaseHandler.createStringArrayListFromJson((JSONArray) jsonObject.get("education"));
        return new Resume(uuid, studentUuid, title, description, skills, experience, education);
    }
    /**
     * creates application from json object.
     * @param jsonObject, the json object to create the application from.
     * @return a new application depending on the json object parameter. 
     */
    private static Application createApplicationFromJson(JSONObject jsonObject) {
        String uuid = jsonObject.get("uuid").toString();
        String studentUuid = jsonObject.get("studentUuid").toString();
        String resumeUuid = jsonObject.get("resumeUuid").toString();
        String internshipUuid = jsonObject.get("internshipUuid").toString();
        return new Application(uuid, studentUuid, resumeUuid, internshipUuid);
    }

    /**
     * creates string array list from json object.
     * @param jsonObject, the json object to create the string array list from.
     * @return returnValue, a string array list from the json array.
     */
    private static ArrayList<String> createStringArrayListFromJson(JSONArray jsonArray) {
        ArrayList<String> returnValue = new ArrayList<String>();     
        if (jsonArray != null) { 
            for (int i = 0; i < jsonArray.size(); i++){ 
                returnValue.add(jsonArray.get(i).toString());
            } 
        } 
        return returnValue;
    }
    /**
     * checks and updates the uuid.
     * @param jsonObject, the json object in which the uuid is being updated using
     * @param dataType, the datatype which is being reference for uuid updating.
     * @return, 
     */
    private static JSONObject checkAndUpdateUuid(JSONObject jsonObject, DataTypes dataType) {
        jsonObject.replace("uuid", "undefined", DatabaseHandler.createNewUuid(dataType));        
        return jsonObject; 
    }
    /**
     * creates a new random uuid.
     * @param dataType, the datatype which is being referenced for uuid creation.
     * @return returnValue, returns a random uuid value.
     */
    private static String createNewUuid(DataTypes dataType) {
        String returnValue = DatabaseHandler.getUUIDPrefix(dataType); 
        returnValue += "-" + UUID.randomUUID();
        return returnValue;
    }
    /**
     * ensures that the Objects formed from uuids relations are updated.
     * @param datum, a passed object from which uuids are managed.
     * @param delete, a parameter which determines how to modify uuid relations.
     * 
     */
    private static void ensureObjectUUIDRelationsAreUpdated(Datum datum, boolean delete) {
        DataTypes dataType = DatabaseHandler.getDataTypeFromUUID(datum.getUuid());
        switch (dataType) {
            case ADMINISTRATOR: 
                Administrator administrator = (Administrator) datum;
                DatabaseHandler.ensureObjectUUIDRelationsAreUpdated(administrator, delete);
                break;
            case EMPLOYER: 
                Employer employer = (Employer) datum;
                DatabaseHandler.ensureObjectUUIDRelationsAreUpdated(employer, delete);
                break;
            case STUDENT:
                Student student = (Student) datum;
                DatabaseHandler.ensureObjectUUIDRelationsAreUpdated(student, delete);
                break;
            case INTERNSHIP:
                Internship internship = (Internship) datum;
                DatabaseHandler.ensureObjectUUIDRelationsAreUpdated(internship, delete);
                break;
            case APPLICATION:
                Application application = (Application) datum;
                DatabaseHandler.ensureObjectUUIDRelationsAreUpdated(application, delete);
                break;
            case RESUME:
                Resume resume = (Resume) datum;
                DatabaseHandler.ensureObjectUUIDRelationsAreUpdated(resume, delete);
                break;
            default: DatabaseHandler.invalidArgument("private void ensureObjectUUIDRelationsAreUpdated(Datum datum, boolean delete);");
        }
    }

    /**
     * @param
     * 
     */
    private static void ensureObjectUUIDRelationsAreUpdated(Administrator administrator, boolean delete) {
    }
    /**
     * @param
     * 
     */
    private static void ensureObjectUUIDRelationsAreUpdated(Employer employer, boolean delete) {
        // ArrayList<String> internships = employer.getInternships();
    }
    /**
     * @param
     * 
     */
    private static void ensureObjectUUIDRelationsAreUpdated(Student student, boolean delete) {
        // ArrayList<String> resumes = student.getResumes();
        // ArrayList<String> applications = student.getApplications();
    }


    /**
     * ensures that the objects formed from uuids relations are updated.
     * @param internship, parameter which is referenced to ensure relation.
     * @param delete, a parameter which determines how to modify uuid relations.
     * 
     */
    private static void ensureObjectUUIDRelationsAreUpdated(Internship internship, boolean delete) {
        Employer employer = DatabaseHandler.getEmployer(internship.getEmployerUuid());
        boolean employerChanged = false; 

        if (delete) {
            employer.removeInternship(internship.getUuid());   
            employerChanged = true;  

            ArrayList<String> applications = internship.getApplications();
            for (String applicationUuid: applications) {
                deleteApplication(applicationUuid);
            }
        } else {
            employer.addInternship(internship.getUuid());   
            employerChanged = true;        
        }            

        if (employerChanged) DatabaseHandler.setEmployer(employer);        
    }
    /**
     * ensures that the objects formed from uuids relations are updated.
     * @param resume, parameter which is referenced to ensure relation.
     * @param delete, a parameter which determines how to modify uuid relations.
     * 
     */
    private static void ensureObjectUUIDRelationsAreUpdated(Resume resume, boolean delete) {
        Student student = DatabaseHandler.getStudent(resume.getStudentUuid());
        boolean studentChanged = false;

        if (delete) {
            student.removeResume(resume.getUuid()); 
            studentChanged = true;
        } else {
            student.addResume(resume.getUuid()); 
            studentChanged = true;
        }
        
        if (studentChanged) DatabaseHandler.setStudent(student);
    }
    /**
     * ensures that the objects formed from uuids relations are updated.
     * @param application, parameter which is referenced to ensure relation.
     * @param delete, a parameter which determines how to modify uuid relations.
     * 
     */
    private static void ensureObjectUUIDRelationsAreUpdated(Application application, boolean delete) {
        // String resumeUuid = application.getResumeUuid();
        Student student = DatabaseHandler.getStudent(application.getStudentUuid());
        Internship internship = DatabaseHandler.getInternship(application.getInternshipUuid());
        boolean studentChanged = true;
        boolean internshipChanged = true;

        if (delete) {
            student.removeApplication(application.getUuid());
            internship.removeApplication(application.getUuid()); 
            studentChanged = true;
            internshipChanged = true;
        } else {
            student.addApplication(application.getUuid());
            internship.addApplication(application.getUuid());
            studentChanged = true;
            internshipChanged = true;            
        }
        
        if (studentChanged) DatabaseHandler.setStudent(student);
        if (internshipChanged) DatabaseHandler.setInternship(internship);
    }
    /**
     * determines whether or not an object is found based on its uuid value.
     * @param uuid, the value in which is referenced in order to determine if an object is found or not.
     * 
     */
    private static void objectNotFound(String uuid) {
        System.err.println("Datum with UUID " + uuid + " was not found");
        
        System.exit(1);
    }
    /**
     * determine if an argument is valid or not.
     * @param function, function to be checked for validity.
     * 
     */
    private static void invalidArgument(String function) {
        System.err.println("You supplied an invalid argument that broke the system.");
        System.err.println("Function: " + function);
        System.exit(1);
    }
    /**
     * The database engine class.
     */
    private static class DatabaseEngine {
        /**
         * a method for defining the menu for the database engine.
         */
        private static void Menu() {
            menu: while (true) {
                InputHandler.resetScreen("Database Engine");
                int option = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(
                    new String[] { "Seed Database" }
                );
                switch (option) {
                    case -1: break menu;
                    case 0: DatabaseEngine.SeedDatabase();
                }
            }
            DatabaseEngine.Finalize();
        }
        /**
         * a method which contains all of the different seeded student, and internship objects.
         */
        private static void SeedDatabase() {
        

            String password = "password";

            Student Raye = new Student("STUDENT-5cabc731-a654-4c83-82a2-0ced3be29bcb", "KRH10@email.sc.edu", "8035567777",
                password, "West Columbia", "South Carolina", "Kathleen", "Raye", "Humphries", "USC", "August 2022"
            );
    
            Student Claude = new Student("STUDENT-ecb0db60-ab0f-4ec2-b7d3-680b200c33d7", "cgreid@email.sc.edu", "8037784456",
                password, "Columbia", "South Carolina", "Claude", "Reide", "Jones", "USC", "Fall 2024"
            );
    
            Student Kayla = new Student("STUDENT-0d9be578-c8e8-4949-8201-e3b26cbe1bb3", "kwaddy@email.sc.edu", "8037784456",
                password, "Columbia", "South Carolina", "Kayla", "Waddy", "USC", "2024", ""
            );
    
            Student Timmy = new Student("STUDENT-a5af4e47-8aae-446a-b591-5fc1958ba7d5", "tkranz@email.sc.edu", "8037784456",
                password, "Columbia", "South Carolina", "Timmy", "Kranz", "USC", "2024", ""
            );
    
            Employer employer1 = new Employer("EMPLOYER-430cc6e5-e1e2-4812-b5de-9b25270dc1dc", "wellsfargo@gmail.com", "9990009323", 
                password, "Denver", "Colorada", "Wells Fargo"
            );
    
            Employer employer2 = new Employer("EMPLOYER-04d41cce-20dc-4dbb-966d-26a99bfc3437", "goodwill@gmail.com", "87239453452", 
                password, "New York", "New York", "Good Will"
            );
    
            Internship internship1 = new Internship("INTERNSHIP-a0bfdde8-7060-4c75-ab47-fe99dd7d2fd8", "EMPLOYER-430cc6e5-e1e2-4812-b5de-9b25270dc1dc", 
                "Wells Fargo Junior Developer Front End", "Learn to code for Wells Fargo", 15, 30, 45 
            );
    
            Internship internship2 = new Internship("INTERNSHIP-54058792-aeef-4220-80af-84f5599745ee", "EMPLOYER-430cc6e5-e1e2-4812-b5de-9b25270dc1dc", 
                "Wells Fargo Junior Developer Back End", "Learn to code for Wells Fargo", 17, 30, 48 
            );
    
            Internship internship3 = new Internship("INTERNSHIP-292aee87-21c7-4ff7-b408-4055d91897d0", "EMPLOYER-430cc6e5-e1e2-4812-b5de-9b25270dc1dc", 
                "Wells Fargo Junior Developer DBA", "Learn to code for Wells Fargo", 20, 32, 55 
            );
    
            Internship internship4 = new Internship("INTERNSHIP-92c4d2d1-60ce-4954-8451-0e74f21903bb", "EMPLOYER-04d41cce-20dc-4dbb-966d-26a99bfc3437", 
                "Good Will Junior Developer Front End", "Learn to code for Good Will", 10, 20, 35 
            );
    
            Internship internship5 = new Internship("INTERNSHIP-40e9953d-e79e-4aeb-986f-11556693b62b", "EMPLOYER-04d41cce-20dc-4dbb-966d-26a99bfc3437", 
                "Good Will Junior Developer Back End", "Learn to code for Good Will", 10, 20, 35 
            );
    
            Internship internship6 = new Internship("INTERNSHIP-092fc783-b2bf-47e5-a3fa-5df371e4e0bf", "EMPLOYER-04d41cce-20dc-4dbb-966d-26a99bfc3437", 
                "Good Will Junior Developer DBA", "Learn to code for Good Will", 10, 20, 35 
            );
    
            DatabaseHandler.setStudent(Raye);
            DatabaseHandler.setStudent(Claude);
            DatabaseHandler.setStudent(Kayla);
            DatabaseHandler.setStudent(Timmy);
            DatabaseHandler.setEmployer(employer1);
            DatabaseHandler.setEmployer(employer2);
            DatabaseHandler.setInternship(internship1);
            DatabaseHandler.setInternship(internship2);
            DatabaseHandler.setInternship(internship3);
            DatabaseHandler.setInternship(internship4);
            DatabaseHandler.setInternship(internship5);
            DatabaseHandler.setInternship(internship6);        
        }        
    
        private static void Finalize() {
            System.out.println("Exiting DatabaseEngine.");
        }
    }

    public static void main(String[] args) {
        DatabaseEngine.Menu();        
    }
}