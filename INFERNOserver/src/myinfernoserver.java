
import com.vmm.JHTTPServer;
import java.sql.ResultSet;

import java.util.Properties;
  

public class myinfernoserver extends JHTTPServer {

    public myinfernoserver(int port) throws Exception {
        super(port);
    }

    @Override
    public Response serve(String uri, String method, Properties header, Properties parms, Properties files) {

        Response res = new Response(HTTP_OK, "text/plain", "WELCOME");

        if (uri.contains("GetResource")) {

            uri = uri.substring(1);
            uri = uri.substring(uri.indexOf("/") + 1);
            res = sendCompleteFile(uri);

        } else if (uri.contains("usersignup")) {

            String username = parms.getProperty("username");
            String password = parms.getProperty("password");
            String email = parms.getProperty("email");
            String mobileno = parms.getProperty("mobileno");
            String gender = parms.getProperty("gender");

            String ans = "";

            try {

                ResultSet rs = DBLoader.executeStatement("select * from users where username='" + username + "'");
                if (rs.next()) {

                    ans = "fail";

                } else {

                    String filename = saveFileOnServerWithRandomName(files, parms, "photo", "src/uploads");
                    String filepath = "src/uploads/" + filename;

                    rs.moveToInsertRow();
                    rs.updateString("username", username);
                    rs.updateString("password", password);
                    rs.updateString("email", email);
                    rs.updateString("mobileno", mobileno);
                    rs.updateString("gender", gender);
                    rs.updateString("photo", filepath);
                    rs.insertRow();
                    ans = "success";

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            res = new Response(HTTP_OK, "text/plain", ans);
        } else if (uri.contains("userlogin")) {
            String username = parms.getProperty("username");
            String password = parms.getProperty("password");

            //Database code
            String ans = "";
            try {

                ResultSet rs = DBLoader.executeStatement("select * from users where username='" + username + "' and password='" + password + "'");
                if (rs.next()) {
                    ans = "success";
                } else {
                    ans = "fail";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            res = new Response(HTTP_OK, "text/plain", ans);

        } else if (uri.contains("userchangepassword")) {
            String username = parms.getProperty("username");
            String oldpassword = parms.getProperty("oldpassword");
            String newpassword = parms.getProperty("newpassword");
            String ans = "";
            try {
                ResultSet rs = DBLoader.executeStatement("select * from users where username='" + username + "' and password='" + oldpassword + "'");

                if (rs.next()) {

                    rs.updateString("password", newpassword);
                    rs.updateRow();

                    ans = "success";
                } else {
                    ans = "fails";
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            res = new Response(HTTP_OK, "text/plain", ans);

        } else if (uri.contains("search")) {
            String keyword = parms.getProperty("keyword");
            String homeusername = parms.getProperty("homeusername");

            //Database code
            String data = "";
            String ans = "";
            try {

                ResultSet rs = DBLoader.executeStatement("select * from users where username like '%" + keyword + "%'");

                while (rs.next()) {

                    String username = rs.getString("username");
                    String photo = rs.getString("photo");
                    ResultSet rs1 = DBLoader.executeStatement("select * from followtable where followedby='" + homeusername + "' and followedto='" + username + "'");

                    if (rs1.next()) {
                        ans = "yes";

                    } else {
                        ans = "no";
                    }
                    data += username + ";#" + photo + ";#" + ans + "&";

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            res = new Response(HTTP_OK, "text/plain", data);

        } else if (uri.contains("followrequest")) {
            String usernames = parms.getProperty("username");
            String homeusername = parms.getProperty("homeusername");

            String ans = "";
            try {

                ResultSet rs = DBLoader.executeStatement("select * from followtable where followedby='" + homeusername + "' and followedto='" + usernames + "'");
                if (rs.next()) {
                    rs.deleteRow();

                    ans = "fail";
                } else {
                    rs.moveToInsertRow();
                    rs.updateString("followedto", usernames);
                    rs.updateString("followedby", homeusername);
                    rs.insertRow();
                    ans = "success";
                }

            } catch (Exception e) {

                e.printStackTrace();

            }
            res = new Response(HTTP_OK, "text/plain", ans);

        } else if (uri.contains("getfollowings")) {
            String username = parms.getProperty("fusername");
            String data = "";
            try {
                ResultSet rs = DBLoader.executeStatement("select * from followtable where followedby='" + username + "'");
                while (rs.next()) {
                    String followedto = rs.getString("followedto");
                    String fid = rs.getString("fid");
                    ResultSet rs1 = DBLoader.executeStatement("select * from users where username ='" + followedto + "'");
                    rs1.next();
                    String photo = rs1.getString("photo");
                    data += followedto + ";#" + fid + ";#" + photo + "&";
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            res = new Response(HTTP_OK, "text/plain", data);

        } else if (uri.contains("unfollow")) {
            String fid = parms.getProperty("fid");
            try {
                ResultSet rs = DBLoader.executeStatement("select * from followtable where fid='" + fid + "'");
                rs.next();
                rs.deleteRow();
                res = new Response(HTTP_OK, "text/plain", "success");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (uri.contains("getfollowers")) {
            String username = parms.getProperty("fusername");
            String data = "";
            try {
                ResultSet rs = DBLoader.executeStatement("select * from followtable where followedto='" + username + "'");
                while (rs.next()) {
                    String followedby = rs.getString("followedby");
                    String fid = rs.getString("fid");
                    ResultSet rs1 = DBLoader.executeStatement("select * from users where username ='" + followedby + "'");
                    rs1.next();
                    String photo = rs1.getString("photo");
                    data += followedby + ";#" + fid + ";#" + photo + "&";
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            res = new Response(HTTP_OK, "text/plain", data);

        } else if (uri.contains("addpost")) {

            String title = parms.getProperty("title");
            String username = parms.getProperty("username");
            String date = parms.getProperty("date");
            String time = parms.getProperty("time");
            String ans = "";
            String data = "";

            try {
                ResultSet rs = DBLoader.executeStatement("select * from posts");

                String filename = saveFileOnServerWithRandomName(files, parms, "photo", "src/uploads");
                String filepath = "src/uploads/" + filename;

                rs.moveToInsertRow();
                rs.updateString("title", title);

                rs.updateString("username", username);

                rs.updateString("date", date);
                rs.updateString("time", time);

                rs.updateString("photo", filepath);
                rs.insertRow();
                ans = "success";
                ResultSet rs1 = DBLoader.executeStatement("select MAX(pid) as pid from posts");
                while (rs1.next()) {
                    String pid = rs1.getString("pid");

//doubt for somethin may happen wrong--------------------------------------------
                    data += pid + ";#" + ans;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            res = new Response(HTTP_OK, "text/plain", data);

        } else if (uri.contains("story")) {

            String caption = parms.getProperty("caption");
            String pids = parms.getProperty("pid");

            //System.out.println("goblin");
            try {
                ResultSet rs = DBLoader.executeStatement("select * from storytable");

                String filename = saveFileOnServerWithRandomName(files, parms, "photo", "src/uploads");
                String filepath = "src/uploads/" + filename;
                rs.moveToInsertRow();
                rs.updateString("caption", caption);
                rs.updateString("pids", pids);

                rs.updateString("photo", filepath);

                rs.insertRow();
                res = new Response(HTTP_OK, "text/plain", "success");

            } catch (Exception e) {
                e.printStackTrace();

            }

        } else if (uri.contains("showadded")) {

            String opid = parms.getProperty("spid");
            //System.out.println("goblin");

            String data = "";

            try {
                ResultSet rs = DBLoader.executeStatement("select * from storytable where pids ='" + opid + "'");

                while (rs.next()) {

                    String psid = rs.getString("psid");

                    String caption = rs.getString("caption");

                    String photo = rs.getString("photo");

                    data += psid + ";#" + caption + ";#" + photo + "&";

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            res = new Response(HTTP_OK, "text/plain", data);

        } else if (uri.contains("dell")) {
            String fid = parms.getProperty("psid");

            try {
                ResultSet rs = DBLoader.executeStatement("select * from storytable where psid ='" + fid + "'");
                rs.next();
                rs.deleteRow();
                res = new Response(HTTP_OK, "text/plain", "success");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (uri.contains("getwallposts")) {
            String username = parms.getProperty("homeusername");
            String ans = "";
            String data = "";
            String ans1 = "";

            //System.out.println("goblin");
            try {
                ResultSet rs = DBLoader.executeStatement("select * from followtable where followedby ='" + username + "' ");
                while (rs.next()) {
                    String followedto = rs.getString("followedto");

                    ResultSet rs1 = DBLoader.executeStatement("select * from posts where username ='" + followedto + "'ORDER BY date desc,time desc");
                    while (rs1.next()) {
                        String pid = rs1.getString("pid");
                        String title = rs1.getString("title");
                        String photo = rs1.getString("photo");
                        String ftusername = rs1.getString("username");
                        String date = rs1.getString("date");
                        String time = rs1.getString("time");
                        ResultSet rs2 = DBLoader.executeStatement("select * from users where username ='" + followedto + "'");
                        while (rs2.next()) {
                            String ftphoto = rs2.getString("photo");
                            ans = ftphoto;

                            // data += pid + ";#" + title + ";#" + photo + ";#" + ftusername + ";#" + date + ";#" + time + ";#" + ftphoto + "&";
                        }
                        ResultSet rs3 = DBLoader.executeStatement("select * from liketable where username ='" + username + "' and pid='" + pid + "'");
                        if (rs3.next()) {

                            ans1 = "yes";
                        } else {

                            ans1 = "no";

                        }
                        ResultSet rs4 = DBLoader.executeStatement("select COUNT(*) as count from liketable where pid='" + pid + "'");
                        rs4.next();
                        String cnt = rs4.getString("count");

                        data += pid + ";#" + title + ";#" + photo + ";#" + ftusername + ";#" + date + ";#" + time + ";#" + ans1 + ";#" + ans + ";#" + cnt + "&";

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            res = new Response(HTTP_OK, "text/plain", data);
        } else if (uri.contains("getlike")) {
            String username = parms.getProperty("homeusername");
            String pid = parms.getProperty("pid");
            String ans = "";
            //String data = "";
            System.out.println("goblin");

            try {
                ResultSet rs = DBLoader.executeStatement("select * from liketable where username ='" + username + "' and pid='" + pid + "'");
                if (rs.next()) {
                    rs.deleteRow();
                    ans = "fail";
                } else {
                    rs.moveToInsertRow();
                    rs.updateString("username", username);
                    rs.updateString("pid", pid);
                    rs.insertRow();
                    ans = "success";

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            res = new Response(HTTP_OK, "text/plain", ans);
        } else if (uri.contains("sndcomments")) {
            String username = parms.getProperty("homeusername");
            String pid = parms.getProperty("pid");
            String comment = parms.getProperty("comment");

            try {
                ResultSet rs = DBLoader.executeStatement("select * from commenttable");

                rs.moveToInsertRow();
                rs.updateString("comments", comment);
                rs.updateString("pid", pid);

                rs.updateString("username", username);

                rs.insertRow();
                res = new Response(HTTP_OK, "text/plain", "success");

            } catch (Exception e) {
                e.printStackTrace();

            }
        } else if (uri.contains("getcomments")) {
            String pid = parms.getProperty("pid");
            String data = "";
            System.out.println("goblin");
            try {

                ResultSet rs = DBLoader.executeStatement("select * from commenttable where pid ='" + pid + "' ORDER BY datetime desc");
                while (rs.next()) {
                    String comments = rs.getString("comments");
                    String by = rs.getString("username");
                    data += comments + ";#" + by + "&";
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            res = new Response(HTTP_OK, "text/plain", data);
        } else if (uri.contains("forgetpassword1")) {
            String username = parms.getProperty("username");
            int min = 1000;
            int max = 9999;
            String ans = "";

            System.out.println("goblin");
            try {
                ResultSet rs = DBLoader.executeStatement("select * from users where username='" + username + "'");
                if (rs.next()) {
                    ans = "success";

                    int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
                    String otp = Integer.toString(random_int);
                    //System.out.println(otp);
                    res = new Response(HTTP_OK, "text/plain", ans + ";#" + otp);

                } else {
                    ans = "fail";
                    String otp = "0";
                    res = new Response(HTTP_OK, "text/plain", ans + ";#" + otp);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (uri.contains("forgetpassword2")) {
            String username = parms.getProperty("username");

            String newpassword = parms.getProperty("newpassword");
            String ans = "";
            try {
                ResultSet rs = DBLoader.executeStatement("select * from users where username='" + username + "'");

                if (rs.next()) {

                    rs.updateString("password", newpassword);
                    rs.updateRow();

                    ans = "success";
                } else {
                    ans = "fails";
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            res = new Response(HTTP_OK, "text/plain", ans);
        } else if (uri.contains("getpostedstories")) {
            String pid = parms.getProperty("pid");
            System.out.println("goblin");
            String data = "";
            try {
                ResultSet rs = DBLoader.executeStatement("select * from storytable where pids='" + pid + "'");
               
                    while (rs.next()) {

                        String photo = rs.getString("photo");

                        String caption = rs.getString("caption");

                        data += "success" + ";#" + photo + ";#" + caption + "&";

                    }
                

            } catch (Exception e) {
                e.printStackTrace();
            }
            res = new Response(HTTP_OK, "text/plain", data);
        }
        return res;
    }

}
