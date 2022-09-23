
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class StoriesUnderPosts2 extends javax.swing.JFrame {

    int w, h;
    String pid;
    String ar[];
   

    public StoriesUnderPosts2() {
        initComponents();
          setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //  System.out.println("Pid" + pid);
        //  setTitle("Wall");

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        w = (int) d.getWidth();
        h = (int) d.getHeight();
        setSize(w, h);
        progresspanel.setSize(w, 100);
        //new Thread(new LoadStories()).start();
         //setVisible(false);
         
        //getstory();
    }

    public StoriesUnderPosts2(String pid) {
        initComponents();
        this.pid = pid;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //  System.out.println("Pid" + pid);
        //  setTitle("Wall");

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        w = (int) d.getWidth();
        h = (int) d.getHeight();
        setSize(w, h);
        progresspanel.setSize(w, 100);
        //new Thread(new LoadStories()).start();
        setVisible(false);
        getstory();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        captionlb = new javax.swing.JLabel();
        photolb = new javax.swing.JLabel();
        progresspanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(137, 207, 240));
        getContentPane().setLayout(null);

        captionlb.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        captionlb.setText("LOADING");
        getContentPane().add(captionlb);
        captionlb.setBounds(250, 384, 350, 50);

        photolb.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        photolb.setText("LOADING");
        getContentPane().add(photolb);
        photolb.setBounds(190, 130, 370, 220);

        progresspanel.setBackground(new java.awt.Color(102, 0, 51));

        javax.swing.GroupLayout progresspanelLayout = new javax.swing.GroupLayout(progresspanel);
        progresspanel.setLayout(progresspanelLayout);
        progresspanelLayout.setHorizontalGroup(
            progresspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );
        progresspanelLayout.setVerticalGroup(
            progresspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(progresspanel);
        progresspanel.setBounds(180, 20, 390, 100);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/srcok/baby-blue-color-solid-background-1920x1080.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(-6, 0, 800, 470);

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
            java.util.logging.Logger.getLogger(StoriesUnderPosts2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StoriesUnderPosts2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StoriesUnderPosts2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StoriesUnderPosts2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StoriesUnderPosts2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel captionlb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel photolb;
    private javax.swing.JPanel progresspanel;
    // End of variables declaration//GEN-END:variables
  private void getstory() {
                  
        Runnable obj = new Runnable() {

            public void run() {
                try {

                    HttpResponse<String> res = Unirest.get("http://localhost:8999/getpostedstories")
                            .queryString("pid", pid)
                            .asString();
                    if (res.getStatus() == 200) {

                        if (res.getBody().contentEquals("")) {
                           JOptionPane.showMessageDialog(null , "no story posted");
              
                        } else {
                            setVisible(true);
                            String data = res.getBody();

                            StringTokenizer st = new StringTokenizer(data, "&");
                            int count = st.countTokens();
                            ar = new String[count];

                            System.out.println(count);

                            int i = 0;
                            int width = (int) (w / (count));
                            int height = 20;
                            int x = 20, y = 20;
                            JProgressBar arprogress[] = new JProgressBar[count];
                            for (int k = 0; k < count; k++) {
                                x = (width * k) + 10;

                                arprogress[k] = new JProgressBar();
                                arprogress[k].setBounds(x, y, width, height);
                                progresspanel.add(arprogress[k]);
                                progresspanel.repaint();
                                repaint();

                            }
                            while (st.hasMoreTokens()) {
                                // arprogress[i].setStringPainted(true);

                                //String photo = st.nextToken();
                                // System.out.println("photo" + photo);
                                String followingsdata = st.nextToken();
                                StringTokenizer st1 = new StringTokenizer(followingsdata, ";#");
                                String ans = st1.nextToken();
                                String photo = st1.nextToken();
                                String caption = st1.nextToken();
                                ar[i] = photo;
                                URL url = new URL("http://localhost:8999/GetResource/" + photo);
                                BufferedImage bimg = ImageIO.read(url);
                                Image img = bimg.getScaledInstance(photolb.getWidth(), photolb.getHeight(), Image.SCALE_SMOOTH);
                                photolb.setIcon(new ImageIcon(img));
                                captionlb.setText(caption);

                                for (int j = 1; j <= 100; j++) {
                                    arprogress[i].setValue(j);
                                    Thread.sleep(10);
                                }
                                i++;
                            }
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        };
        Thread t = new Thread(obj);
        t.start();

    }

}
