package com.gwtpro.html5.fileapi.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DevNullUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletInputStream inputStream = req.getInputStream();
        try {
            byte[] buffer = new byte[8192];
            int numRead, total = 0;
            while ((numRead = inputStream.read(buffer)) > 0) {
                total += numRead;
            }
            resp.getWriter().print(String.format("got %d bytes", total));
        } finally {
            inputStream.close();
        }
    }
}
