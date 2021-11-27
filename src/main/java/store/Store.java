package store;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;

public interface Store {

    List<Item> findAllItems(boolean all, int userid);

    Item saveItem(Item item);

    void setDone(int id);

    User saveUser(User user);

    User findUserByEmail(String email);
}
