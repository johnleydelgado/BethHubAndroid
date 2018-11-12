package com.robert.bethub.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class User extends RealmObject {

    public int id;
    public String email,username,status;
    public RealmList<String> title = new RealmList<String>();

}
