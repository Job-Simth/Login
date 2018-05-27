package jdbc;

import com.mysql.jdbc.Driver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.sql.*;

public class 登录框体 extends JFrame {
    private JLabel name, pass, card, imageCard, loginCheck;
    private JTextField nameText, passText, cardText;
    private JButton login;
    private int width = 100, height = 30;
    private String str = "";

    public 登录框体() {
        setTitle("登录窗体");
        setFont(new Font("", Font.BOLD, 24));
        setLayout(null);//自定义布局

        //负责产生验证码图片
        Icon icon = new ImageIcon(getCardImage(width, height));
        name = new JLabel("账 号");
        pass = new JLabel("密 码");
        card = new JLabel("验证码");
        loginCheck = new JLabel("");

        imageCard = new JLabel(icon);

        nameText = new JTextField();
        passText = new JTextField();
        cardText = new JTextField();

        login = new JButton("登 录");

        name.setBounds(80, 20, 60, 30);
        pass.setBounds(80, 60, 60, 30);
        card.setBounds(80, 100, 60, 30);
        imageCard.setBounds(240, 100, width, height);

        nameText.setBounds(150, 20, 200, 30);
        passText.setBounds(150, 60, 200, 30);
        cardText.setBounds(150, 100, 80, 30);

        login.setBounds(120, 160, 220, 30);
        loginCheck.setBounds(160, 200, 220, 30);

        add(name);
        add(pass);
        add(card);
        add(imageCard);

        add(nameText);
        add(passText);
        add(cardText);

        add(login);
        add(loginCheck);

        login.addActionListener(e -> {
            String name = nameText.getText();
            String word = passText.getText();
            String cardWord = cardText.getText();

            try {
                Class.forName(Driver.class.getName());

                String url = "jdbc:mysql://localhost/firstdb";
                String user = "root";
                String password = "mysql102021";

                Connection conn = DriverManager.getConnection(url, user, password);

                String sql = "select * from logininfo where username = ? and userpassword = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, word);
                ResultSet rs = ps.executeQuery();

                if (rs.next() && str.toLowerCase().equals(cardWord.toLowerCase())) {
                    loginCheck.setText("登录成功");
                } else if (!str.toLowerCase().equals(cardWord.toLowerCase())) {
                    loginCheck.setText("登录失败,验证码错误");
                    Icon newIcon = new ImageIcon(getCardImage(width, height));
                    imageCard.setIcon(newIcon);
                } else if (!rs.next()) {
                    loginCheck.setText("登录失败,账号或密码错误");
                    Icon newIcon = new ImageIcon(getCardImage(width, height));
                    imageCard.setIcon(newIcon);
                }
            } catch (ClassNotFoundException | SQLException e1) {
                e1.printStackTrace();
            }

        });

        //注册事件
        addMouseListener(new ChangeCard());

        setBounds(0, 0, 450, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public Image getCardImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        int red1 = (int) (Math.random() * 256);
        int green1 = (int) (Math.random() * 256);
        int blue1 = (int) (Math.random() * 256);
        Color c1 = new Color(red1, green1, blue1);

        g.setColor(c1);
        g.fillRect(0, 0, width, height);


        //生成文字
        int red2 = (int) (Math.random() * 256);
        int green2 = (int) (Math.random() * 256);
        int blue2 = (int) (Math.random() * 256);
        Color c2 = new Color(red2, green2, blue2);
        str = "";
        for (int i = 0; i < 4; i++) {
            char ch = (char) ((int) (Math.random() * 26 + 65));
            str += ch;
        }
        g.setColor(c2);
        int x = (int) (Math.random() * (width / 3));
        int y = (int) (Math.random() * (height / 2) + 10);
        g.setFont(new Font("", Font.BOLD, 24));
        g.drawString(str, x, y);

        return image;
    }

    //监听类:鼠标单击时执行
    class ChangeCard implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            Icon icon = new ImageIcon(getCardImage(width, height));
            imageCard.setIcon(icon);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    public static void main(String[] args) {
        new 登录框体();
    }
}