
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 91987
 */
public class followings extends javax.swing.JFrame {

    String fusername;

    public followings() {
        initComponents();
        setSize(700, 600);
       // getfollowings();

    }

    public followings(String homeusername) {
        initComponents();
        setSize(700, 600);
        fusername = homeusername;
        getfollowings();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fscrolpane = new javax.swing.JScrollPane();
        fpanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        fpanel.setBackground(new java.awt.Color(101, 67, 33));
        fpanel.setLayout(null);
        fscrolpane.setViewportView(fpanel);

        getContentPane().add(fscrolpane);
        fscrolpane.setBounds(0, 0, 660, 540);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(followings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(followings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(followings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(followings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new followings().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel fpanel;
    private javax.swing.JScrollPane fscrolpane;
    // End of variables declaration//GEN-END:variables

    public void getfollowings() {
        try {
            fpanel.removeAll();
            HttpResponse<String> res = Unirest.get("http://localhost:8999/getfollowings")
                    .queryString("fusername", fusername)
                    .asString();
            if (res.getStatus() == 200) {
                String data = res.getBody();
                StringTokenizer st = new StringTokenizer(data, "&");
                int y = 10;
                int mainheight = 100;
                while (st.hasMoreTokens()) {

                    String followingsdata = st.nextToken();
                    StringTokenizer st1 = new StringTokenizer(followingsdata, ";#");
                    String followedto = st1.nextToken();
                    String fid = st1.nextToken();
                    String photo = st1.nextToken();
                    followingspanel fp = new followingspanel();
                    fp.setBounds(10, y, 600, 100);
                    fpanel.add(fp);
                    URL url = new URL("http://localhost:8999/GetResource/" + photo);
                    BufferedImage bimg = ImageIO.read(url);
                    Image img = bimg.getScaledInstance(fp.followingimagelb.getWidth(), fp.followingimagelb.getHeight(), Image.SCALE_SMOOTH);
                    fp.followingimagelb.setIcon(new ImageIcon(img));
                    fp.followingnamelb.setText(followedto);
                    fp.unfollowbtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            new Thread() {
                                public void run() {
                                    try {
                                        HttpResponse<String> res = Unirest.get("http://localhost:8999/unfollow")
                                                .queryString("fid", fid)
                                                .asString();
                                        if (res.getStatus() == 200) {
                                            if (res.getBody().contentEquals("success")) {

                                                getfollowings();

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
                fpanel.repaint();
                fpanel.setPreferredSize(new Dimension(fscrolpane.getWidth(), mainheight));

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
