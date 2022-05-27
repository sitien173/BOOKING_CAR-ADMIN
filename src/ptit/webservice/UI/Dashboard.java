/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptit.webservice.UI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.TrayIcon;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ptit.webservice.UI.user.add;
import ptit.webservice.UI.user.edit;
import ptit.webservice.admin.Program;
import ptit.webservice.model.AppUser;
import ptit.webservice.model.PagingModel;
import ptit.webservice.model.ResponseModel;

/**
 *
 * @author ngosi
 */
public class Dashboard extends javax.swing.JFrame {

    private static int pageIndexUser = 1;
    private static final int limitUser = 20;
    private static int totalPageUser = 0;
    // cache appUser
    private static final Map<Integer, List<AppUser>> cacheAppUser = new HashMap<>();
    
    
    private static int pageIndexCar = 0;

    private static int pageIndexBrand = 0;

    private static int pageIndexDetail = 0;

    private void getUsers(boolean isRefresh) {
        try {
            List<AppUser> appUsers = cacheAppUser.get(pageIndexUser);
            if(appUsers == null || isRefresh) {
              String url = "/AppUsers/list";
               Map<String, String> params = new HashMap<>();
               params.put("pageIndex", String.valueOf(pageIndexUser));
               params.put("limit", String.valueOf(limitUser));

               Map<Program.HttpHeader, String> headers = new HashMap<>();
               headers.put(Program.HttpHeader.Authorization, Program.PrefixToken + Program.Token);
               headers.put(Program.HttpHeader.ContentType, "application/json; charset=utf-8");

               String jsonData = Program.SendHttpGet(url, params, headers);

               java.lang.reflect.Type type = new TypeToken<ResponseModel<PagingModel<AppUser>>>() {
               }.getType();
               ResponseModel<PagingModel<AppUser>> response = new Gson().fromJson(jsonData, type);
                if (!response.isSuccess()) {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
                }
                appUsers = response.getData().getItems();
                totalPageUser = response.getData().getTotalPage();
                cacheAppUser.put(pageIndexUser, appUsers);
            }
            
            DefaultTableModel model = (DefaultTableModel) tableUser.getModel();
//                 DefaultTableModel model = new DefaultTableModel(){
//                    @Override
//                    public Class<?> getColumnClass(int column) {
//                        switch (column) {
//                            case 6: return ImageIcon.class;
//                            default: return String.class;
//                        }
//                    }
//                };
                model.setColumnCount(0);
                model.setRowCount(0);
                
                model.addColumn("Id");
                model.addColumn("Username");
                model.addColumn("Email");
                model.addColumn("PhoneNumber");
                model.addColumn("Name");
                model.addColumn("Role");
               // model.addColumn("Avatar");
                int size = appUsers.size();
                for (int i = 0; i < size; i++) {
                    Vector vt = new Vector<>();
                    vt.add(String.valueOf(appUsers.get(i).getId()));
                    vt.add(appUsers.get(i).getUsername());
                    vt.add(appUsers.get(i).getEmail());
                    vt.add(appUsers.get(i).getPhoneNumber());
                    vt.add(appUsers.get(i).getName());
                    vt.add(appUsers.get(i).getRole());
                    //vt.add(appUsers.get(i).getAvatar());
                    model.addRow(vt);
                }
                lbPageIndex.setText(String.valueOf(pageIndexUser));
            
        } catch (ProtocolException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates new form Dashboard
     */
    public Dashboard() {
        initComponents();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setTitle("Hi " + Program.identities.getName());
        this.setBackground(new Color(248, 250, 252));  //Whatever color
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        tabDetail = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        toolBarUser = new javax.swing.JToolBar();
        btnUserAdd = new javax.swing.JButton();
        btnUserEdit = new javax.swing.JButton();
        btnUserRemove = new javax.swing.JButton();
        btnUserRefresh = new javax.swing.JButton();
        btnUserPrev = new javax.swing.JButton();
        lbPageIndex = new javax.swing.JLabel();
        btnUserNext = new javax.swing.JButton();
        txtSearchTableUser = new javax.swing.JTextField();
        btnTableUserSearch = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableUser = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        tabApprove = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        toolBarUser4 = new javax.swing.JToolBar();
        btnUserAdd4 = new javax.swing.JButton();
        btnUserEdit4 = new javax.swing.JButton();
        btnUserRemove4 = new javax.swing.JButton();
        btnUserRefresh4 = new javax.swing.JButton();
        btnUserPrev4 = new javax.swing.JButton();
        lbPageIndex4 = new javax.swing.JLabel();
        btnUserNext4 = new javax.swing.JButton();
        txtSearchTableUser4 = new javax.swing.JTextField();
        btnTableUserSearch4 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableUser4 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        toolBarUser5 = new javax.swing.JToolBar();
        btnUserAdd5 = new javax.swing.JButton();
        btnUserEdit5 = new javax.swing.JButton();
        btnUserRemove5 = new javax.swing.JButton();
        btnUserRefresh5 = new javax.swing.JButton();
        btnUserPrev5 = new javax.swing.JButton();
        lbPageIndex5 = new javax.swing.JLabel();
        btnUserNext5 = new javax.swing.JButton();
        txtSearchTableUser5 = new javax.swing.JTextField();
        btnTableUserSearch5 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableUser5 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        toolBarUser9 = new javax.swing.JToolBar();
        btnUserAdd8 = new javax.swing.JButton();
        btnUserEdit8 = new javax.swing.JButton();
        btnUserRemove9 = new javax.swing.JButton();
        btnUserRefresh9 = new javax.swing.JButton();
        btnUserPrev9 = new javax.swing.JButton();
        lbPageIndex9 = new javax.swing.JLabel();
        btnUserNext9 = new javax.swing.JButton();
        txtSearchTableUser9 = new javax.swing.JTextField();
        btnTableUserSearch9 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableUser9 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        toolBarUser10 = new javax.swing.JToolBar();
        btnUserAdd9 = new javax.swing.JButton();
        btnUserEdit9 = new javax.swing.JButton();
        btnUserRemove10 = new javax.swing.JButton();
        btnUserRefresh10 = new javax.swing.JButton();
        btnUserPrev10 = new javax.swing.JButton();
        lbPageIndex10 = new javax.swing.JLabel();
        btnUserNext10 = new javax.swing.JButton();
        txtSearchTableUser10 = new javax.swing.JTextField();
        btnTableUserSearch10 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tableUser10 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        toolBarUser11 = new javax.swing.JToolBar();
        btnUserAdd10 = new javax.swing.JButton();
        btnUserEdit10 = new javax.swing.JButton();
        btnUserRemove11 = new javax.swing.JButton();
        btnUserRefresh11 = new javax.swing.JButton();
        btnUserPrev11 = new javax.swing.JButton();
        lbPageIndex11 = new javax.swing.JLabel();
        btnUserNext11 = new javax.swing.JButton();
        txtSearchTableUser11 = new javax.swing.JTextField();
        btnTableUserSearch11 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tableUser11 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        toolBarUser12 = new javax.swing.JToolBar();
        btnUserRemove12 = new javax.swing.JButton();
        btnUserRefresh12 = new javax.swing.JButton();
        btnUserPrev12 = new javax.swing.JButton();
        lbPageIndex12 = new javax.swing.JLabel();
        btnUserNext12 = new javax.swing.JButton();
        txtSearchTableUser12 = new javax.swing.JTextField();
        btnTableUserSearch12 = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tableUser12 = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ADMIN DASHBOARD");

        tabDetail.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.SystemColor.textHighlight, null, null));
        tabDetail.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        toolBarUser.setBorder(null);
        toolBarUser.setRollover(true);

        btnUserAdd.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\add.png")); // NOI18N
        btnUserAdd.setText("Thêm");
        buttonGroup1.add(btnUserAdd);
        btnUserAdd.setFocusable(false);
        btnUserAdd.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserAddActionPerformed(evt);
            }
        });
        toolBarUser.add(btnUserAdd);

        btnUserEdit.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\edit.png")); // NOI18N
        btnUserEdit.setText("Sửa");
        btnUserEdit.setFocusable(false);
        btnUserEdit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserEditActionPerformed(evt);
            }
        });
        toolBarUser.add(btnUserEdit);

        btnUserRemove.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\remove.png")); // NOI18N
        btnUserRemove.setText("Xoá");
        btnUserRemove.setFocusable(false);
        btnUserRemove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRemoveActionPerformed(evt);
            }
        });
        toolBarUser.add(btnUserRemove);

        btnUserRefresh.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-refresh-24.png")); // NOI18N
        btnUserRefresh.setText("Làm mới");
        btnUserRefresh.setFocusable(false);
        btnUserRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRefreshActionPerformed(evt);
            }
        });
        toolBarUser.add(btnUserRefresh);

        btnUserPrev.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-rewind-button-round-24.png")); // NOI18N
        btnUserPrev.setFocusable(false);
        btnUserPrev.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserPrev.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserPrevActionPerformed(evt);
            }
        });
        toolBarUser.add(btnUserPrev);

        lbPageIndex.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndex.setText("page");
        lbPageIndex.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndex.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser.add(lbPageIndex);

        btnUserNext.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-fast-forward-round-24.png")); // NOI18N
        btnUserNext.setFocusable(false);
        btnUserNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserNextActionPerformed(evt);
            }
        });
        toolBarUser.add(btnUserNext);

        txtSearchTableUser.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableUser.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableUser.setText("nhập tên đăng nhập, email, số điện thoại ...");
        txtSearchTableUser.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableUser.setColumns(40);
        txtSearchTableUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableUserMouseClicked(evt);
            }
        });
        txtSearchTableUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableUserKeyPressed(evt);
            }
        });
        toolBarUser.add(txtSearchTableUser);

        btnTableUserSearch.setText("Tìm kiếm");
        btnTableUserSearch.setFocusable(false);
        btnTableUserSearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTableUserSearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTableUserSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableUserSearchActionPerformed(evt);
            }
        });
        toolBarUser.add(btnTableUserSearch);

        tableUser.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableUser.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableUser.setGridColor(java.awt.SystemColor.control);
        tableUser.setRowHeight(30);
        tableUser.setRowMargin(2);
        tableUser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tableUser);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(toolBarUser, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabDetail.addTab("Người dùng", jPanel1);

        toolBarUser4.setBorder(null);
        toolBarUser4.setRollover(true);

        btnUserAdd4.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\add.png")); // NOI18N
        btnUserAdd4.setText("Thêm");
        buttonGroup1.add(btnUserAdd4);
        btnUserAdd4.setFocusable(false);
        btnUserAdd4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserAdd4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserAdd4ActionPerformed(evt);
            }
        });
        toolBarUser4.add(btnUserAdd4);

        btnUserEdit4.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\edit.png")); // NOI18N
        btnUserEdit4.setText("Sửa");
        btnUserEdit4.setFocusable(false);
        btnUserEdit4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserEdit4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserEdit4ActionPerformed(evt);
            }
        });
        toolBarUser4.add(btnUserEdit4);

        btnUserRemove4.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\remove.png")); // NOI18N
        btnUserRemove4.setText("Xoá");
        btnUserRemove4.setFocusable(false);
        btnUserRemove4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRemove4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRemove4ActionPerformed(evt);
            }
        });
        toolBarUser4.add(btnUserRemove4);

        btnUserRefresh4.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-refresh-24.png")); // NOI18N
        btnUserRefresh4.setText("Làm mới");
        btnUserRefresh4.setFocusable(false);
        btnUserRefresh4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRefresh4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRefresh4ActionPerformed(evt);
            }
        });
        toolBarUser4.add(btnUserRefresh4);

        btnUserPrev4.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-rewind-button-round-24.png")); // NOI18N
        btnUserPrev4.setFocusable(false);
        btnUserPrev4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserPrev4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserPrev4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserPrev4ActionPerformed(evt);
            }
        });
        toolBarUser4.add(btnUserPrev4);

        lbPageIndex4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndex4.setText("page");
        lbPageIndex4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndex4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser4.add(lbPageIndex4);

        btnUserNext4.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-fast-forward-round-24.png")); // NOI18N
        btnUserNext4.setFocusable(false);
        btnUserNext4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserNext4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserNext4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserNext4ActionPerformed(evt);
            }
        });
        toolBarUser4.add(btnUserNext4);

        txtSearchTableUser4.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableUser4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableUser4.setText("nhập tên đăng nhập, email, số điện thoại ...");
        txtSearchTableUser4.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableUser.setColumns(40);
        txtSearchTableUser4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableUser4MouseClicked(evt);
            }
        });
        txtSearchTableUser4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableUser4KeyPressed(evt);
            }
        });
        toolBarUser4.add(txtSearchTableUser4);

        btnTableUserSearch4.setText("Tìm kiếm");
        btnTableUserSearch4.setFocusable(false);
        btnTableUserSearch4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTableUserSearch4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTableUserSearch4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableUserSearch4ActionPerformed(evt);
            }
        });
        toolBarUser4.add(btnTableUserSearch4);

        tableUser4.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableUser4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableUser4.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableUser4.setGridColor(java.awt.SystemColor.control);
        tableUser4.setRowHeight(30);
        tableUser4.setRowMargin(2);
        tableUser4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tableUser4);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(toolBarUser4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabApprove.addTab("Tất cả", jPanel5);

        toolBarUser5.setBorder(null);
        toolBarUser5.setRollover(true);

        btnUserAdd5.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\add.png")); // NOI18N
        btnUserAdd5.setText("Thêm");
        buttonGroup1.add(btnUserAdd5);
        btnUserAdd5.setFocusable(false);
        btnUserAdd5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserAdd5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserAdd5ActionPerformed(evt);
            }
        });
        toolBarUser5.add(btnUserAdd5);

        btnUserEdit5.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\edit.png")); // NOI18N
        btnUserEdit5.setText("Sửa");
        btnUserEdit5.setFocusable(false);
        btnUserEdit5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserEdit5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserEdit5ActionPerformed(evt);
            }
        });
        toolBarUser5.add(btnUserEdit5);

        btnUserRemove5.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\remove.png")); // NOI18N
        btnUserRemove5.setText("Xoá");
        btnUserRemove5.setFocusable(false);
        btnUserRemove5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRemove5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRemove5ActionPerformed(evt);
            }
        });
        toolBarUser5.add(btnUserRemove5);

        btnUserRefresh5.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-refresh-24.png")); // NOI18N
        btnUserRefresh5.setText("Làm mới");
        btnUserRefresh5.setFocusable(false);
        btnUserRefresh5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRefresh5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRefresh5ActionPerformed(evt);
            }
        });
        toolBarUser5.add(btnUserRefresh5);

        btnUserPrev5.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-rewind-button-round-24.png")); // NOI18N
        btnUserPrev5.setFocusable(false);
        btnUserPrev5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserPrev5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserPrev5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserPrev5ActionPerformed(evt);
            }
        });
        toolBarUser5.add(btnUserPrev5);

        lbPageIndex5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndex5.setText("page");
        lbPageIndex5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndex5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser5.add(lbPageIndex5);

        btnUserNext5.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-fast-forward-round-24.png")); // NOI18N
        btnUserNext5.setFocusable(false);
        btnUserNext5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserNext5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserNext5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserNext5ActionPerformed(evt);
            }
        });
        toolBarUser5.add(btnUserNext5);

        txtSearchTableUser5.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableUser5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableUser5.setText("nhập tên đăng nhập, email, số điện thoại ...");
        txtSearchTableUser5.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableUser.setColumns(40);
        txtSearchTableUser5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableUser5MouseClicked(evt);
            }
        });
        txtSearchTableUser5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableUser5KeyPressed(evt);
            }
        });
        toolBarUser5.add(txtSearchTableUser5);

        btnTableUserSearch5.setText("Tìm kiếm");
        btnTableUserSearch5.setFocusable(false);
        btnTableUserSearch5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTableUserSearch5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTableUserSearch5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableUserSearch5ActionPerformed(evt);
            }
        });
        toolBarUser5.add(btnTableUserSearch5);

        tableUser5.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableUser5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableUser5.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableUser5.setGridColor(java.awt.SystemColor.control);
        tableUser5.setRowHeight(30);
        tableUser5.setRowMargin(2);
        tableUser5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(tableUser5);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(toolBarUser5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabApprove.addTab("Đang chờ duyệt", jPanel6);

        toolBarUser9.setBorder(null);
        toolBarUser9.setRollover(true);

        btnUserAdd8.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\add.png")); // NOI18N
        btnUserAdd8.setText("Thêm");
        buttonGroup1.add(btnUserAdd8);
        btnUserAdd8.setFocusable(false);
        btnUserAdd8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserAdd8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserAdd8ActionPerformed(evt);
            }
        });
        toolBarUser9.add(btnUserAdd8);

        btnUserEdit8.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\edit.png")); // NOI18N
        btnUserEdit8.setText("Sửa");
        btnUserEdit8.setFocusable(false);
        btnUserEdit8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserEdit8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserEdit8ActionPerformed(evt);
            }
        });
        toolBarUser9.add(btnUserEdit8);

        btnUserRemove9.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\remove.png")); // NOI18N
        btnUserRemove9.setText("Xoá");
        btnUserRemove9.setFocusable(false);
        btnUserRemove9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRemove9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRemove9ActionPerformed(evt);
            }
        });
        toolBarUser9.add(btnUserRemove9);

        btnUserRefresh9.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-refresh-24.png")); // NOI18N
        btnUserRefresh9.setText("Làm mới");
        btnUserRefresh9.setFocusable(false);
        btnUserRefresh9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRefresh9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRefresh9ActionPerformed(evt);
            }
        });
        toolBarUser9.add(btnUserRefresh9);

        btnUserPrev9.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-rewind-button-round-24.png")); // NOI18N
        btnUserPrev9.setFocusable(false);
        btnUserPrev9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserPrev9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserPrev9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserPrev9ActionPerformed(evt);
            }
        });
        toolBarUser9.add(btnUserPrev9);

        lbPageIndex9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndex9.setText("page");
        lbPageIndex9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndex9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser9.add(lbPageIndex9);

        btnUserNext9.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-fast-forward-round-24.png")); // NOI18N
        btnUserNext9.setFocusable(false);
        btnUserNext9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserNext9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserNext9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserNext9ActionPerformed(evt);
            }
        });
        toolBarUser9.add(btnUserNext9);

        txtSearchTableUser9.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableUser9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableUser9.setText("nhập tên đăng nhập, email, số điện thoại ...");
        txtSearchTableUser9.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableUser.setColumns(40);
        txtSearchTableUser9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableUser9MouseClicked(evt);
            }
        });
        txtSearchTableUser9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableUser9KeyPressed(evt);
            }
        });
        toolBarUser9.add(txtSearchTableUser9);

        btnTableUserSearch9.setText("Tìm kiếm");
        btnTableUserSearch9.setFocusable(false);
        btnTableUserSearch9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTableUserSearch9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTableUserSearch9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableUserSearch9ActionPerformed(evt);
            }
        });
        toolBarUser9.add(btnTableUserSearch9);

        tableUser9.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableUser9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableUser9.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableUser9.setGridColor(java.awt.SystemColor.control);
        tableUser9.setRowHeight(30);
        tableUser9.setRowMargin(2);
        tableUser9.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(tableUser9);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(toolBarUser9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabApprove.addTab("Đã duyệt", jPanel7);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabApprove)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabApprove)
        );

        tabDetail.addTab("Xe", jPanel2);

        toolBarUser10.setBorder(null);
        toolBarUser10.setRollover(true);

        btnUserAdd9.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\add.png")); // NOI18N
        btnUserAdd9.setText("Thêm");
        buttonGroup1.add(btnUserAdd9);
        btnUserAdd9.setFocusable(false);
        btnUserAdd9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserAdd9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserAdd9ActionPerformed(evt);
            }
        });
        toolBarUser10.add(btnUserAdd9);

        btnUserEdit9.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\edit.png")); // NOI18N
        btnUserEdit9.setText("Sửa");
        btnUserEdit9.setFocusable(false);
        btnUserEdit9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserEdit9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserEdit9ActionPerformed(evt);
            }
        });
        toolBarUser10.add(btnUserEdit9);

        btnUserRemove10.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\remove.png")); // NOI18N
        btnUserRemove10.setText("Xoá");
        btnUserRemove10.setFocusable(false);
        btnUserRemove10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRemove10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRemove10ActionPerformed(evt);
            }
        });
        toolBarUser10.add(btnUserRemove10);

        btnUserRefresh10.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-refresh-24.png")); // NOI18N
        btnUserRefresh10.setText("Làm mới");
        btnUserRefresh10.setFocusable(false);
        btnUserRefresh10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRefresh10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRefresh10ActionPerformed(evt);
            }
        });
        toolBarUser10.add(btnUserRefresh10);

        btnUserPrev10.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-rewind-button-round-24.png")); // NOI18N
        btnUserPrev10.setFocusable(false);
        btnUserPrev10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserPrev10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserPrev10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserPrev10ActionPerformed(evt);
            }
        });
        toolBarUser10.add(btnUserPrev10);

        lbPageIndex10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndex10.setText("page");
        lbPageIndex10.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndex10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser10.add(lbPageIndex10);

        btnUserNext10.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-fast-forward-round-24.png")); // NOI18N
        btnUserNext10.setFocusable(false);
        btnUserNext10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserNext10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserNext10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserNext10ActionPerformed(evt);
            }
        });
        toolBarUser10.add(btnUserNext10);

        txtSearchTableUser10.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableUser10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableUser10.setText("nhập tên đăng nhập, email, số điện thoại ...");
        txtSearchTableUser10.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableUser.setColumns(40);
        txtSearchTableUser10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableUser10MouseClicked(evt);
            }
        });
        txtSearchTableUser10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableUser10KeyPressed(evt);
            }
        });
        toolBarUser10.add(txtSearchTableUser10);

        btnTableUserSearch10.setText("Tìm kiếm");
        btnTableUserSearch10.setFocusable(false);
        btnTableUserSearch10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTableUserSearch10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTableUserSearch10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableUserSearch10ActionPerformed(evt);
            }
        });
        toolBarUser10.add(btnTableUserSearch10);

        tableUser10.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableUser10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableUser10.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableUser10.setGridColor(java.awt.SystemColor.control);
        tableUser10.setRowHeight(30);
        tableUser10.setRowMargin(2);
        tableUser10.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane11.setViewportView(tableUser10);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(toolBarUser10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabDetail.addTab("Thương Hiệu", jPanel3);

        toolBarUser11.setBorder(null);
        toolBarUser11.setRollover(true);

        btnUserAdd10.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\add.png")); // NOI18N
        btnUserAdd10.setText("Thêm");
        buttonGroup1.add(btnUserAdd10);
        btnUserAdd10.setFocusable(false);
        btnUserAdd10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserAdd10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserAdd10ActionPerformed(evt);
            }
        });
        toolBarUser11.add(btnUserAdd10);

        btnUserEdit10.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\edit.png")); // NOI18N
        btnUserEdit10.setText("Sửa");
        btnUserEdit10.setFocusable(false);
        btnUserEdit10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserEdit10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserEdit10ActionPerformed(evt);
            }
        });
        toolBarUser11.add(btnUserEdit10);

        btnUserRemove11.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\remove.png")); // NOI18N
        btnUserRemove11.setText("Xoá");
        btnUserRemove11.setFocusable(false);
        btnUserRemove11.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRemove11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRemove11ActionPerformed(evt);
            }
        });
        toolBarUser11.add(btnUserRemove11);

        btnUserRefresh11.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-refresh-24.png")); // NOI18N
        btnUserRefresh11.setText("Làm mới");
        btnUserRefresh11.setFocusable(false);
        btnUserRefresh11.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRefresh11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRefresh11ActionPerformed(evt);
            }
        });
        toolBarUser11.add(btnUserRefresh11);

        btnUserPrev11.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-rewind-button-round-24.png")); // NOI18N
        btnUserPrev11.setFocusable(false);
        btnUserPrev11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserPrev11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserPrev11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserPrev11ActionPerformed(evt);
            }
        });
        toolBarUser11.add(btnUserPrev11);

        lbPageIndex11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndex11.setText("page");
        lbPageIndex11.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndex11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser11.add(lbPageIndex11);

        btnUserNext11.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-fast-forward-round-24.png")); // NOI18N
        btnUserNext11.setFocusable(false);
        btnUserNext11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserNext11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserNext11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserNext11ActionPerformed(evt);
            }
        });
        toolBarUser11.add(btnUserNext11);

        txtSearchTableUser11.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableUser11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableUser11.setText("nhập tên đăng nhập, email, số điện thoại ...");
        txtSearchTableUser11.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableUser.setColumns(40);
        txtSearchTableUser11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableUser11MouseClicked(evt);
            }
        });
        txtSearchTableUser11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableUser11KeyPressed(evt);
            }
        });
        toolBarUser11.add(txtSearchTableUser11);

        btnTableUserSearch11.setText("Tìm kiếm");
        btnTableUserSearch11.setFocusable(false);
        btnTableUserSearch11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTableUserSearch11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTableUserSearch11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableUserSearch11ActionPerformed(evt);
            }
        });
        toolBarUser11.add(btnTableUserSearch11);

        tableUser11.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableUser11.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableUser11.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableUser11.setGridColor(java.awt.SystemColor.control);
        tableUser11.setRowHeight(30);
        tableUser11.setRowMargin(2);
        tableUser11.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane12.setViewportView(tableUser11);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(toolBarUser11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabDetail.addTab("Đặc Điểm, Tính Năng", jPanel4);

        toolBarUser12.setBorder(null);
        toolBarUser12.setRollover(true);

        btnUserRemove12.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\remove.png")); // NOI18N
        btnUserRemove12.setText("Xoá");
        btnUserRemove12.setFocusable(false);
        btnUserRemove12.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRemove12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRemove12ActionPerformed(evt);
            }
        });
        toolBarUser12.add(btnUserRemove12);

        btnUserRefresh12.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-refresh-24.png")); // NOI18N
        btnUserRefresh12.setText("Làm mới");
        btnUserRefresh12.setFocusable(false);
        btnUserRefresh12.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRefresh12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRefresh12ActionPerformed(evt);
            }
        });
        toolBarUser12.add(btnUserRefresh12);

        btnUserPrev12.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-rewind-button-round-24.png")); // NOI18N
        btnUserPrev12.setFocusable(false);
        btnUserPrev12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserPrev12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserPrev12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserPrev12ActionPerformed(evt);
            }
        });
        toolBarUser12.add(btnUserPrev12);

        lbPageIndex12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndex12.setText("page");
        lbPageIndex12.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndex12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser12.add(lbPageIndex12);

        btnUserNext12.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-fast-forward-round-24.png")); // NOI18N
        btnUserNext12.setFocusable(false);
        btnUserNext12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserNext12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserNext12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserNext12ActionPerformed(evt);
            }
        });
        toolBarUser12.add(btnUserNext12);

        txtSearchTableUser12.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableUser12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableUser12.setText("nhập tên đăng nhập, email, số điện thoại ...");
        txtSearchTableUser12.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableUser.setColumns(40);
        txtSearchTableUser12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableUser12MouseClicked(evt);
            }
        });
        txtSearchTableUser12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableUser12KeyPressed(evt);
            }
        });
        toolBarUser12.add(txtSearchTableUser12);

        btnTableUserSearch12.setText("Tìm kiếm");
        btnTableUserSearch12.setFocusable(false);
        btnTableUserSearch12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTableUserSearch12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTableUserSearch12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableUserSearch12ActionPerformed(evt);
            }
        });
        toolBarUser12.add(btnTableUserSearch12);

        tableUser12.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableUser12.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableUser12.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableUser12.setGridColor(java.awt.SystemColor.control);
        tableUser12.setRowHeight(30);
        tableUser12.setRowMargin(2);
        tableUser12.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane13.setViewportView(tableUser12);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(toolBarUser12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Log", jPanel13);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 983, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Cài đặt", jPanel14);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        tabDetail.addTab("Cấu hình", jPanel9);

        jMenu1.setIcon(new javax.swing.ImageIcon("D:\\Project\\Project Netbean\\BOOKING_CAR\\resource\\icons8-settings-24.png")); // NOI18N
        jMenu1.setText("Cài đặt");

        jMenuItem1.setText("Thông tin cá nhân");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Đổi mật khẩu");
        jMenu1.add(jMenuItem2);

        jMenuItem4.setText("Đăng xuất");
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabDetail)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabDetail)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUserAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserAddActionPerformed
        add.run();
    }//GEN-LAST:event_btnUserAddActionPerformed

    private void btnUserEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserEditActionPerformed
        int row = tableUser.getSelectedRow();
        try {
            String username = (String) tableUser.getModel().getValueAt(row, 1);
            String email = (String) tableUser.getModel().getValueAt(row, 2);
            String phoneNumber = (String) tableUser.getModel().getValueAt(row, 3);
            String name = (String) tableUser.getModel().getValueAt(row, 4);
            AppUser appUser = new AppUser();
            appUser.setEmail(email);
            appUser.setUsername(username);
            appUser.setPhoneNumber(phoneNumber);
            appUser.setName(name);
            new edit().run(appUser);
        } catch (Exception e) {

        }

    }//GEN-LAST:event_btnUserEditActionPerformed

    private void btnUserNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserNextActionPerformed
        int temp = pageIndexUser + 1;
        if(temp <= totalPageUser) {
            pageIndexUser++;
             new Thread(() -> {
                getUsers(false);
            }).start();
        }else {
            pageIndexUser = totalPageUser;
        }
        
    }//GEN-LAST:event_btnUserNextActionPerformed

    private void btnUserPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserPrevActionPerformed
        int temp = pageIndexUser - 1;
        if((temp + 1) > 1) {
            pageIndexUser--;
            new Thread(() -> {
                getUsers(false);
            }).start();
        }else {
            pageIndexUser = 1;
        }
    }//GEN-LAST:event_btnUserPrevActionPerformed

    private void btnUserRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRefreshActionPerformed
        new Thread(() -> {
            getUsers(true);
        }).start();
    }//GEN-LAST:event_btnUserRefreshActionPerformed

    private void btnUserRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRemoveActionPerformed
        int row = tableUser.getSelectedRow();
        try {
            String username = (String) tableUser.getModel().getValueAt(row, 1);
            int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xoá " + username + "", "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
            if (result == JOptionPane.OK_OPTION) {
                deleteAppUser(username);
            }
        } catch (HeadlessException | IOException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng cần xoá", "Thông báo", TrayIcon.MessageType.INFO.ordinal(), null);
        }

    }//GEN-LAST:event_btnUserRemoveActionPerformed

    private void txtSearchTableUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUserKeyPressed
        if (txtSearchTableUser.getForeground() != Color.BLACK) {
            txtSearchTableUser.setForeground(Color.BLACK);
            txtSearchTableUser.setText("");
            txtSearchTableUser.setText(String.valueOf(evt.getKeyChar()));
        }
    }//GEN-LAST:event_txtSearchTableUserKeyPressed

    private void btnTableUserSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearchActionPerformed
        String text = txtSearchTableUser.getText();
        searchTableContents(text);
    }//GEN-LAST:event_btnTableUserSearchActionPerformed

    private void txtSearchTableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUserMouseClicked
        if (txtSearchTableUser.getForeground() != Color.BLACK) {
            txtSearchTableUser.setForeground(Color.BLACK);
            txtSearchTableUser.setText("");
        }
    }//GEN-LAST:event_txtSearchTableUserMouseClicked

    private void btnUserAdd4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserAdd4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserAdd4ActionPerformed

    private void btnUserEdit4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserEdit4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserEdit4ActionPerformed

    private void btnUserRemove4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRemove4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRemove4ActionPerformed

    private void btnUserRefresh4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRefresh4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRefresh4ActionPerformed

    private void btnUserPrev4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserPrev4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserPrev4ActionPerformed

    private void btnUserNext4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserNext4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserNext4ActionPerformed

    private void txtSearchTableUser4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUser4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser4MouseClicked

    private void txtSearchTableUser4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUser4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser4KeyPressed

    private void btnTableUserSearch4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTableUserSearch4ActionPerformed

    private void btnUserAdd5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserAdd5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserAdd5ActionPerformed

    private void btnUserEdit5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserEdit5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserEdit5ActionPerformed

    private void btnUserRemove5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRemove5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRemove5ActionPerformed

    private void btnUserRefresh5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRefresh5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRefresh5ActionPerformed

    private void btnUserPrev5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserPrev5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserPrev5ActionPerformed

    private void btnUserNext5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserNext5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserNext5ActionPerformed

    private void txtSearchTableUser5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUser5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser5MouseClicked

    private void txtSearchTableUser5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUser5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser5KeyPressed

    private void btnTableUserSearch5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTableUserSearch5ActionPerformed

    private void btnUserAdd8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserAdd8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserAdd8ActionPerformed

    private void btnUserEdit8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserEdit8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserEdit8ActionPerformed

    private void btnUserRemove9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRemove9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRemove9ActionPerformed

    private void btnUserRefresh9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRefresh9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRefresh9ActionPerformed

    private void btnUserPrev9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserPrev9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserPrev9ActionPerformed

    private void btnUserNext9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserNext9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserNext9ActionPerformed

    private void txtSearchTableUser9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUser9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser9MouseClicked

    private void txtSearchTableUser9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUser9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser9KeyPressed

    private void btnTableUserSearch9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTableUserSearch9ActionPerformed

    private void btnUserAdd9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserAdd9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserAdd9ActionPerformed

    private void btnUserEdit9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserEdit9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserEdit9ActionPerformed

    private void btnUserRemove10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRemove10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRemove10ActionPerformed

    private void btnUserRefresh10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRefresh10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRefresh10ActionPerformed

    private void btnUserPrev10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserPrev10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserPrev10ActionPerformed

    private void btnUserNext10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserNext10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserNext10ActionPerformed

    private void txtSearchTableUser10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUser10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser10MouseClicked

    private void txtSearchTableUser10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUser10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser10KeyPressed

    private void btnTableUserSearch10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTableUserSearch10ActionPerformed

    private void btnUserAdd10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserAdd10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserAdd10ActionPerformed

    private void btnUserEdit10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserEdit10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserEdit10ActionPerformed

    private void btnUserRemove11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRemove11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRemove11ActionPerformed

    private void btnUserRefresh11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRefresh11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRefresh11ActionPerformed

    private void btnUserPrev11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserPrev11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserPrev11ActionPerformed

    private void btnUserNext11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserNext11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserNext11ActionPerformed

    private void txtSearchTableUser11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUser11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser11MouseClicked

    private void txtSearchTableUser11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUser11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser11KeyPressed

    private void btnTableUserSearch11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTableUserSearch11ActionPerformed

    private void btnUserRemove12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRemove12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRemove12ActionPerformed

    private void btnUserRefresh12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRefresh12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRefresh12ActionPerformed

    private void btnUserPrev12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserPrev12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserPrev12ActionPerformed

    private void btnUserNext12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserNext12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserNext12ActionPerformed

    private void txtSearchTableUser12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUser12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser12MouseClicked

    private void txtSearchTableUser12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUser12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser12KeyPressed

    private void btnTableUserSearch12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTableUserSearch12ActionPerformed
    public void searchTableContents(String searchString) {
        new Thread(() -> {
            DefaultTableModel temp = (DefaultTableModel) tableUser.getModel();
            List<Vector> list = new ArrayList<>();
            for (Object rows : temp.getDataVector()) {
                Vector rowVector = (Vector) rows;
                try {
                    for (Object column : rowVector) {
                        if (column.toString().contains(searchString)) {
                            //content found so adding to table
                            list.add(rowVector);
                            break;
                        }
                    }
                } catch (NullPointerException e) {
                }

            }

            ((DefaultTableModel) tableUser.getModel()).setRowCount(0);

            list.forEach(row -> {
                ((DefaultTableModel) tableUser.getModel()).addRow(row);
            });
        }).start();

    }

    private void deleteAppUser(String username) throws ProtocolException, IOException {
        // call API
        String url = "/AppUsers/delete";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("username", username);
        Map<Program.HttpHeader, String> headers = new HashMap<>();
        headers.put(Program.HttpHeader.Authorization, Program.PrefixToken + Program.Token);

        String jsonData = Program.SendHttp(url, Program.HttpMethod.DELETE, null, queryParams, headers);
        Map<String, Object> response = new Gson().fromJson(jsonData, Map.class);
        boolean isSuccess = (boolean) response.get("success");
        if (!isSuccess) {
            String message = (String) response.get("message");
            JOptionPane.showMessageDialog(this, message, "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
        } else {
            JOptionPane.showMessageDialog(this, response.get("data").toString(), "Thông báo", TrayIcon.MessageType.NONE.ordinal(), null);
        }
    }

    /**
     * @param args the command line arguments
     */
    public void run() {
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
            this.setVisible(true);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        // call api get all appUsers
        new Thread(() -> {
            getUsers(false);
        }).start();

//            // call api get all cars
//            new Thread(() -> {
//
//            }).start();
//
//            // call api get all brands
//            new Thread(() -> {
//
//            }).start();
//
//            // call api get all details
//            new Thread(() -> {
//
//            }).start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTableUserSearch;
    private javax.swing.JButton btnTableUserSearch10;
    private javax.swing.JButton btnTableUserSearch11;
    private javax.swing.JButton btnTableUserSearch12;
    private javax.swing.JButton btnTableUserSearch4;
    private javax.swing.JButton btnTableUserSearch5;
    private javax.swing.JButton btnTableUserSearch9;
    private javax.swing.JButton btnUserAdd;
    private javax.swing.JButton btnUserAdd10;
    private javax.swing.JButton btnUserAdd4;
    private javax.swing.JButton btnUserAdd5;
    private javax.swing.JButton btnUserAdd8;
    private javax.swing.JButton btnUserAdd9;
    private javax.swing.JButton btnUserEdit;
    private javax.swing.JButton btnUserEdit10;
    private javax.swing.JButton btnUserEdit4;
    private javax.swing.JButton btnUserEdit5;
    private javax.swing.JButton btnUserEdit8;
    private javax.swing.JButton btnUserEdit9;
    private javax.swing.JButton btnUserNext;
    private javax.swing.JButton btnUserNext10;
    private javax.swing.JButton btnUserNext11;
    private javax.swing.JButton btnUserNext12;
    private javax.swing.JButton btnUserNext4;
    private javax.swing.JButton btnUserNext5;
    private javax.swing.JButton btnUserNext9;
    private javax.swing.JButton btnUserPrev;
    private javax.swing.JButton btnUserPrev10;
    private javax.swing.JButton btnUserPrev11;
    private javax.swing.JButton btnUserPrev12;
    private javax.swing.JButton btnUserPrev4;
    private javax.swing.JButton btnUserPrev5;
    private javax.swing.JButton btnUserPrev9;
    private javax.swing.JButton btnUserRefresh;
    private javax.swing.JButton btnUserRefresh10;
    private javax.swing.JButton btnUserRefresh11;
    private javax.swing.JButton btnUserRefresh12;
    private javax.swing.JButton btnUserRefresh4;
    private javax.swing.JButton btnUserRefresh5;
    private javax.swing.JButton btnUserRefresh9;
    private javax.swing.JButton btnUserRemove;
    private javax.swing.JButton btnUserRemove10;
    private javax.swing.JButton btnUserRemove11;
    private javax.swing.JButton btnUserRemove12;
    private javax.swing.JButton btnUserRemove4;
    private javax.swing.JButton btnUserRemove5;
    private javax.swing.JButton btnUserRemove9;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbPageIndex;
    private javax.swing.JLabel lbPageIndex10;
    private javax.swing.JLabel lbPageIndex11;
    private javax.swing.JLabel lbPageIndex12;
    private javax.swing.JLabel lbPageIndex4;
    private javax.swing.JLabel lbPageIndex5;
    private javax.swing.JLabel lbPageIndex9;
    private javax.swing.JTabbedPane tabApprove;
    private javax.swing.JTabbedPane tabDetail;
    private javax.swing.JTable tableUser;
    private javax.swing.JTable tableUser10;
    private javax.swing.JTable tableUser11;
    private javax.swing.JTable tableUser12;
    private javax.swing.JTable tableUser4;
    private javax.swing.JTable tableUser5;
    private javax.swing.JTable tableUser9;
    private javax.swing.JToolBar toolBarUser;
    private javax.swing.JToolBar toolBarUser10;
    private javax.swing.JToolBar toolBarUser11;
    private javax.swing.JToolBar toolBarUser12;
    private javax.swing.JToolBar toolBarUser4;
    private javax.swing.JToolBar toolBarUser5;
    private javax.swing.JToolBar toolBarUser9;
    private javax.swing.JTextField txtSearchTableUser;
    private javax.swing.JTextField txtSearchTableUser10;
    private javax.swing.JTextField txtSearchTableUser11;
    private javax.swing.JTextField txtSearchTableUser12;
    private javax.swing.JTextField txtSearchTableUser4;
    private javax.swing.JTextField txtSearchTableUser5;
    private javax.swing.JTextField txtSearchTableUser9;
    // End of variables declaration//GEN-END:variables

}
