package com.bjpowernode.javaweb.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@WebServlet({"/transfer"})
public class StudentServlet extends HttpServlet {
    public StudentServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        String convertedPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String rootPath = convertedPath.replace('/', '\\');
        rootPath = rootPath.replaceAll("^\\\\+", "");
        rootPath = rootPath.replaceAll("\\\\+$", "");
        rootPath = rootPath.substring(0, rootPath.indexOf("\\out"));
        String scriptPath = rootPath + "\\py\\Uattending(1).py";
        System.out.println(scriptPath);
        BufferedReader br = null;

        try {
            String[] args1 = new String[]{"python", scriptPath, username, password};
            Process process = Runtime.getRuntime().exec(args1);
            System.out.println("开始执行了");
            br = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));
            String line = null;
            response.setContentType("text/html;charset=GBK");

            while((line = br.readLine()) != null) {
                PrintWriter out = response.getWriter();
                if (line.contains("签到成功")) {
                    out.print("<h1>" + line + "</h1>");
                    process.destroy();
                    break;
                }
            }

            process.waitFor();
            System.out.println("执行结束");
        } catch (Exception var21) {
            Exception e = var21;
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception var20) {
                    Exception e = var20;
                    e.printStackTrace();
                }
            }

        }

    }
}
