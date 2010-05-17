package com.gwtpro.html5.fileapi.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DevNullUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletInputStream inputStream = req.getInputStream();
        try {
            byte[] buffer = new byte[8192];
            int numRead;
            while ((numRead = inputStream.read(buffer)) > 0) {
                System.out.print(new String(buffer,0,numRead));
            }
        } finally {
            inputStream.close();
        }
    }
}
