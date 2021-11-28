package store;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;

public interface Store {

    List<Item> findAllItems(boolean all, int userid);

    Item saveItem(Item item, String[] cids);

    void setDone(int id);

    User saveUser(User user);

    User findUserByEmail(String email);

    List<Category> findAllCategories();
}
