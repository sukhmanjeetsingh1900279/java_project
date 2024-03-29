
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class newsearch extends javax.swing.JFrame {

    String homeusernames;

    public newsearch() {
        initComponents();
        setSize(1000, 800);
    }

    public newsearch(String homeusername) {
        initComponents();
        setSize(1000, 800);
        homeusernames = homeusername;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchnametf = new javax.swing.JTextField();
        searchbtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mpanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(searchnametf);
        searchnametf.setBounds(50, 20, 250, 30);

        searchbtn.setBackground(new java.awt.Color(123, 156, 179));
        searchbtn.setText("search");
        searchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbtnActionPerformed(evt);
            }
        });
        getContentPane().add(searchbtn);
        searchbtn.setBounds(350, 10, 220, 60);

        mpanel.setBackground(new java.awt.Color(204, 204, 204));
        mpanel.setLayout(null);
        jScrollPane1.setViewportView(mpanel);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(30, 90, 770, 400);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/srcok/baby-blue-color-solid-background-1920x1080.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(4, -6, 1100, 880);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed

        String keyword = searchnametf.getText();
        if (keyword.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter values");
        } else {
            try {
                mpanel.removeAll();

                HttpResponse<String> res = Unirest.get("http://localhost:8999/search")
                        .queryString("keyword", keyword)
                        .queryString("homeusername", homeusernames)
                        .asString();

                if (res.getStatus() == 200) {
                    String udata = res.getBody();
                    StringTokenizer st = new StringTokenizer(udata, "&");
                    int y = 10;
                    int mainheight = 100;
                    while (st.hasMoreTokens()) {

                        String wholeuserdata = st.nextToken();
                        StringTokenizer st1 = new StringTokenizer(wholeuserdata, ";#");
                        String usernames = st1.nextToken();
                        String photo = st1.nextToken();
                        String iffollow = st1.nextToken();
                        if (!usernames.equals(homeusernames)) {
                            System.out.println(usernames + "  " + photo);
                            userpanel up = new userpanel();
                            up.setBounds(10, y, 600, 100);
                            mpanel.add(up);

                            URL url = new URL("http://localhost:8999/GetResource/" + photo);
                            BufferedImage bimg = ImageIO.read(url);
                            Image img = bimg.getScaledInstance(up.searchphotolb.getWidth(), up.searchphotolb.getHeight(), Image.SCALE_SMOOTH);
                            up.searchphotolb.setIcon(new ImageIcon(img));

                            up.searchnamelb.setText(usernames);

                            if (iffollow.equals("yes")) {
                                up.followbtn.setText("following");

                            } else {
                                up.followbtn.setText("follow");

                            }

                            up.followbtn.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent ae) {
                                    new Thread() {
                                        public void run() {
                                            try {
                                                HttpResponse<String> res = Unirest.get("http://localhost:8999/followrequest")
                                                        .queryString("username", usernames)
                                                        .queryString("homeusername", homeusernames)
                                                        .asString();
                                                if (res.getStatus() == 200) {
                                                    if (res.getBody().contentEquals("success")) {

                                                        up.followbtn.setText("following");
                                                        //userhome uh =new userhome(homeusernames);
                                                        //String homeusername = uh.homeusername;
                                                        //uh.getwallposts();

                                                    } else {
                                                        up.followbtn.setText("follow");
                                                       // userhome uh =new userhome(homeusernames);
                                                        //String homeusername = uh.homeusername;
                                                       // uh.getwallposts();
                                                    }
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }.start();

                                }
                            });
                            y += 120;
                            mainheight += 120;
                        }
                    }

                    mpanel.repaint();
                    mpanel.setPreferredSize(new Dimension(jScrollPane1.getWidth(), mainheight));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_searchbtnActionPerformed

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
            java.util.logging.Logger.getLogger(newsearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(newsearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(newsearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(newsearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new newsearch().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mpanel;
    private javax.swing.JButton searchbtn;
    private javax.swing.JTextField searchnametf;
    // End of variables declaration//GEN-END:variables
}
