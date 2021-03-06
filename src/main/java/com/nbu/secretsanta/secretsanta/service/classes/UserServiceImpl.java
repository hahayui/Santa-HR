package com.nbu.secretsanta.secretsanta.service.classes;

import com.nbu.secretsanta.secretsanta.model.Hobby;
import com.nbu.secretsanta.secretsanta.model.User;
import com.nbu.secretsanta.secretsanta.repository.UserRepository;
import com.nbu.secretsanta.secretsanta.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean validate(User user) {
        return false;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(@Email String i) {
        return userRepository.findByEmail(i);
    }

    @Override
    public User getUserById(long userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public List<User> getAllUsers() { return userRepository.findAll(); }

    @Override
    // can be done by increasing the value of
    // specific indexes in
    // an ArrayList instead of initializing
    // all these variables
    public LinkedHashMap<String, Integer> countAllUsers() {
        Integer totalCount = (int) userRepository.count();
        Integer regCount = 0;
        Integer notRegCount = 0;
        Integer femaleCount = 0;
        Integer maleCount = 0;
        Integer sheMaleCount = 0;

        List<User> users = userRepository.findAll();
        for( User user: users) {
            if (user.getIsRegistered()) {
                regCount += 1;
            } else {
                notRegCount += 1;
            }
            if (user.getGender() == 1) {
                femaleCount += 1;
            } else if (user.getGender() == 0) {
                maleCount += 1;
            } else {
                sheMaleCount+=1;
            }
        }
            LinkedHashMap<String, Integer> results = new LinkedHashMap<>();
            results.put(" Total Users ", totalCount);
            results.put(" Registrated Users ", regCount);
            results.put(" Not Registrated Users ", notRegCount);
            results.put(" Women <3 ", femaleCount);
            results.put(" Man ;( ", maleCount);
            results.put(" Other? ", sheMaleCount);
            return results;

    }

    @Override
    public List<User> getAllByNameContaining(String substring) {
        return userRepository.getAllByNameContaining(substring);
    }

    @Override
    public void unregUser(User user) {
        user.setIsRegistered(false);
        userRepository.save(user);
    }

    @Override
    public void changeHobbies(User user,Set<Hobby> hobbyList) {
        user.setHobbies(hobbyList);
    }

    @Override
    public Set<Hobby> getHobbies(User user) {
        return user.getHobbies();
    }

}
