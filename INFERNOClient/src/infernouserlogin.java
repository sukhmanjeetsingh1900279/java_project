
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 */
public class infernouserlogin extends javax.swing.JFrame {

    /**
     * Creates new form LoginExp
     */
    String username;

    public infernouserlogin() {

        initComponents();
        setSize(800, 700);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lb2 = new javax.swing.JLabel();
        usernametf = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        passwordtf = new javax.swing.JTextField();
        loginbtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        backbtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("LOGIN");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(300, 30, 110, 30);

        lb2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb2.setForeground(new java.awt.Color(255, 255, 255));
        lb2.setText("Enter Username");
        getContentPane().add(lb2);
        lb2.setBounds(50, 110, 160, 20);

        usernametf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernametfActionPerformed(evt);
            }
        });
        getContentPane().add(usernametf);
        usernametf.setBounds(460, 100, 220, 40);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Enter Password");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(50, 180, 150, 30);
        getContentPane().add(passwordtf);
        passwordtf.setBounds(460, 180, 220, 40);

        loginbtn.setBackground(new java.awt.Color(153, 0, 0));
        loginbtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        loginbtn.setText("Login");
        loginbtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));
        loginbtn.setBorderPainted(false);
        loginbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginbtnActionPerformed(evt);
            }
        });
        getContentPane().add(loginbtn);
        loginbtn.setBounds(290, 360, 160, 50);

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 255, 255));
        jLabel3.setText("Forget Password???");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel3);
        jLabel3.setBounds(570, 540, 180, 50);

        backbtn.setText("back");
        backbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backbtnActionPerformed(evt);
            }
        });
        getContentPane().add(backbtn);
        backbtn.setBounds(20, 543, 110, 30);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/srcok/1507776.jpg"))); // NOI18N
        jLabel4.setText("jLabel4");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(0, 0, 2000, 1110);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginbtnActionPerformed

        try {

            username = usernametf.getText();

            String password = passwordtf.getText();

            if (username.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter values");
            } else {
                HttpResponse<String> res = Unirest.get("http://localhost:8999/userlogin")
                        .queryString("username", username)
                        .queryString("password", password)
                        .asString();
                if (res.getStatus() == 200) {
                    if (res.getBody().contentEquals("success")) {
                        JOptionPane.showMessageDialog(this, "login successful " + username);

                        userhome uhome = new userhome(username);
                        uhome.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        uhome.setVisible(true);
                        this.dispose();

                    } else {
                        JOptionPane.showMessageDialog(this, "password/username invalid");
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_loginbtnActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        forgetpassword fp = new forgetpassword();
        fp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        fp.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void backbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backbtnActionPerformed
      newmainhome nh =new newmainhome();
      nh.setVisible(true);
      this.dispose();
      
    }//GEN-LAST:event_backbtnActionPerformed

    private void usernametfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernametfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernametfActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(infernouserlogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(infernouserlogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(infernouserlogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(infernouserlogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new infernouserlogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lb2;
    private javax.swing.JButton loginbtn;
    private javax.swing.JTextField passwordtf;
    private javax.swing.JTextField usernametf;
    // End of variables declaration//GEN-END:variables
}
