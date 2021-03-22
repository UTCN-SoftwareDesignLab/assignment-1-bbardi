package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientValidator {
    private final String PNC_VALIDATION_REGEX = "^[1-8]\\d{12}$";
    private final String IDCARD_VALIDATION_REGEX = "^[A-Z][A-Z]\\d{6}$";
    private final String NAME_VALIDATION_REGEX = "^[A-Z][a-z]+ [A-Z][a-z]+$";
    private final String ADDRESS_VALIDATION_REGEX = "^[A-Z][A-Za-z0-9\\s]+$";
    private final Client client;
    private final List<String> errorList;

    public ClientValidator(Client client) {
        this.client = client;
        this.errorList = new ArrayList<>();
    }

    public boolean validate(){
        validateName(client.getName());
        validatePNC(client.getPersonalNumericCode());
        validateIDCard(client.getIdentityCard());
        validateAddress(client.getAddress());
        return errorList.isEmpty();
    }

    private void validatePNC(String PNC){
        if(!Pattern.compile(PNC_VALIDATION_REGEX).matcher(PNC).matches()){
            errorList.add("Invalid Personal Numeric Code");
        }
    }
    private void validateIDCard(String IDCard){
        if(!Pattern.compile(IDCARD_VALIDATION_REGEX).matcher(IDCard).matches()){
            errorList.add("Invalid ID Card");
        }
    }
    private void validateName(String name){
        if(!Pattern.compile(NAME_VALIDATION_REGEX).matcher(name).matches()){
            errorList.add("Invalid Name");
        }
    }
    private void validateAddress(String address){
        if(!Pattern.compile(ADDRESS_VALIDATION_REGEX).matcher(address).matches()){
            errorList.add("Invalid Address");
        }
    }
    public List<String> getErrors(){
        return errorList;
    }
}
