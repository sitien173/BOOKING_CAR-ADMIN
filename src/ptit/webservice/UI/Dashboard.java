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
import ptit.webservice.UI.AppUser.add;
import ptit.webservice.UI.AppUser.edit;
import ptit.webservice.main.Program;
import ptit.webservice.model.AppLog.AppLog;
import ptit.webservice.model.AppUser.AppUser;
import ptit.webservice.model.Brand.Brand;
import ptit.webservice.model.Detail.Detail;
import ptit.webservice.model.DetailType.DetailType;
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

    private static int pageIndexBrand = 1;
    private static final int limitBrand = 20;
    private static int totalPageBrand = 0;
    private static final Map<Integer, List<Brand>> cacheBrand = new HashMap<>();

    private static int pageIndexDetailType = 1;
    private static final int limitDetailType = 20;
    private static int totalPageDetailType = 0;
    private static final Map<Integer, List<DetailType>> cacheDetailType = new HashMap<>();

    private static int pageIndexDetail = 1;
    private static final int limitDetail = 20;
    private static int totalPageDetail = 0;
    private static final Map<Integer, List<Detail>> cacheDetail = new HashMap<>();

    private void getUsers(boolean isRefresh) {
        try {
            List<AppUser> appUsers = cacheAppUser.get(pageIndexUser);
            if (appUsers == null || isRefresh) {
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

    private void getBrands(boolean isRefresh) {
        try {
            List<Brand> brands = cacheBrand.get(pageIndexBrand);
            if (brands == null || isRefresh) {
                String url = "/Brands/list";
                Map<String, String> params = new HashMap<>();
                params.put("pageIndex", String.valueOf(pageIndexBrand));
                params.put("limit", String.valueOf(limitBrand));

                Map<Program.HttpHeader, String> headers = new HashMap<>();
                headers.put(Program.HttpHeader.Authorization, Program.PrefixToken + Program.Token);
                headers.put(Program.HttpHeader.ContentType, "application/json; charset=utf-8");

                String jsonData = Program.SendHttpGet(url, params, headers);

                java.lang.reflect.Type type = new TypeToken<ResponseModel<PagingModel<Brand>>>() {
                }.getType();
                ResponseModel<PagingModel<Brand>> response = new Gson().fromJson(jsonData, type);
                if (!response.isSuccess()) {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
                }
                brands = response.getData().getItems();
                totalPageBrand = response.getData().getTotalPage();
                cacheBrand.put(pageIndexBrand, brands);
            }

            DefaultTableModel model = (DefaultTableModel) tableBrand.getModel();
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
            model.addColumn("Name");
            model.addColumn("Code");

            // model.addColumn("Avatar");
            int size = brands.size();
            for (int i = 0; i < size; i++) {
                Vector vt = new Vector<>();
                vt.add(String.valueOf(brands.get(i).getId()));
                vt.add(brands.get(i).getName());
                vt.add(brands.get(i).getCode());
                model.addRow(vt);
            }
            lbBrandPageIndex.setText(String.valueOf(pageIndexBrand));

        } catch (ProtocolException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getDetailTypes(boolean isRefresh) {
        try {
            List<DetailType> detailTypes = cacheDetailType.get(pageIndexDetailType);
            if (detailTypes == null || isRefresh) {
                String url = "/DetailTypes/list";
                Map<String, String> params = new HashMap<>();
                params.put("pageIndex", String.valueOf(pageIndexDetailType));
                params.put("limit", String.valueOf(limitDetailType));

                Map<Program.HttpHeader, String> headers = new HashMap<>();
                headers.put(Program.HttpHeader.Authorization, Program.PrefixToken + Program.Token);
                headers.put(Program.HttpHeader.ContentType, "application/json; charset=utf-8");

                String jsonData = Program.SendHttpGet(url, params, headers);

                java.lang.reflect.Type type = new TypeToken<ResponseModel<PagingModel<DetailType>>>() {
                }.getType();
                ResponseModel<PagingModel<DetailType>> response = new Gson().fromJson(jsonData, type);
                if (!response.isSuccess()) {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
                }
                detailTypes = response.getData().getItems();
                totalPageDetailType = response.getData().getTotalPage();
                cacheDetailType.put(pageIndexDetailType, detailTypes);
            }

            DefaultTableModel model = (DefaultTableModel) tableDetailType.getModel();
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
            model.addColumn("Name");
            model.addColumn("Code");
            model.addColumn("Icon");
            model.addColumn("Description");
            model.addColumn("Parent Id");

            // model.addColumn("Avatar");
            int size = detailTypes.size();
            for (int i = 0; i < size; i++) {
                Vector vt = new Vector<>();
                vt.add(String.valueOf(detailTypes.get(i).getId()));
                vt.add(detailTypes.get(i).getName());
                vt.add(detailTypes.get(i).getCode());
                vt.add(detailTypes.get(i).getIcon());
                vt.add(detailTypes.get(i).getDescription());
                if (detailTypes.get(i).getParent() != null) {
                    vt.add(String.valueOf(detailTypes.get(i).getParent().getId()));
                }
                model.addRow(vt);
            }
            lbDetailTypePageIndex.setText(String.valueOf(pageIndexDetailType));

        } catch (ProtocolException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getDetail(boolean isRefresh) {
        try {
            List<Detail> details = cacheDetail.get(pageIndexDetail);
            if (details == null || isRefresh) {
                String url = "/Detail/list";
                Map<String, String> params = new HashMap<>();
                params.put("pageIndex", String.valueOf(pageIndexDetail));
                params.put("limit", String.valueOf(limitDetail));

                Map<Program.HttpHeader, String> headers = new HashMap<>();
                headers.put(Program.HttpHeader.Authorization, Program.PrefixToken + Program.Token);
                headers.put(Program.HttpHeader.ContentType, "application/json; charset=utf-8");

                String jsonData = Program.SendHttpGet(url, params, headers);

                java.lang.reflect.Type type = new TypeToken<ResponseModel<PagingModel<Detail>>>() {
                }.getType();
                ResponseModel<PagingModel<Detail>> response = new Gson().fromJson(jsonData, type);
                if (!response.isSuccess()) {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
                }
                details = response.getData().getItems();
                totalPageDetail = response.getData().getTotalPage();
                cacheDetail.put(pageIndexDetail, details);
            }

            DefaultTableModel model = (DefaultTableModel) tableDetail.getModel();
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
            model.addColumn("Val");
            model.addColumn("DetailType_Id");
            model.addColumn("DetailType_Name");
            model.addColumn("DetailType_Code");;

            // model.addColumn("Avatar");
            int size = details.size();
            for (int i = 0; i < size; i++) {
                Vector vt = new Vector<>();
                vt.add(String.valueOf(details.get(i).getId()));
                vt.add(String.valueOf(details.get(i).getVal()));
                vt.add(String.valueOf(details.get(i).getDetailType().getId()));
                vt.add(String.valueOf(details.get(i).getDetailType().getName()));
                vt.add(String.valueOf(details.get(i).getDetailType().getCode()));
                model.addRow(vt);
            }
            lbDetailPageIndex.setText(String.valueOf(pageIndexDetail));

        } catch (ProtocolException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getLogs() {
        try {
            String url = "/AppLogs/list";

            Map<Program.HttpHeader, String> headers = new HashMap<>();
            headers.put(Program.HttpHeader.Authorization, Program.PrefixToken + Program.Token);
            headers.put(Program.HttpHeader.ContentType, "application/json; charset=utf-8");

            String jsonData = Program.SendHttpGet(url, null, headers);

            java.lang.reflect.Type type = new TypeToken<ResponseModel<List<AppLog>>>() {
            }.getType();
            ResponseModel<List<AppLog>> response = new Gson().fromJson(jsonData, type);
            if (!response.isSuccess()) {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
            }
            List<AppLog> logs = response.getData();;

            DefaultTableModel model = (DefaultTableModel) tableLog.getModel();
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
            model.addColumn("Exception");
            model.addColumn("Message");
            model.addColumn("Method");
            model.addColumn("Params");
            model.addColumn("RequestUrl");;
            model.addColumn("Status");;
            model.addColumn("CreatedAt");;
            model.addColumn("CreatedBy");;
            model.addColumn("Query");;
            model.addColumn("Header");;
            model.addColumn("Host");;

            // model.addColumn("Avatar");
            logs.forEach(item -> {
                Vector vt = new Vector();
                vt.add(String.valueOf(item.getId()));
                vt.add(item.getException());
                vt.add(item.getMessage());
                vt.add(item.getMethod());
                vt.add(item.getParams());
                vt.add(item.getRequest_url());
                vt.add(String.valueOf(item.getStatus()));
                vt.add(item.getCreated_at());
                vt.add(item.getCreated_by());
                vt.add(item.getQuery());
                vt.add(item.getHeader());
                vt.add(item.getHost());
                model.addRow(vt);
            });
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
        toolBarUser1 = new javax.swing.JToolBar();
        btnUserAdd1 = new javax.swing.JButton();
        btnUserEdit1 = new javax.swing.JButton();
        btnUserRemove1 = new javax.swing.JButton();
        btnUserRefresh1 = new javax.swing.JButton();
        btnUserPrev1 = new javax.swing.JButton();
        lbPageIndex1 = new javax.swing.JLabel();
        btnUserNext1 = new javax.swing.JButton();
        txtSearchTableUser1 = new javax.swing.JTextField();
        btnTableUserSearch1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableUser1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        toolBarUser2 = new javax.swing.JToolBar();
        btnUserAdd2 = new javax.swing.JButton();
        btnUserEdit2 = new javax.swing.JButton();
        btnUserRemove2 = new javax.swing.JButton();
        btnUserRefresh2 = new javax.swing.JButton();
        btnUserPrev2 = new javax.swing.JButton();
        lbPageIndex2 = new javax.swing.JLabel();
        btnUserNext2 = new javax.swing.JButton();
        txtSearchTableUser2 = new javax.swing.JTextField();
        btnTableUserSearch2 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableUser2 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        toolBarUser3 = new javax.swing.JToolBar();
        btnUserAdd3 = new javax.swing.JButton();
        btnUserEdit3 = new javax.swing.JButton();
        btnUserRemove3 = new javax.swing.JButton();
        btnUserRefresh3 = new javax.swing.JButton();
        btnUserPrev3 = new javax.swing.JButton();
        lbPageIndex3 = new javax.swing.JLabel();
        btnUserNext3 = new javax.swing.JButton();
        txtSearchTableUser3 = new javax.swing.JTextField();
        btnTableUserSearch3 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableUser3 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        toolBarUser6 = new javax.swing.JToolBar();
        btnBrandAdd = new javax.swing.JButton();
        btnBrandEdit = new javax.swing.JButton();
        btnBrandRemove = new javax.swing.JButton();
        btnBrandRefresh = new javax.swing.JButton();
        btnBrandPrev = new javax.swing.JButton();
        lbBrandPageIndex = new javax.swing.JLabel();
        btnBrandNext = new javax.swing.JButton();
        txtSearchTableUser6 = new javax.swing.JTextField();
        btnTableUserSearch6 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableBrand = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel18 = new javax.swing.JPanel();
        toolBarUser9 = new javax.swing.JToolBar();
        btnDetailTypeAdd = new javax.swing.JButton();
        btnDetailTypeEdit = new javax.swing.JButton();
        btnDetailTypeRemove = new javax.swing.JButton();
        btnDetailTypeRefresh = new javax.swing.JButton();
        btnDetailTypePrev = new javax.swing.JButton();
        lbDetailTypePageIndex = new javax.swing.JLabel();
        btnDetailTypeNext = new javax.swing.JButton();
        txtSearchTableUser9 = new javax.swing.JTextField();
        btnTableUserSearch9 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tableDetailType = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        toolBarUser10 = new javax.swing.JToolBar();
        btnDetailAdd = new javax.swing.JButton();
        btnDetailEdit = new javax.swing.JButton();
        btnDetailRemove = new javax.swing.JButton();
        btnDetailRefresh = new javax.swing.JButton();
        btnDetailPrev = new javax.swing.JButton();
        lbDetailPageIndex = new javax.swing.JLabel();
        btnDetailNext = new javax.swing.JButton();
        txtSearchTableUser10 = new javax.swing.JTextField();
        btnTableUserSearch10 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tableDetail = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        toolBarUser8 = new javax.swing.JToolBar();
        btnLogRemove = new javax.swing.JButton();
        btnLogRefresh = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tableLog = new javax.swing.JTable();
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

        btnUserAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/add.png"))); // NOI18N
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

        btnUserEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/edit.png"))); // NOI18N
        btnUserEdit.setText("Sửa");
        btnUserEdit.setFocusable(false);
        btnUserEdit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserEditActionPerformed(evt);
            }
        });
        toolBarUser.add(btnUserEdit);

        btnUserRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/remove.png"))); // NOI18N
        btnUserRemove.setText("Xoá");
        btnUserRemove.setFocusable(false);
        btnUserRemove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRemoveActionPerformed(evt);
            }
        });
        toolBarUser.add(btnUserRemove);

        btnUserRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-refresh-24.png"))); // NOI18N
        btnUserRefresh.setText("Làm mới");
        btnUserRefresh.setFocusable(false);
        btnUserRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRefreshActionPerformed(evt);
            }
        });
        toolBarUser.add(btnUserRefresh);

        btnUserPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-rewind-button-round-24.png"))); // NOI18N
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

        btnUserNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-fast-forward-round-24.png"))); // NOI18N
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

        toolBarUser1.setBorder(null);
        toolBarUser1.setRollover(true);

        btnUserAdd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/add.png"))); // NOI18N
        btnUserAdd1.setText("Thêm");
        buttonGroup1.add(btnUserAdd1);
        btnUserAdd1.setFocusable(false);
        btnUserAdd1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserAdd1ActionPerformed(evt);
            }
        });
        toolBarUser1.add(btnUserAdd1);

        btnUserEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/edit.png"))); // NOI18N
        btnUserEdit1.setText("Sửa");
        btnUserEdit1.setFocusable(false);
        btnUserEdit1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserEdit1ActionPerformed(evt);
            }
        });
        toolBarUser1.add(btnUserEdit1);

        btnUserRemove1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/remove.png"))); // NOI18N
        btnUserRemove1.setText("Xoá");
        btnUserRemove1.setFocusable(false);
        btnUserRemove1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRemove1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRemove1ActionPerformed(evt);
            }
        });
        toolBarUser1.add(btnUserRemove1);

        btnUserRefresh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-refresh-24.png"))); // NOI18N
        btnUserRefresh1.setText("Làm mới");
        btnUserRefresh1.setFocusable(false);
        btnUserRefresh1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRefresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRefresh1ActionPerformed(evt);
            }
        });
        toolBarUser1.add(btnUserRefresh1);

        btnUserPrev1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-rewind-button-round-24.png"))); // NOI18N
        btnUserPrev1.setFocusable(false);
        btnUserPrev1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserPrev1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserPrev1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserPrev1ActionPerformed(evt);
            }
        });
        toolBarUser1.add(btnUserPrev1);

        lbPageIndex1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndex1.setText("page");
        lbPageIndex1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndex1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser1.add(lbPageIndex1);

        btnUserNext1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-fast-forward-round-24.png"))); // NOI18N
        btnUserNext1.setFocusable(false);
        btnUserNext1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserNext1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserNext1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserNext1ActionPerformed(evt);
            }
        });
        toolBarUser1.add(btnUserNext1);

        txtSearchTableUser1.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableUser1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableUser1.setText("nhập tên đăng nhập, email, số điện thoại ...");
        txtSearchTableUser1.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableUser1.setColumns(40);
        txtSearchTableUser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableUser1MouseClicked(evt);
            }
        });
        txtSearchTableUser1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableUser1KeyPressed(evt);
            }
        });
        toolBarUser1.add(txtSearchTableUser1);

        btnTableUserSearch1.setText("Tìm kiếm");
        btnTableUserSearch1.setFocusable(false);
        btnTableUserSearch1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTableUserSearch1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTableUserSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableUserSearch1ActionPerformed(evt);
            }
        });
        toolBarUser1.add(btnTableUserSearch1);

        tableUser1.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableUser1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableUser1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableUser1.setGridColor(java.awt.SystemColor.control);
        tableUser1.setRowHeight(30);
        tableUser1.setRowMargin(2);
        tableUser1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(tableUser1);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(toolBarUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabApprove.addTab("Tất cả", jPanel5);

        toolBarUser2.setBorder(null);
        toolBarUser2.setRollover(true);

        btnUserAdd2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/add.png"))); // NOI18N
        btnUserAdd2.setText("Thêm");
        buttonGroup1.add(btnUserAdd2);
        btnUserAdd2.setFocusable(false);
        btnUserAdd2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserAdd2ActionPerformed(evt);
            }
        });
        toolBarUser2.add(btnUserAdd2);

        btnUserEdit2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/edit.png"))); // NOI18N
        btnUserEdit2.setText("Sửa");
        btnUserEdit2.setFocusable(false);
        btnUserEdit2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserEdit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserEdit2ActionPerformed(evt);
            }
        });
        toolBarUser2.add(btnUserEdit2);

        btnUserRemove2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/remove.png"))); // NOI18N
        btnUserRemove2.setText("Xoá");
        btnUserRemove2.setFocusable(false);
        btnUserRemove2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRemove2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRemove2ActionPerformed(evt);
            }
        });
        toolBarUser2.add(btnUserRemove2);

        btnUserRefresh2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-refresh-24.png"))); // NOI18N
        btnUserRefresh2.setText("Làm mới");
        btnUserRefresh2.setFocusable(false);
        btnUserRefresh2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRefresh2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRefresh2ActionPerformed(evt);
            }
        });
        toolBarUser2.add(btnUserRefresh2);

        btnUserPrev2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-rewind-button-round-24.png"))); // NOI18N
        btnUserPrev2.setFocusable(false);
        btnUserPrev2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserPrev2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserPrev2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserPrev2ActionPerformed(evt);
            }
        });
        toolBarUser2.add(btnUserPrev2);

        lbPageIndex2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndex2.setText("page");
        lbPageIndex2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndex2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser2.add(lbPageIndex2);

        btnUserNext2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-fast-forward-round-24.png"))); // NOI18N
        btnUserNext2.setFocusable(false);
        btnUserNext2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserNext2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserNext2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserNext2ActionPerformed(evt);
            }
        });
        toolBarUser2.add(btnUserNext2);

        txtSearchTableUser2.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableUser2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableUser2.setText("nhập tên đăng nhập, email, số điện thoại ...");
        txtSearchTableUser2.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableUser.setColumns(40);
        txtSearchTableUser2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableUser2MouseClicked(evt);
            }
        });
        txtSearchTableUser2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableUser2KeyPressed(evt);
            }
        });
        toolBarUser2.add(txtSearchTableUser2);

        btnTableUserSearch2.setText("Tìm kiếm");
        btnTableUserSearch2.setFocusable(false);
        btnTableUserSearch2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTableUserSearch2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTableUserSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableUserSearch2ActionPerformed(evt);
            }
        });
        toolBarUser2.add(btnTableUserSearch2);

        tableUser2.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableUser2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableUser2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableUser2.setGridColor(java.awt.SystemColor.control);
        tableUser2.setRowHeight(30);
        tableUser2.setRowMargin(2);
        tableUser2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(tableUser2);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(toolBarUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabApprove.addTab("Đang chờ duyệt", jPanel6);

        toolBarUser3.setBorder(null);
        toolBarUser3.setRollover(true);

        btnUserAdd3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/add.png"))); // NOI18N
        btnUserAdd3.setText("Thêm");
        buttonGroup1.add(btnUserAdd3);
        btnUserAdd3.setFocusable(false);
        btnUserAdd3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserAdd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserAdd3ActionPerformed(evt);
            }
        });
        toolBarUser3.add(btnUserAdd3);

        btnUserEdit3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/edit.png"))); // NOI18N
        btnUserEdit3.setText("Sửa");
        btnUserEdit3.setFocusable(false);
        btnUserEdit3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserEdit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserEdit3ActionPerformed(evt);
            }
        });
        toolBarUser3.add(btnUserEdit3);

        btnUserRemove3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/remove.png"))); // NOI18N
        btnUserRemove3.setText("Xoá");
        btnUserRemove3.setFocusable(false);
        btnUserRemove3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRemove3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRemove3ActionPerformed(evt);
            }
        });
        toolBarUser3.add(btnUserRemove3);

        btnUserRefresh3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-refresh-24.png"))); // NOI18N
        btnUserRefresh3.setText("Làm mới");
        btnUserRefresh3.setFocusable(false);
        btnUserRefresh3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRefresh3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRefresh3ActionPerformed(evt);
            }
        });
        toolBarUser3.add(btnUserRefresh3);

        btnUserPrev3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-rewind-button-round-24.png"))); // NOI18N
        btnUserPrev3.setFocusable(false);
        btnUserPrev3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserPrev3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserPrev3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserPrev3ActionPerformed(evt);
            }
        });
        toolBarUser3.add(btnUserPrev3);

        lbPageIndex3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndex3.setText("page");
        lbPageIndex3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndex3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser3.add(lbPageIndex3);

        btnUserNext3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-fast-forward-round-24.png"))); // NOI18N
        btnUserNext3.setFocusable(false);
        btnUserNext3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserNext3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserNext3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserNext3ActionPerformed(evt);
            }
        });
        toolBarUser3.add(btnUserNext3);

        txtSearchTableUser3.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableUser3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableUser3.setText("nhập tên đăng nhập, email, số điện thoại ...");
        txtSearchTableUser3.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableUser.setColumns(40);
        txtSearchTableUser3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableUser3MouseClicked(evt);
            }
        });
        txtSearchTableUser3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableUser3KeyPressed(evt);
            }
        });
        toolBarUser3.add(txtSearchTableUser3);

        btnTableUserSearch3.setText("Tìm kiếm");
        btnTableUserSearch3.setFocusable(false);
        btnTableUserSearch3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTableUserSearch3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTableUserSearch3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableUserSearch3ActionPerformed(evt);
            }
        });
        toolBarUser3.add(btnTableUserSearch3);

        tableUser3.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableUser3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableUser3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableUser3.setGridColor(java.awt.SystemColor.control);
        tableUser3.setRowHeight(30);
        tableUser3.setRowMargin(2);
        tableUser3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(tableUser3);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(toolBarUser3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        toolBarUser6.setBorder(null);
        toolBarUser6.setRollover(true);

        btnBrandAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/add.png"))); // NOI18N
        btnBrandAdd.setText("Thêm");
        buttonGroup1.add(btnBrandAdd);
        btnBrandAdd.setFocusable(false);
        btnBrandAdd.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBrandAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrandAddActionPerformed(evt);
            }
        });
        toolBarUser6.add(btnBrandAdd);

        btnBrandEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/edit.png"))); // NOI18N
        btnBrandEdit.setText("Sửa");
        btnBrandEdit.setFocusable(false);
        btnBrandEdit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBrandEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrandEditActionPerformed(evt);
            }
        });
        toolBarUser6.add(btnBrandEdit);

        btnBrandRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/remove.png"))); // NOI18N
        btnBrandRemove.setText("Xoá");
        btnBrandRemove.setFocusable(false);
        btnBrandRemove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBrandRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrandRemoveActionPerformed(evt);
            }
        });
        toolBarUser6.add(btnBrandRemove);

        btnBrandRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-refresh-24.png"))); // NOI18N
        btnBrandRefresh.setText("Làm mới");
        btnBrandRefresh.setFocusable(false);
        btnBrandRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBrandRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrandRefreshActionPerformed(evt);
            }
        });
        toolBarUser6.add(btnBrandRefresh);

        btnBrandPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-rewind-button-round-24.png"))); // NOI18N
        btnBrandPrev.setFocusable(false);
        btnBrandPrev.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBrandPrev.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBrandPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrandPrevActionPerformed(evt);
            }
        });
        toolBarUser6.add(btnBrandPrev);

        lbBrandPageIndex.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbBrandPageIndex.setText("page");
        lbBrandPageIndex.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbBrandPageIndex.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser6.add(lbBrandPageIndex);

        btnBrandNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-fast-forward-round-24.png"))); // NOI18N
        btnBrandNext.setFocusable(false);
        btnBrandNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBrandNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBrandNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrandNextActionPerformed(evt);
            }
        });
        toolBarUser6.add(btnBrandNext);

        txtSearchTableUser6.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableUser6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableUser6.setText("nhập tên, code.");
        txtSearchTableUser6.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableUser6.setColumns(40);
        txtSearchTableUser6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableUser6MouseClicked(evt);
            }
        });
        txtSearchTableUser6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableUser6KeyPressed(evt);
            }
        });
        toolBarUser6.add(txtSearchTableUser6);

        btnTableUserSearch6.setText("Tìm kiếm");
        btnTableUserSearch6.setFocusable(false);
        btnTableUserSearch6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTableUserSearch6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTableUserSearch6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableUserSearch6ActionPerformed(evt);
            }
        });
        toolBarUser6.add(btnTableUserSearch6);

        tableBrand.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableBrand.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableBrand.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableBrand.setGridColor(java.awt.SystemColor.control);
        tableBrand.setRowHeight(30);
        tableBrand.setRowMargin(2);
        tableBrand.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane8.setViewportView(tableBrand);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(toolBarUser6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabDetail.addTab("Thương Hiệu", jPanel3);

        toolBarUser9.setBorder(null);
        toolBarUser9.setRollover(true);

        btnDetailTypeAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/add.png"))); // NOI18N
        btnDetailTypeAdd.setText("Thêm");
        buttonGroup1.add(btnDetailTypeAdd);
        btnDetailTypeAdd.setFocusable(false);
        btnDetailTypeAdd.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDetailTypeAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailTypeAddActionPerformed(evt);
            }
        });
        toolBarUser9.add(btnDetailTypeAdd);

        btnDetailTypeEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/edit.png"))); // NOI18N
        btnDetailTypeEdit.setText("Sửa");
        btnDetailTypeEdit.setFocusable(false);
        btnDetailTypeEdit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDetailTypeEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailTypeEditActionPerformed(evt);
            }
        });
        toolBarUser9.add(btnDetailTypeEdit);

        btnDetailTypeRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/remove.png"))); // NOI18N
        btnDetailTypeRemove.setText("Xoá");
        btnDetailTypeRemove.setFocusable(false);
        btnDetailTypeRemove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDetailTypeRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailTypeRemoveActionPerformed(evt);
            }
        });
        toolBarUser9.add(btnDetailTypeRemove);

        btnDetailTypeRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-refresh-24.png"))); // NOI18N
        btnDetailTypeRefresh.setText("Làm mới");
        btnDetailTypeRefresh.setFocusable(false);
        btnDetailTypeRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDetailTypeRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailTypeRefreshActionPerformed(evt);
            }
        });
        toolBarUser9.add(btnDetailTypeRefresh);

        btnDetailTypePrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-rewind-button-round-24.png"))); // NOI18N
        btnDetailTypePrev.setFocusable(false);
        btnDetailTypePrev.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDetailTypePrev.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDetailTypePrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailTypePrevActionPerformed(evt);
            }
        });
        toolBarUser9.add(btnDetailTypePrev);

        lbDetailTypePageIndex.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDetailTypePageIndex.setText("page");
        lbDetailTypePageIndex.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbDetailTypePageIndex.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser9.add(lbDetailTypePageIndex);

        btnDetailTypeNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-fast-forward-round-24.png"))); // NOI18N
        btnDetailTypeNext.setFocusable(false);
        btnDetailTypeNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDetailTypeNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDetailTypeNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailTypeNextActionPerformed(evt);
            }
        });
        toolBarUser9.add(btnDetailTypeNext);

        txtSearchTableUser9.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableUser9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableUser9.setText("nhập tên, code.");
        txtSearchTableUser9.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableUser9.setColumns(40);
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

        tableDetailType.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableDetailType.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableDetailType.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableDetailType.setGridColor(java.awt.SystemColor.control);
        tableDetailType.setRowHeight(30);
        tableDetailType.setRowMargin(2);
        tableDetailType.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane11.setViewportView(tableDetailType);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(toolBarUser9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Loại đặc điểm", jPanel18);

        toolBarUser10.setBorder(null);
        toolBarUser10.setRollover(true);

        btnDetailAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/add.png"))); // NOI18N
        btnDetailAdd.setText("Thêm");
        buttonGroup1.add(btnDetailAdd);
        btnDetailAdd.setFocusable(false);
        btnDetailAdd.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDetailAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailAddActionPerformed(evt);
            }
        });
        toolBarUser10.add(btnDetailAdd);

        btnDetailEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/edit.png"))); // NOI18N
        btnDetailEdit.setText("Sửa");
        btnDetailEdit.setFocusable(false);
        btnDetailEdit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDetailEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailEditActionPerformed(evt);
            }
        });
        toolBarUser10.add(btnDetailEdit);

        btnDetailRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/remove.png"))); // NOI18N
        btnDetailRemove.setText("Xoá");
        btnDetailRemove.setFocusable(false);
        btnDetailRemove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDetailRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailRemoveActionPerformed(evt);
            }
        });
        toolBarUser10.add(btnDetailRemove);

        btnDetailRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-refresh-24.png"))); // NOI18N
        btnDetailRefresh.setText("Làm mới");
        btnDetailRefresh.setFocusable(false);
        btnDetailRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDetailRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailRefreshActionPerformed(evt);
            }
        });
        toolBarUser10.add(btnDetailRefresh);

        btnDetailPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-rewind-button-round-24.png"))); // NOI18N
        btnDetailPrev.setFocusable(false);
        btnDetailPrev.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDetailPrev.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDetailPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailPrevActionPerformed(evt);
            }
        });
        toolBarUser10.add(btnDetailPrev);

        lbDetailPageIndex.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDetailPageIndex.setText("page");
        lbDetailPageIndex.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbDetailPageIndex.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser10.add(lbDetailPageIndex);

        btnDetailNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-fast-forward-round-24.png"))); // NOI18N
        btnDetailNext.setFocusable(false);
        btnDetailNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDetailNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDetailNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailNextActionPerformed(evt);
            }
        });
        toolBarUser10.add(btnDetailNext);

        txtSearchTableUser10.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableUser10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableUser10.setText("nhập tên, code.");
        txtSearchTableUser10.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableUser10.setColumns(40);
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

        tableDetail.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableDetail.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableDetail.setGridColor(java.awt.SystemColor.control);
        tableDetail.setRowHeight(30);
        tableDetail.setRowMargin(2);
        tableDetail.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane12.setViewportView(tableDetail);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(toolBarUser10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Đặc điểm", jPanel19);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        tabDetail.addTab("Đặc Điểm, Tính Năng", jPanel4);

        toolBarUser8.setBorder(null);
        toolBarUser8.setRollover(true);

        btnLogRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/remove.png"))); // NOI18N
        btnLogRemove.setText("Xoá");
        btnLogRemove.setFocusable(false);
        btnLogRemove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLogRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogRemoveActionPerformed(evt);
            }
        });
        toolBarUser8.add(btnLogRemove);

        btnLogRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-refresh-24.png"))); // NOI18N
        btnLogRefresh.setText("Làm mới");
        btnLogRefresh.setFocusable(false);
        btnLogRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLogRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogRefreshActionPerformed(evt);
            }
        });
        toolBarUser8.add(btnLogRefresh);

        tableLog.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableLog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableLog.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableLog.setGridColor(java.awt.SystemColor.control);
        tableLog.setRowHeight(30);
        tableLog.setRowMargin(2);
        tableLog.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane10.setViewportView(tableLog);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(toolBarUser8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-settings-24.png"))); // NOI18N
        jMenu1.setText("Cài đặt");

        jMenuItem1.setText("Thông tin cá nhân");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Đổi mật khẩu");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setText("Đăng xuất");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
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

    private void btnUserAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserAdd1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserAdd1ActionPerformed

    private void btnUserEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserEdit1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserEdit1ActionPerformed

    private void btnUserRemove1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRemove1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRemove1ActionPerformed

    private void btnUserRefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRefresh1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRefresh1ActionPerformed

    private void btnUserPrev1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserPrev1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserPrev1ActionPerformed

    private void btnUserNext1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserNext1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserNext1ActionPerformed

    private void txtSearchTableUser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUser1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser1MouseClicked

    private void txtSearchTableUser1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUser1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser1KeyPressed

    private void btnTableUserSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTableUserSearch1ActionPerformed

    private void btnUserAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserAdd2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserAdd2ActionPerformed

    private void btnUserEdit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserEdit2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserEdit2ActionPerformed

    private void btnUserRemove2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRemove2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRemove2ActionPerformed

    private void btnUserRefresh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRefresh2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRefresh2ActionPerformed

    private void btnUserPrev2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserPrev2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserPrev2ActionPerformed

    private void btnUserNext2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserNext2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserNext2ActionPerformed

    private void txtSearchTableUser2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUser2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser2MouseClicked

    private void txtSearchTableUser2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUser2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser2KeyPressed

    private void btnTableUserSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTableUserSearch2ActionPerformed

    private void btnUserAdd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserAdd3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserAdd3ActionPerformed

    private void btnUserEdit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserEdit3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserEdit3ActionPerformed

    private void btnUserRemove3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRemove3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRemove3ActionPerformed

    private void btnUserRefresh3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRefresh3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserRefresh3ActionPerformed

    private void btnUserPrev3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserPrev3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserPrev3ActionPerformed

    private void btnUserNext3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserNext3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserNext3ActionPerformed

    private void txtSearchTableUser3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUser3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser3MouseClicked

    private void txtSearchTableUser3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUser3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser3KeyPressed

    private void btnTableUserSearch3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTableUserSearch3ActionPerformed

    private void btnBrandAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrandAddActionPerformed
        new ptit.webservice.UI.Brand.add().run();
    }//GEN-LAST:event_btnBrandAddActionPerformed

    private void btnBrandEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrandEditActionPerformed
        int row = tableBrand.getSelectedRow();
        try {
            String id = (String) tableBrand.getModel().getValueAt(row, 0);
            String name = (String) tableBrand.getModel().getValueAt(row, 1);
            String code = (String) tableBrand.getModel().getValueAt(row, 2);
            Brand brand = new Brand();
            brand.setId(Integer.parseInt(id.trim()));
            brand.setName(name);
            brand.setCode(code);
            new ptit.webservice.UI.Brand.edit().run(brand);
        } catch (Exception e) {

        }
    }//GEN-LAST:event_btnBrandEditActionPerformed

    private void btnBrandRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrandRemoveActionPerformed
        int row = tableBrand.getSelectedRow();
        try {
            String id = (String) tableBrand.getModel().getValueAt(row, 0);
            int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xoá brand id= " + id + "", "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
            if (result == JOptionPane.OK_OPTION) {
                deleteBrand(Integer.parseInt(id.trim()));
            }
        } catch (HeadlessException | IOException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng cần xoá", "Thông báo", TrayIcon.MessageType.INFO.ordinal(), null);
        }
    }//GEN-LAST:event_btnBrandRemoveActionPerformed

    private void deleteBrand(int id) throws ProtocolException, IOException {
        // call API
        String url = "/Brands";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("id", String.valueOf(id));
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

    private void deleteDetailType(int id) throws ProtocolException, IOException {
        // call API
        String url = "/DetailTypes";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("id", String.valueOf(id));
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

    private void deleteDetail(int id) throws ProtocolException, IOException {
        // call API
        String url = "/Detail";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("id", String.valueOf(id));
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

    private void deleteLog(String ids) throws ProtocolException, IOException {
        // call API
        String url = "/AppLogs";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("ids", ids);
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
    
    private void btnBrandRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrandRefreshActionPerformed
        new Thread(() -> {
            getBrands(true);
        }).start();

    }//GEN-LAST:event_btnBrandRefreshActionPerformed

    private void btnBrandPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrandPrevActionPerformed
        int temp = pageIndexBrand - 1;
        if ((temp + 1) > 1) {
            pageIndexBrand--;
            new Thread(() -> {
                getBrands(false);
            }).start();
        } else {
            pageIndexBrand = 1;
        }
    }//GEN-LAST:event_btnBrandPrevActionPerformed

    private void btnBrandNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrandNextActionPerformed
        int temp = pageIndexBrand + 1;
        if (temp <= pageIndexBrand) {
            pageIndexBrand++;
            new Thread(() -> {
                getBrands(false);
            }).start();
        } else {
            pageIndexBrand = totalPageBrand;
        }
    }//GEN-LAST:event_btnBrandNextActionPerformed

    private void txtSearchTableUser6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUser6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser6MouseClicked

    private void txtSearchTableUser6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUser6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser6KeyPressed

    private void btnTableUserSearch6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTableUserSearch6ActionPerformed

    private void btnLogRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogRemoveActionPerformed
        try {
            int[] selects = tableLog.getSelectedRows();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < selects.length; i++) {
                sb.append((String) tableLog.getModel().getValueAt(selects[i], 0)).append(",");
            }
            String ids = sb.toString().substring(0, sb.toString().length() - 1);
            deleteLog(ids);
        } catch (IOException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLogRemoveActionPerformed

    private void btnLogRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogRefreshActionPerformed
       new Thread(() -> { getLogs();} ).start();
    }//GEN-LAST:event_btnLogRefreshActionPerformed

    private void btnTableUserSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearchActionPerformed
        String text = txtSearchTableUser.getText();
        searchTableContents(text);
    }//GEN-LAST:event_btnTableUserSearchActionPerformed

    private void txtSearchTableUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUserKeyPressed
        if (txtSearchTableUser.getForeground() != Color.BLACK) {
            txtSearchTableUser.setForeground(Color.BLACK);
            txtSearchTableUser.setText("");
            txtSearchTableUser.setText(String.valueOf(evt.getKeyChar()));
        }
    }//GEN-LAST:event_txtSearchTableUserKeyPressed

    private void txtSearchTableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUserMouseClicked
        if (txtSearchTableUser.getForeground() != Color.BLACK) {
            txtSearchTableUser.setForeground(Color.BLACK);
            txtSearchTableUser.setText("");
        }
    }//GEN-LAST:event_txtSearchTableUserMouseClicked

    private void btnUserNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserNextActionPerformed
        int temp = pageIndexUser + 1;
        if (temp <= totalPageUser) {
            pageIndexUser++;
            new Thread(() -> {
                getUsers(false);
            }).start();
        } else {
            pageIndexUser = totalPageUser;
        }

    }//GEN-LAST:event_btnUserNextActionPerformed

    private void btnUserPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserPrevActionPerformed
        int temp = pageIndexUser - 1;
        if ((temp + 1) > 1) {
            pageIndexUser--;
            new Thread(() -> {
                getUsers(false);
            }).start();
        } else {
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

    private void btnUserAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserAddActionPerformed
        add.run();
    }//GEN-LAST:event_btnUserAddActionPerformed

    private void btnDetailTypeAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailTypeAddActionPerformed
        new ptit.webservice.UI.DetailType.add().run();
    }//GEN-LAST:event_btnDetailTypeAddActionPerformed

    private void btnDetailTypeEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailTypeEditActionPerformed
        int row = tableDetailType.getSelectedRow();
        try {
            String id = (String) tableDetailType.getModel().getValueAt(row, 0);
            String name = (String) tableDetailType.getModel().getValueAt(row, 1);
            String code = (String) tableDetailType.getModel().getValueAt(row, 2);
            String icon = (String) tableDetailType.getModel().getValueAt(row, 3);
            String description = (String) tableDetailType.getModel().getValueAt(row, 4);
            String parentId = (String) tableDetailType.getModel().getValueAt(row, 5);

            DetailType dt = new DetailType();
            dt.setId(Integer.parseInt(id.trim()));
            dt.setName(name);
            dt.setCode(code);
            dt.setIcon(icon);
            dt.setDescription(description);
            if (parentId != null) {
                dt.setParentId(Integer.parseInt(parentId.trim()));
            }
            new ptit.webservice.UI.DetailType.edit().run(dt);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDetailTypeEditActionPerformed

    private void btnDetailTypeRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailTypeRemoveActionPerformed
        int row = tableDetailType.getSelectedRow();
        try {
            String id = (String) tableDetailType.getModel().getValueAt(row, 0);
            int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xoá DetailType id: " + id + "", "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
            if (result == JOptionPane.OK_OPTION) {
                deleteDetailType(Integer.parseInt(id.trim()));
            }
        } catch (HeadlessException | IOException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng cần xoá", "Thông báo", TrayIcon.MessageType.INFO.ordinal(), null);
        }
    }//GEN-LAST:event_btnDetailTypeRemoveActionPerformed

    private void btnDetailTypeRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailTypeRefreshActionPerformed
        new Thread(() -> {
            getDetailTypes(true);
        }).start();
    }//GEN-LAST:event_btnDetailTypeRefreshActionPerformed

    private void btnDetailTypePrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailTypePrevActionPerformed
        int temp = pageIndexDetailType - 1;
        if ((temp + 1) > 1) {
            pageIndexDetailType--;
            new Thread(() -> {
                getDetailTypes(false);
            }).start();
        } else {
            pageIndexDetailType = 1;
        }
    }//GEN-LAST:event_btnDetailTypePrevActionPerformed

    private void btnDetailTypeNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailTypeNextActionPerformed
        int temp = pageIndexDetailType + 1;
        if (temp <= totalPageDetailType) {
            pageIndexDetailType++;
            new Thread(() -> {
                getDetailTypes(false);
            }).start();
        } else {
            pageIndexDetailType = totalPageDetailType;
        }
    }//GEN-LAST:event_btnDetailTypeNextActionPerformed

    private void txtSearchTableUser9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUser9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser9MouseClicked

    private void txtSearchTableUser9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUser9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser9KeyPressed

    private void btnTableUserSearch9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTableUserSearch9ActionPerformed

    private void btnDetailAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailAddActionPerformed
        new ptit.webservice.UI.Detail.add().run();
    }//GEN-LAST:event_btnDetailAddActionPerformed

    private void btnDetailEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailEditActionPerformed
        int row = tableDetail.getSelectedRow();
        try {
            String id = (String) tableDetail.getModel().getValueAt(row, 0);
            String val = (String) tableDetail.getModel().getValueAt(row, 1);
            String detailTypeId = (String) tableDetail.getModel().getValueAt(row, 2);
            String detailTypeName = (String) tableDetail.getModel().getValueAt(row, 3);
            String detailTypeCode = (String) tableDetail.getModel().getValueAt(row, 4);
            Detail dt = new Detail();
            dt.setId(Integer.parseInt(id.trim()));
            dt.setVal(val);
            dt.setDetailType(new DetailType(Integer.parseInt(detailTypeId.trim()), detailTypeName, detailTypeCode));

//            DetailType dt = new DetailType();
//            dt.setId(Integer.parseInt(id.trim()));
//            dt.setName(name);
//            dt.setCode(code);
//            dt.setIcon(icon);
//            dt.setDescription(description);
//            if (parentId != null) {
//                dt.setParentId(Integer.parseInt(parentId.trim()));
//            }
            new ptit.webservice.UI.Detail.edit().run(dt);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDetailEditActionPerformed

    private void btnDetailRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailRemoveActionPerformed
        int row = tableDetail.getSelectedRow();
        try {
            String id = (String) tableDetail.getModel().getValueAt(row, 0);
            int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xoá Detail id: " + id + "", "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
            if (result == JOptionPane.OK_OPTION) {
                deleteDetail(Integer.parseInt(id.trim()));
            }
        } catch (HeadlessException | IOException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng cần xoá", "Thông báo", TrayIcon.MessageType.INFO.ordinal(), null);
        }
    }//GEN-LAST:event_btnDetailRemoveActionPerformed

    private void btnDetailRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailRefreshActionPerformed
        new Thread(() -> {
            getDetail(true);
        }).start();
    }//GEN-LAST:event_btnDetailRefreshActionPerformed

    private void btnDetailPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailPrevActionPerformed
        int temp = pageIndexDetail - 1;
        if ((temp + 1) > 1) {
            pageIndexDetail--;
            new Thread(() -> {
                getDetail(false);
            }).start();
        } else {
            pageIndexDetail = 1;
        }
    }//GEN-LAST:event_btnDetailPrevActionPerformed

    private void btnDetailNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailNextActionPerformed
        int temp = pageIndexDetail + 1;
        if (temp <= totalPageDetail) {
            pageIndexDetail++;
            new Thread(() -> {
                getDetail(false);
            }).start();
        } else {
            pageIndexDetail = totalPageDetail;
        }
    }//GEN-LAST:event_btnDetailNextActionPerformed

    private void txtSearchTableUser10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableUser10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser10MouseClicked

    private void txtSearchTableUser10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableUser10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTableUser10KeyPressed

    private void btnTableUserSearch10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTableUserSearch10ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new ptit.webservice.UI.AppUser.edit().run(Program.identities);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
         new ptit.webservice.UI.AppUser.ChangePassword().run(Program.identities.getUsername());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
       this.dispose();
       Program.Token = "";
       Program.identities = null;
       new Login().run();
    }//GEN-LAST:event_jMenuItem4ActionPerformed
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
        new Thread(() -> {
            getBrands(false);
        }).start();

        new Thread(() -> {
            getDetailTypes(false);
        }).start();
        new Thread(() -> {
            getDetail(false);
        }).start();

        new Thread(() -> {
            getLogs();
        }).start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrandAdd;
    private javax.swing.JButton btnBrandEdit;
    private javax.swing.JButton btnBrandNext;
    private javax.swing.JButton btnBrandPrev;
    private javax.swing.JButton btnBrandRefresh;
    private javax.swing.JButton btnBrandRemove;
    private javax.swing.JButton btnDetailAdd;
    private javax.swing.JButton btnDetailEdit;
    private javax.swing.JButton btnDetailNext;
    private javax.swing.JButton btnDetailPrev;
    private javax.swing.JButton btnDetailRefresh;
    private javax.swing.JButton btnDetailRemove;
    private javax.swing.JButton btnDetailTypeAdd;
    private javax.swing.JButton btnDetailTypeEdit;
    private javax.swing.JButton btnDetailTypeNext;
    private javax.swing.JButton btnDetailTypePrev;
    private javax.swing.JButton btnDetailTypeRefresh;
    private javax.swing.JButton btnDetailTypeRemove;
    private javax.swing.JButton btnLogRefresh;
    private javax.swing.JButton btnLogRemove;
    private javax.swing.JButton btnTableUserSearch;
    private javax.swing.JButton btnTableUserSearch1;
    private javax.swing.JButton btnTableUserSearch10;
    private javax.swing.JButton btnTableUserSearch2;
    private javax.swing.JButton btnTableUserSearch3;
    private javax.swing.JButton btnTableUserSearch6;
    private javax.swing.JButton btnTableUserSearch9;
    private javax.swing.JButton btnUserAdd;
    private javax.swing.JButton btnUserAdd1;
    private javax.swing.JButton btnUserAdd2;
    private javax.swing.JButton btnUserAdd3;
    private javax.swing.JButton btnUserEdit;
    private javax.swing.JButton btnUserEdit1;
    private javax.swing.JButton btnUserEdit2;
    private javax.swing.JButton btnUserEdit3;
    private javax.swing.JButton btnUserNext;
    private javax.swing.JButton btnUserNext1;
    private javax.swing.JButton btnUserNext2;
    private javax.swing.JButton btnUserNext3;
    private javax.swing.JButton btnUserPrev;
    private javax.swing.JButton btnUserPrev1;
    private javax.swing.JButton btnUserPrev2;
    private javax.swing.JButton btnUserPrev3;
    private javax.swing.JButton btnUserRefresh;
    private javax.swing.JButton btnUserRefresh1;
    private javax.swing.JButton btnUserRefresh2;
    private javax.swing.JButton btnUserRefresh3;
    private javax.swing.JButton btnUserRemove;
    private javax.swing.JButton btnUserRemove1;
    private javax.swing.JButton btnUserRemove2;
    private javax.swing.JButton btnUserRemove3;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lbBrandPageIndex;
    private javax.swing.JLabel lbDetailPageIndex;
    private javax.swing.JLabel lbDetailTypePageIndex;
    private javax.swing.JLabel lbPageIndex;
    private javax.swing.JLabel lbPageIndex1;
    private javax.swing.JLabel lbPageIndex2;
    private javax.swing.JLabel lbPageIndex3;
    private javax.swing.JTabbedPane tabApprove;
    private javax.swing.JTabbedPane tabDetail;
    private javax.swing.JTable tableBrand;
    private javax.swing.JTable tableDetail;
    private javax.swing.JTable tableDetailType;
    private javax.swing.JTable tableLog;
    private javax.swing.JTable tableUser;
    private javax.swing.JTable tableUser1;
    private javax.swing.JTable tableUser2;
    private javax.swing.JTable tableUser3;
    private javax.swing.JToolBar toolBarUser;
    private javax.swing.JToolBar toolBarUser1;
    private javax.swing.JToolBar toolBarUser10;
    private javax.swing.JToolBar toolBarUser2;
    private javax.swing.JToolBar toolBarUser3;
    private javax.swing.JToolBar toolBarUser6;
    private javax.swing.JToolBar toolBarUser8;
    private javax.swing.JToolBar toolBarUser9;
    private javax.swing.JTextField txtSearchTableUser;
    private javax.swing.JTextField txtSearchTableUser1;
    private javax.swing.JTextField txtSearchTableUser10;
    private javax.swing.JTextField txtSearchTableUser2;
    private javax.swing.JTextField txtSearchTableUser3;
    private javax.swing.JTextField txtSearchTableUser6;
    private javax.swing.JTextField txtSearchTableUser9;
    // End of variables declaration//GEN-END:variables

}
