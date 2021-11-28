package ru.job4j.todo.servlet;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import store.PsqlStore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(urlPatterns = "/todo")
public class TodoServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String viewdone = req.getParameter("viewdone");
        String userid = req.getParameter("userid");
        boolean all = "true".equals(viewdone);
        List<Item> items = PsqlStore.instOf().findAllItems(all, Integer.parseInt(userid));
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(items);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String description = req.getParameter("description");
        String userid = req.getParameter("userid");
        Item item = new Item();
        item.setDescription(description);
        item.setUser(new User(Integer.parseInt(userid)));
        PsqlStore.instOf().saveItem(item);
        doGet(req, resp);
    }
}
