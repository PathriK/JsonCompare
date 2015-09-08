package in.pathri.jsoncompare;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Compare {
	public static void main(String[] args) {
		try {			
			String response = new Scanner(new File("resources/response.json")).useDelimiter("\\Z").next();
			String update = new Scanner(new File("resources/update.json")).useDelimiter("\\Z").next();
		
			compare(update,response);
			
		} catch (FileNotFoundException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void compare(String update,String response) throws ParseException{
		JSONParser parser=new JSONParser();
		
		Object objResponse = parser.parse(response);
		Object objUpdate = parser.parse(update);
		
		String classType = objUpdate.getClass().getSimpleName(); 
		
		switch (classType) {
		case "JSONArray":
			compare("",(JSONArray)objResponse,(JSONArray)objUpdate);
			break;
		case "JSONObject":
			compare((JSONObject)objResponse,(JSONObject)objUpdate);
			break;
		case "String":
			System.out.println("String in compare string" + objResponse + objUpdate);
			break;				
		default:
			System.out.println("Unknown: " + classType);
			break;
		}

	}
	
	private static void compare(JSONObject response,JSONObject update){
		for (Iterator<String> updateItr = update.keySet().iterator(); updateItr.hasNext();) {
			String updateKey = updateItr.next();
			Object updateVal = update.get(updateKey);
			if(response.containsKey(updateKey)){
			Object responseVal = response.get(updateKey);
			String classType = updateVal.getClass().getSimpleName();
			switch (classType) {
			case "JSONArray":
				compare(updateKey,(JSONArray)responseVal,(JSONArray)updateVal);
				break;
			case "JSONObject":
				compare((JSONObject)responseVal,(JSONObject)updateVal);
				break;
			case "String":
				if(updateVal.equals(responseVal)){
					System.out.println("PASS: " + updateKey + ":" + (String)updateVal + "==" + (String)responseVal);					
				}else{
					System.out.println("FAIL: " + updateKey + ":" + (String)updateVal + "==" + (String)responseVal);
				}
				break;
			default:
				System.out.println("Defaault in compare jsonobj");
				break;
			}
			}else{
				System.out.println("ERROR");
			}
			
		}
	}

	private static void compare(String key,JSONArray response,JSONArray update){
		for (int i = 0; i < update.size(); i++) {
			Object updateElem = update.get(i);
			Object responseElem = response.get(i);
			String classType = updateElem.getClass().getSimpleName();
			switch (classType) {
			case "JSONArray":
				compare(key,(JSONArray)responseElem,(JSONArray)updateElem);
				break;
			case "JSONObject":
				compare((JSONObject)responseElem,(JSONObject)updateElem);
				break;
			case "String":
				if(responseElem.equals(updateElem)){
					System.out.println("PASS: " + key + ":" + (String)updateElem + "==" + (String)responseElem);
				}else{
					System.out.println("FAIL: " + key + ":" + (String)updateElem + "==" + (String)responseElem);
				}
				break;
			default:
				System.out.println("Defaault in compare jsonarray");
				break;
			}			
		}
	}
}

