package ca.macewan.cmpt305;

import java.util.ArrayList;
import java.util.List;

public class CreateProperty {
    public static PropertyAssessment CreatePropertyAssessment(String line) {
        String[] tok = line.split(",");
        for (int i=0; i < tok.length; i++) {
            tok[i] = tok[i].replaceAll("\"", "");
        }

        int accountNumber = Integer.parseInt(tok[0]);
        Address address = new Address(tok[1], tok[2], tok[3]);
        boolean garage = tok[4].equalsIgnoreCase("y");
        Neighbourhood neighbourhood =new Neighbourhood(tok[5], tok[6], tok[7]);
        int assessedValue = Integer.parseInt(tok[8]);
        Location location = new Location(Double.parseDouble(tok[9]), Double.parseDouble(tok[10]));
        List<AssessmentClass> assessmentClass = new ArrayList<>();
        assessmentClass.add(new AssessmentClass(tok[15], Integer.parseInt(tok[12])));

        if (tok.length > 16) {assessmentClass.add(new AssessmentClass(tok[16], Integer.parseInt(tok[13])));}
        if (tok.length > 17) {assessmentClass.add(new AssessmentClass(tok[16], Integer.parseInt(tok[13])));}

        return new PropertyAssessment(accountNumber, address, garage, neighbourhood, assessedValue, location,
                assessmentClass);

    }

}

