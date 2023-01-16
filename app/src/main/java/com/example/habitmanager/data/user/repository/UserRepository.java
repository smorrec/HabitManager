package com.example.habitmanager.data.user.repository;

import com.example.habitmanager.R;
import com.example.habitmanager.data.user.model.User;

import java.util.ArrayList;

public class UserRepository {
    private ArrayList<User> list;
    private static UserRepository instance;

    private UserRepository(){
        initialize();
    }

    public static UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    public ArrayList<User> getList(){
        return list;
    }

    public void addUser(User user){
        list.add(user);
    }

    public boolean login(String email, String password){
        User user = new User(email, password);
        return list.contains(user);
    }

    private void initialize(){
        list = new ArrayList<>();
        list.add(new User("sergio@sergio.com", "sergio", "Sergio", "Morales", R.drawable.profile));
        list.add(new User("aaaa@aaaaa.com", "A+a12345678", "aaaaaaaaaaaa", "aaaaaaaaaaaa", 1));
        list.add(new User("bbbb@bbbbb.com", "bbbbbbbbbbbb", "bbbbbbbbbbbb", "bbbbbbbbbbbb", 2));
        list.add(new User("cccc@ccccc.com", "cccccccccccc", "cccccccccccc", "cccccccccccc", 3));
        list.add(new User("dddd@ddddd.com", "dddddddddddd", "dddddddddddd", "dddddddddddd", 4));
        list.add(new User("eeee@eeeee.com", "eeeeeeeeeeee", "eeeeeeeeeeee", "eeeeeeeeeeee", 5));
        list.add(new User("ffff@fffff.com", "ffffffffffff", "ffffffffffff", "ffffffffffff", 6));
        list.add(new User("gggg@ggggg.com", "gggggggggggg", "gggggggggggg", "gggggggggggg", 7));
        list.add(new User("hhhh@hhhhh.com", "hhhhhhhhhhhh", "hhhhhhhhhhhh", "hhhhhhhhhhhh", 8));
        list.add(new User("iiii@iiiii.com", "iiiiiiiiiiii", "iiiiiiiiiiii", "iiiiiiiiiiii", 9));
        list.add(new User("jjjj@jjjjj.com", "jjjjjjjjjjjj", "jjjjjjjjjjjj", "jjjjjjjjjjjj", 10));
        list.add(new User("kkkk@kkkkk.com", "kkkkkkkkkkkk", "kkkkkkkkkkkk", "kkkkkkkkkkkk", 11));
        list.add(new User("llll@lllll.com", "llllllllllll", "llllllllllll", "llllllllllll", 12));
        list.add(new User("mmmm@mmmmm.com", "mmmmmmmmmmmm", "mmmmmmmmmmmm", "mmmmmmmmmmmm", 13));


    }

    public User getUser(String email){
        for (User user : list){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }
}
