package by.krukouski.restfulldemo.services;

import by.krukouski.restfulldemo.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final AtomicInteger counter = new AtomicInteger();

    private static List<User> users;

    static {
        users = populateUsers();
    }

    private static List<User> populateUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(counter.incrementAndGet(), "Kiril", 23, 3300));
        users.add(new User(counter.incrementAndGet(), "Masha", 21, 4500));
        users.add(new User(counter.incrementAndGet(), "Oleg", 55, 7300));
        return users;
    }


    @Override
    public User findById(long id) {
        for (User user: users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByName(String name) {
        for (User user: users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void saveUser(User user) {
        user.setId(counter.incrementAndGet());
        users.add(user);
    }

    @Override
    public void updateUser(User user) {
        int index = users.indexOf(user);
        users.set(index, user);
    }

    @Override
    public void deleteUserBuId(long id) {
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();){
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
            }
        }
    }

    @Override
    public List<User> findAllUsers() {
        return users;
    }

    @Override
    public void deleteAllUsers() {
        users.clear();
    }

    @Override
    public boolean isUserExist(User user) {
        return findByName(user.getName()) != null;
    }
}
