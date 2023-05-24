package com.elibrary.group4.service;
import com.elibrary.group4.exception.NotFoundException;
import com.elibrary.group4.model.User;
import com.elibrary.group4.model.request.UserRequest;
import com.elibrary.group4.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    IUploadService uploadService;

    @Override
    public List<User> list() throws Exception {
        return userRepository.findAll();
    }

    @Override
    public User Create(UserRequest userRequest) throws Exception {
        try {
//            String filePath = "";
//
//            if (!userRequest.getImage().isEmpty()) {
//                filePath = uploadService.uploadMaterial(userRequest.getImage());
//            }

            User user = new User();
            user.setFirstName(userRequest.getFirstName());
//            user.setImage(filePath);
            user.setLastName(userRequest.getLastName());
            user.setUserName(userRequest.getUserName());
            user.setEmail(userRequest.getEmail());
            user.setPhone(userRequest.getPhone());
            user.setPassword(userRequest.getPassword());
            user.setRole(userRequest.getRole());

            return userRepository.save(user);

        } catch (DataIntegrityViolationException e) {
            throw new EntityExistsException();
        }
    }

    @Override
    public User get(String userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found!");
        }
        return user.get();
    }

    @Override
    public void update(UserRequest userRequest, String userId) throws Exception {
        try {
            User existingUser = get(userId);
            existingUser.setFirstName(userRequest.getFirstName());
            existingUser.setLastName(userRequest.getLastName());
            existingUser.setUserName(userRequest.getUserName());
            existingUser.setEmail(userRequest.getEmail());
            existingUser.setPassword(userRequest.getPassword());
            existingUser.setPhone(userRequest.getPhone());
            userRepository.save(existingUser);
        } catch (NotFoundException e) {
            throw new NotFoundException("Update failed because ID is not found");
        }
    }

    @Override
    public void delete(String userId) throws Exception {
        try {
            User existingUser = get(userId);
            userRepository.delete(existingUser);
        } catch (NotFoundException e) {
            throw new NotFoundException("Delete failed because ID is not found");
        }
    }
}
