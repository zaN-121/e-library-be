package com.elibrary.group4.service;
import com.elibrary.group4.model.User;
import com.elibrary.group4.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@Service
public class UserService implements IService<User>{
    UserRepository userRepository;
    @Override
    public Iterable<User> findAll() {

        try{
            Iterable<User> users = userRepository.findAll();
            Iterator<User> iterator = userRepository.findAll().iterator();
            if(!iterator.hasNext()){
                throw new RuntimeException("Daftar pengguna masih kosong");
            }
            else{
                return users;
            }

        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public User add(User user) {
        try{
            return userRepository.save(user);
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<User> findById(String id) {
        try{
            Optional<User> user =  userRepository.findById(id);
            if(user.isEmpty()){
                throw new RuntimeException("User dengan id " + id +" tidak ditemukan");
            }
            else{
                return user;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(String id) {
        try{
            Optional<User> user =  userRepository.findById(id);
            if(user.isEmpty()){
                throw new RuntimeException("User dengan id " + id +" tidak ditemukan");
            }
            else{
                userRepository.deleteById(id);
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public User update(String id, User updatedUser) {
        try{
            Optional<User> user =  userRepository.findById(id);
            if(user.isEmpty()){
                throw new RuntimeException("User dengan id " + id +" tidak ditemukan");
            }
            else{
                updatedUser.setUserId(id);
                userRepository.save(updatedUser);
                return updatedUser;
            }
        }
        catch (Exception e){throw new RuntimeException(e);}
    }
}
