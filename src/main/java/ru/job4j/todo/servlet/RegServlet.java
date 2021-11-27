package ru.job4j.todo.servlet;

import ru.job4j.todo.model.User;
import store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        User userInDB = PsqlStore.instOf().findUserByEmail(email);
        if (userInDB == null) {
            HttpSession sc = req.getSession();
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            PsqlStore.instOf().saveUser(user);
            sc.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            req.setAttribute("error", "Пользователь с таким email уже существует");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/reg.jsp");
    }
}
