/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;

/**
 *
 * @author HP
 */
public class myServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           
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
       // processRequest(request, response);
       
       //for webhook verification
       String VERIFY_TOKEN = "Shantha";
       
       String stringToJsp = new String();
       //button click for posting message
       if(request.getParameter("btnPost")!=null){
           String message = request.getParameter("StrPost");
           //call function to post on faceboook
           stringToJsp = Activities.makePost(message);
           if(stringToJsp != null){
               //passing values to jsp page
           request.getSession().setAttribute("result", stringToJsp);
           request.getRequestDispatcher("/StringResponses.jsp").forward(request, response);
          }
       }
       // button click action for get messages
       if(request.getParameter("btnGetMsg")!= null){
           //function call
           JSONArray messages = Activities.getConversations();
            stringToJsp = messages.toString();
           
         if(stringToJsp != null){
           // passing vlue to jsp
           request.setAttribute("result", stringToJsp);
           request.getRequestDispatcher("/RedirectJsp.jsp").forward(request, response);
         }
       }

       //
       if(request.getParameter("btnGetCmnt")!=null){
           //function call
           JSONArray cmnts = Activities.getAllPostComments();
           stringToJsp = cmnts.toString();
           if(stringToJsp != null){
               //passing values to jsp
           request.setAttribute("result",stringToJsp);
           request.getRequestDispatcher("/RedirectJsp.jsp").forward(request, response);
           }
           
       }

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
