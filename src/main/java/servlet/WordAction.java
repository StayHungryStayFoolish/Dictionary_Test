package servlet;

import model.Word;
import util.DB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingfei.net@gmail.com
 * 2/24/17 14:57
 */
@WebServlet(urlPatterns = "/word")
public class WordAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("add")) {
            add(req, resp);
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String english = req.getParameter("english");
        String phoneticUk = req.getParameter("phoneticUk");
        String phoneticUs = req.getParameter("phoneticUs");

        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO dictionary.word VALUES (NULL, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, english);
            preparedStatement.setString(2, phoneticUk);
            preparedStatement.setString(3, phoneticUs);

            preparedStatement.executeUpdate();

            resp.sendRedirect("word.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(null, preparedStatement, connection);
        }
    }

    protected void queryAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM dictionary.word";

        try {
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            List<Word> words = new ArrayList<>();
            while (resultSet.next()) {
                Word word = new Word();
                words.add(word);
            }

            req.getSession().setAttribute("words", words);
            resp.sendRedirect("word.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(null, preparedStatement, connection);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
