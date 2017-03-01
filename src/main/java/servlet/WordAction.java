package servlet;

import model.*;
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
        PreparedStatement prepWord = null;
        ResultSet resWord = null;

        try {
            String sqlWord = "SELECT * FROM dictionary.word WHERE english = ?";
            prepWord = connection.prepareStatement(sqlWord);
            prepWord.setString(1, english);
            resWord = prepWord.executeQuery();
            if (!resWord.next()) {
                req.getSession().removeAttribute("word"); // ***
                resp.sendRedirect("index.jsp");
                return;
            }

            Word word = new Word(
                    resWord.getInt("id"),
                    resWord.getString("english"),
                    resWord.getString("phoneticUk"),
                    resWord.getString("phoneticUs")
            );

            int wordId = word.getId();

            String sqlPos = "SELECT * FROM dictionary.pos WHERE wordId = ?";
            PreparedStatement prepPos = connection.prepareStatement(sqlPos);
            prepPos.setInt(1, word.getId());
            ResultSet resPos = prepPos.executeQuery();

            List<Pos> poss = new ArrayList<>();
            while (resPos.next()) {
                Pos pos = new Pos(
                        resPos.getInt("id"),
                        resPos.getString("pos"),
                        wordId
                );

                int posId = pos.getId();

                String sqlConcise = "SELECT * FROM dictionary.concise WHERE posId = ?";
                PreparedStatement prepConcise = connection.prepareStatement(sqlConcise);
                prepConcise.setInt(1, posId);
                ResultSet resConcise = prepConcise.executeQuery();
                if (resConcise.next()) {
                    Concise concise = new Concise(
                            resConcise.getInt("id"),
                            resConcise.getString("chinese"),
                            posId);
                    pos.setConcise(concise);
                }

                String sqlDetail = "SELECT * FROM dictionary.detail WHERE posId = ?";
                PreparedStatement prepDetail = connection.prepareStatement(sqlDetail);
                prepDetail.setInt(1, posId);
                ResultSet resDetail = prepDetail.executeQuery();
                List<Detail> details = new ArrayList<>();
                while (resDetail.next()) {
                    Detail detail = new Detail(
                            resDetail.getInt("id"),
                            resDetail.getString("detail"),
                            posId
                    );
                    details.add(detail);
                }
                pos.setDetails(details);

                String sqlSentence = "SELECT * FROM dictionary.sentence WHERE posId = ?";
                PreparedStatement prepSentence = connection.prepareStatement(sqlSentence);
                prepSentence.setInt(1, posId);
                ResultSet resSentence = prepSentence.executeQuery();
                List<Sentence> sentences = new ArrayList<>();
                while (resSentence.next()) {
                    Sentence sentence = new Sentence(
                            resSentence.getInt("id"),
                            resSentence.getString("english"),
                            resSentence.getString("chinese"),
                            posId
                    );
                    sentences.add(sentence);
                }
                pos.setSentences(sentences);

                poss.add(pos);
            }

            word.setPoss(poss);

            req.getSession().setAttribute("word", word); // ***
            resp.sendRedirect("index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(resWord, prepWord, connection);
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
