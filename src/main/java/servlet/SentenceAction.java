package servlet;

import model.Detail;
import model.Sentence;
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
 * 2/27/17 14:42
 */
@WebServlet(urlPatterns = "/sentence")
public class SentenceAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("add")) {
            add(req, resp);
        }
        if (action.equals("queryByPosId")) {
            queryByPosId(req, resp);
        }
    }

    private void queryByPosId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int posId = Integer.parseInt(req.getParameter("posId"));
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM dictionary.sentence WHERE posId = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, posId);
            resultSet = preparedStatement.executeQuery();

            List<Sentence> sentences = new ArrayList<>();
            while (resultSet.next()) {
                Sentence sentence = new Sentence(resultSet.getInt("id"), resultSet.getString("english"), resultSet.getString("chinese"), resultSet.getInt("posId"));
                sentences.add(sentence);
            }
            req.getSession().setAttribute("sentences", sentences);
            req.getSession().setAttribute("posId", posId);

            resp.sendRedirect("sentence.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(resultSet, preparedStatement, connection);
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int posId = Integer.parseInt(req.getParameter("posId"));
        String english = req.getParameter("english");
        String chinese = req.getParameter("chinese");

        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO dictionary.sentence VALUES(NULL, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, english);
            preparedStatement.setString(2, chinese);
            preparedStatement.setInt(3, posId);

            preparedStatement.executeUpdate();

            resp.sendRedirect("sentence?action=queryByPosId&posId=" + posId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
