package model;

public class Utility {
    private String utilityName;
    private String clientIdentifier_validation_regex;

    public String getClientIdentifier_validation_regex() {
        return clientIdentifier_validation_regex;
    }

    public void setClientIdentifier_validation_regex(String clientIdentifier_validation_regex) {
        this.clientIdentifier_validation_regex = clientIdentifier_validation_regex;
    }

    public String getUtilityName() {
        return utilityName;
    }

    public void setUtilityName(String utilityName) {
        this.utilityName = utilityName;
    }

    @Override
    public String toString() {
        return utilityName;
    }
}
