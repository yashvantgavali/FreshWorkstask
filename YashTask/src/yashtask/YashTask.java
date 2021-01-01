package yashtask;
/**
 *
 * @author Yashvant
 */
import java.io.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//JSONArray and JSONObject present in this package

public class YashTask
{
    Scanner sn=new Scanner(System.in);
    
    public void create()
    {
       try
      {
          JSONObject employeeDetails = new JSONObject();
          
          System.out.println("Please Enter The Employee ID");
          String empId=sn.next();
          //Length of key should be less than 32 char
          while(empId.length()>32)
         {
            System.out.println("Please enter the key less than 32 char");
            empId=sn.next();
         }
          
          FileReader reader;
          FileWriter file ;
        
          try
         {
           reader=new FileReader("employees.json");
         } 
        catch(FileNotFoundException e)
         {
           //if database is not created then create it
           file= new FileWriter("employees.json");
           JSONArray employeeList =new JSONArray();
           file.write(employeeList.toJSONString());
           file.flush();
         }
       
           reader = new FileReader("employees.json");
           JSONParser jsonParser = new JSONParser();
           Object obj = jsonParser.parse(reader);
 
           JSONArray employeeList = (JSONArray) obj;
           
           Iterator<JSONObject> objectIterator =  employeeList.iterator();
           boolean flag=true;
   
           while(objectIterator.hasNext())
         {
             JSONObject object = objectIterator.next();
             String empid = (String) object.get("EmpId");
             if(empid.equals(empId))
           {
               System.out.println("Data Alreday present for this Employee,Please enter unique EmpId");
               flag=false;
               break;
               
            }
         }
        //If data is unique then only insert it in Database
        if(flag)
        {
            System.out.println("Please Enter The Details of Employee ");
            System.out.println("FirstName ");
            String firstName=sn.next();
            System.out.println("LastName ");
            String lastName=sn.next();
            System.out.println("Department ");
            String department=sn.next();
            employeeDetails.put("EmpId", empId);
            employeeDetails.put("FirstName", firstName);
            employeeDetails.put("LastName", lastName);
            employeeDetails.put("Department", department);
         
            employeeList.add(employeeDetails);
       
            file = new FileWriter("employees.json");
            file.write(employeeList.toJSONString());
            file.flush();
            
            System.out.println("Data Added Succesfully ! ");
        }
       //If client is trying to use database in unexpected way
      }catch (FileNotFoundException e) {
          System.out.println("Database Not Created Succesfully");
      }catch (IOException e) {
           System.out.println("Sorry The Database does not contain Data in JSON format");
       } catch (ParseException e) {
            System.out.println("Sorry The Database does not contain Data in JSON format");
       }
       
    }
   
  public void read()
   {
        JSONParser jsonParser = new JSONParser();
        //Create a FileReader object to read the data 
        try (FileReader reader = new FileReader("employees.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray employeeList = (JSONArray) obj;
             
            System.out.println("Enter the EmpId which you want to search");
            String key=sn.next();
           
            Iterator<JSONObject> objectIterator =  employeeList.iterator();
            boolean flag=true;
           
            while(objectIterator.hasNext())
           {
               JSONObject object = objectIterator.next();
               String empId = (String) object.get("EmpId");
               
               if(key.equals(empId))//key match found then print JSON object
             {
                System.out.println("Record Found ");
                System.out.println(object);
                flag=false;
                break;
             }
           }
             if(flag)
          {
              System.out.println("Sorry Not Record Found ");
          }
    
        //If client is trying to use database in unexpected way
        } catch (FileNotFoundException e) {
           System.out.println("Sorry first write data in the Database");
        } catch (IOException e) {
           System.out.println("Sorry Database does not contain data in JSON from");
        } catch (ParseException e) {
            System.out.println("Sorry first write data in the Database");
        }
   
    }

   
  public void deleteData()
  {
     JSONParser jsonParser = new JSONParser();
     
     try (FileReader reader = new FileReader("employees.json"))
     {
        Object obj = jsonParser.parse(reader);
        JSONArray employeeList = (JSONArray) obj;
             
        System.out.println("Enter the EmpId which you want to Delete");
        String key=sn.next();

        Iterator<JSONObject> objectIterator =  employeeList.iterator();
        boolean flag=true;
       
        while(objectIterator.hasNext())
       {
           JSONObject object = objectIterator.next();
           String empId = (String) object.get("EmpId");
           
           if(key.equals(empId))
          {
            employeeList.remove(object);
            System.out.println("Employee Data Deleted Succesfully");
            FileWriter file = new FileWriter("employees.json");
            file.write(employeeList.toJSONString());
            file.flush();
            flag=false;
            break;
          }
       
       }
       if(flag)
       {
          System.out.println("Sorry Not Record Found ");
       }
      //If client is trying to use database in unexpected way 
     } catch (FileNotFoundException e) {
          System.out.println("Soory first write Data in the Database");
     } catch (IOException e) {
            System.out.println("Soory Database Does not contain Data in JSON from");
     } catch (ParseException e) {
            System.out.println("Soory first write Data in the Database");
     }
       
  }
  
  public void showAll()
  {
       JSONParser jsonParser = new JSONParser();
       try (FileReader reader = new FileReader("employees.json"))
       {
           Object obj = jsonParser.parse(reader);
           JSONArray employeeList = (JSONArray) obj;
           System.out.println(employeeList);
        //If client is trying to use database in unexpected way
       } catch (FileNotFoundException e) {
           System.out.println("Sorry first write data in the Database");
        } catch (IOException e) {
           System.out.println("Sorry Database does not contain data in JSON from");
        } catch (ParseException e) {
            System.out.println("Sorry first write data in the Database");
        }
  
  }

  public static void main(String args[])
  {
  
  YashTask in = new YashTask();
  Scanner sn=new Scanner(System.in);
  int ch;
  
  while(true)
  {
       System.out.println("Please Enter Your Choice");
       System.out.println("1.ADD Employee Data To Database");
       System.out.println("2.Read Employee Data From Database");
       System.out.println("3.Delete Employee Data from Database");
       System.out.println("4.Show All Data from Database");
       System.out.println("5.Done with my work! Please Exit");
       ch=sn.nextInt();
 
  switch(ch)
  {
      case 1:
          in.create();
          break;
      case 2:
          in.read();
          break;
      case 3:
          in.deleteData();
          break;
      case 4:
          in.showAll();
          break;
      case 5:
          break;
      default:
          System.out.println("Please enter the valid Choice");
  }
  if(ch==5)
  {
      break;
  }
  }
 
}
}


 
