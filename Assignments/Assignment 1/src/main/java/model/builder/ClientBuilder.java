package model.builder;

import model.Client;

public class ClientBuilder {
    private Client client;

    public ClientBuilder(){
        client = new Client();
    }

    public ClientBuilder setId(Long id){
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name){
        client.setName(name);
        return this;
    }

    public ClientBuilder setIdentityCard(String identityCard){
        client.setIdentityCard(identityCard);
        return this;
    }

    public ClientBuilder setPersonalNumericCode(String personalNumericCode){
        client.setPersonalNumericCode(personalNumericCode);
        return this;
    }

    public ClientBuilder setAddress(String address){
        client.setAddress(address);
        return this;
    }

    public Client build(){
        return client;
    }
}
