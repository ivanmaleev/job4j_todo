package store;

import ru.job4j.todo.model.Item;

import java.util.List;

public interface Store {

    List<Item> findAllItems(boolean all);

    Item saveItem(Item item);

    void setDone(int id);
}
