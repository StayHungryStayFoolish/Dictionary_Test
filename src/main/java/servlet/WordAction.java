package servlet;

import model.Concise;
import model.Pos;
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
        if (action.equals("queryAll")) {
            queryAll(req, resp);
        }
        if (action.equals("search")) {
            search(req, resp);
        }
        if (action.equals("update")) {
            update(req, resp);
        }
        if (action.equals("remove")) {
            remove(req, resp);
        }
        if (action.equals("queryByEnglish")) {
            queryByEnglish(req, resp);
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

            resp.sendRedirect("/word?action=queryAll");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(null, preparedStatement, connection);
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String english = req.getParameter("english");
        String phoneticUk = req.getParameter("phoneticUk");
        String phoneticUs = req.getParameter("phoneticUs");
        int id = Integer.parseInt(req.getParameter("id"));

        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE dictionary.word SET english = ?, phoneticUk = ?, phoneticUs = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, english);
            preparedStatement.setString(2, phoneticUk);
            preparedStatement.setString(3, phoneticUs);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            resp.sendRedirect("/word?action=queryAll");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(null, preparedStatement, connection);
        }
    }

    private void queryAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM dictionary.word";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            List<Word> words = new ArrayList<>();
            while (resultSet.next()) {
                Word word = new Word(resultSet.getInt("id"), resultSet.getString("english"), resultSet.getString("phoneticUk"), resultSet.getString("phoneticUs"));
                words.add(word);
            }
            req.getSession().setAttribute("words", words);
            resp.sendRedirect("admin.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(resultSet, preparedStatement, connection);
        }
    }

    private void queryByEnglish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String english = req.getParameter("english").trim();
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        try {
            String sqlWord = "SELECT * FROM dictionary.word WHERE english = ?";
            preparedStatement = connection.prepareStatement(sqlWord);
            preparedStatement.setString(1, english);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                req.getSession().removeAttribute("word"); // ***
                req.getSession().removeAttribute("poss"); // ***
                resp.sendRedirect("index.jsp");
                return;
            }

            Word word = new Word(
                    resultSet.getInt("id"),
                    resultSet.getString("english"),
                    resultSet.getString("phoneticUk"),
                    resultSet.getString("phoneticUs")
            );

            String sqlPos = "SELECT p.id, p.pos, c.chinese FROM dictionary.pos p INNER JOIN dictionary.concise c ON p.id = c.posId WHERE p.wordId = ?";
            preparedStatement = connection.prepareStatement(sqlPos);
            preparedStatement.setInt(1, word.getId());
            resultSet = preparedStatement.executeQuery();

            List<Pos> poss = new ArrayList<>();
            while (resultSet.next()) {
                Concise concise = new Concise(null, resultSet.getString("chinese"), 0);
                Pos pos = new Pos(
                        resultSet.getInt("id"),
                        resultSet.getString("pos"),
                        0,
                        concise
                );
                poss.add(pos);
            }

            req.getSession().setAttribute("word", word); // ***
            req.getSession().setAttribute("poss", poss);
            resp.sendRedirect("index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(resultSet, preparedStatement, connection);
        }
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM dictionary.word WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Word word = new Word(resultSet.getInt("id"), resultSet.getString("english"), resultSet.getString("phoneticUk"), resultSet.getString("phoneticUs"));
            req.setAttribute("word", word);
            req.getRequestDispatcher("admin.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(resultSet, preparedStatement, connection);
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM dictionary.word WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            resp.sendRedirect("/word?action=queryAll");
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
