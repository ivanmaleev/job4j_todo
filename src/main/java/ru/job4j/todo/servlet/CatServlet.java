package ru.job4j.todo.servlet;

import ru.job4j.todo.model.Category;
import store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/index.do")
public class CatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Category> categories = PsqlStore.instOf().findAllCategories();
        req.setAttribute("allCategories", categories);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
