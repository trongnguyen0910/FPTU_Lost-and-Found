/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fu.servlets;

import fu.daos.CommentDAO;
import fu.entities.Member;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "DeleteCommentServlet", urlPatterns = {"/DeleteCommentServlet"})
public class DeleteCommentServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                request.setAttribute("errormessage", "Please login!");
                request.getRequestDispatcher("paging").forward(request, response);
            } else if (session.getAttribute("userdata") != null) { // check login              
                Member member = (Member) session.getAttribute("userdata");
                if (member.getMemberRole() == 1) {
                    if (member.getStatus() == 1) {
                        String cmtId = request.getParameter("cmtId");
                        String aId = request.getParameter("aId");
                        CommentDAO cdao = new CommentDAO();
                        cdao.deleteComment(Integer.parseInt(cmtId));
                        request.getRequestDispatcher("ViewDetailServlet?aId=" + aId).forward(request, response);
                    } else {
                        request.setAttribute("errormessage", "Your account has been banned!");
                        request.getRequestDispatcher("paging").forward(request, response);
                    }
                } else {
                    request.setAttribute("errormessage", "Incorrect ROLE! Must be MEMBER");
                    request.getRequestDispatcher("paging").forward(request, response);
                }
            } else {
                request.setAttribute("errormessage", "Please login!");
                request.getRequestDispatcher("paging").forward(request, response);
            }

        } catch (Exception e) {
            log("ERROR at DeleteCommentServlet: " + e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
