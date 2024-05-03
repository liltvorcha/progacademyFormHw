import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
Создать проект «Анкета». Сделать возможность ввода пользователем его имени, фамилии,
возраста и ответов на 2-3 вопроса. Данные должны отправляться на сервер, который в ответ
должен вернуть статистику по ответам в виде HTML документа.
 */

@SuppressWarnings("serial")
public class FormServlet extends HttpServlet{

    static final String TEMPLATE = "<html>" +
            "<head><title>Prog.kiev.ua</title></head><body><h3>" +
            "Users count: %1s<br>" +
            "Users with same name count: %2s<br>" +
            "Users with same surname count: %3s<br>" +
            "Older users count: %4s<br>" +
            "Married users count: %5s<br>" +
            "Singe users count: %6s<br>" +
            "Man users count: %7s<br>" +
            "Woman users count: %8s<br>" +
            "<a href=\"index.html\">repeat</a>"+
            "</h3></body></html>";

    static final List<User> listUsers = new ArrayList<User>();

    private int marriedUsers = 0;
    private int singeUsers = 0;
    private int manUsers = 0;
    private int womanUsers = 0;

    //обработка POST запроса
    public void doPost(HttpServletRequest req, HttpServletResponse resp)throws IOException{

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String ageS = req.getParameter("age");
        int age = 0;
        try {
             age = Integer.parseInt(ageS);
        }
        catch (Exception e){

        }
        String sexS = req.getParameter("sex");
        boolean sex = Boolean.parseBoolean(sexS);
        String statusS = req.getParameter("status");
        boolean status = Boolean.parseBoolean(statusS);

        int usersWithSameName = 0;
        int usersWithSameSurname = 0;
        int olderUsers = 0;

        for (User u: listUsers){
            if(name.equals(u.getName())){
                usersWithSameName++;
            }
            if(surname.equals(u.getSurname())){
                usersWithSameSurname++;
            }
            if(age<u.age){
                olderUsers++;
            }
        }

        if(sex){
            manUsers++;
        }
        else
            womanUsers++;

        if(status){
            marriedUsers++;
        }
        else {
            singeUsers++;
        }

        User user = new User(name,surname,age,sex,status);
        listUsers.add(user);

        resp.getWriter().println(String.format(TEMPLATE, listUsers.size(), usersWithSameName, usersWithSameSurname,
                                                olderUsers, marriedUsers, singeUsers,manUsers,womanUsers));
    }

    private class User{
        private String name;
        private String surname;
        private int age;
        private boolean sex;
        private boolean status;

        public User(String name, String surname, int age, boolean sex, boolean status) {
            this.name = name;
            this.surname = surname;
            this.age = age;
            this.sex = sex;
            this.status = status;
        }

        String getName() {
            return name;
        }

        String getSurname() {
            return surname;
        }

        int getAge() {
            return age;
        }

        boolean isSex() {
            return sex;
        }

        boolean isStatus() {
            return status;
        }
    }
}
