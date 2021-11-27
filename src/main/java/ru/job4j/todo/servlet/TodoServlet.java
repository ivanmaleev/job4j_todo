package ru.job4j.todo.servlet;

import ru.job4j.todo.model.Item;
import store.PsqlStore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TodoServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String viewdone = req.getParameter("viewdone");
        boolean all = false;
        if ("true".equals(viewdone)) {
            all = true;
        }
        List<Item> items = PsqlStore.instOf().findAllItems(all);
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
        if (!"".equals(description)) {
            Item item = new Item();
            item.setDescription(description);
            PsqlStore.instOf().saveItem(item);
        }
        doGet(req, resp);
    }
}
