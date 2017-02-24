package servlet;

import model.Pos;
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
 * 2/24/17 14:05
 */
@WebServlet(urlPatterns = "/pos")
public class PosAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("add")) {
            add(req, resp);
        }
        if (action.equals("queryByWordId")) {
            queryByWordId(req, resp);
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pos = req.getParameter("pos").trim();
        int wordId = Integer.parseInt(req.getParameter("wordId"));

        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO dictionary.pos VALUES (NULL, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pos);
            preparedStatement.setInt(2, wordId);
            preparedStatement.executeUpdate();
            resp.sendRedirect("/pos?action=queryByWordId&wordId=" + wordId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(null, preparedStatement, connection);
        }
    }

    private void queryByWordId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int wordId = Integer.parseInt(req.getParameter("wordId"));

        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM dictionary.pos WHERE wordId = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, wordId);
            resultSet = preparedStatement.executeQuery();
            List<Pos> poss = new ArrayList<>();
            while (resultSet.next()) {
                Pos pos = new Pos(resultSet.getInt("id"), resultSet.getString("pos"), resultSet.getInt("wordId"));
                poss.add(pos);
            }
            req.getSession().setAttribute("poss", poss);
            req.getSession().setAttribute("wordId", wordId);
            resp.sendRedirect("pos.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(resultSet, preparedStatement, connection);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
