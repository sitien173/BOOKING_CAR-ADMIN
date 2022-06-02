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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import ptit.webservice.UI.AppUser.add;
import ptit.webservice.UI.AppUser.edit;
import ptit.webservice.main.Program;
import ptit.webservice.model.AppLog.AppLog;
import ptit.webservice.model.AppUser.AppUser;
import ptit.webservice.model.Brand.Brand;
import ptit.webservice.model.Car.Car;
import ptit.webservice.model.Car.CarPutStatusModel;
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

    private static int pageIndexAllCar = 1;
    private static final int limitAllCar = 20;
    private static int totalPageAllCar = 0;
    private static final Map<Integer, List<Car>> cacheAllCar = new HashMap<>();

    private static int pageIndexCarWaitConfirm = 1;
    private static final int limitCarWaitConfirm = 20;
    private static int totalPageCarWaitConfirm = 0;
    private static final Map<Integer, List<Car>> cacheCarWaitConfirm = new HashMap<>();

    private static int pageIndexCarApprove = 1;
    private static final int limitCarApprove = 20;
    private static int totalPageCarApprove = 0;
    private static final Map<Integer, List<Car>> cacheCarApprove = new HashMap<>();

    private static int pageIndexCarIgnoreApprove = 1;
    private static final int limitCarIgnoreApprove = 20;
    private static int totalPageCarIgnoreApprove = 0;
    private static final Map<Integer, List<Car>> cacheCarIgnoreApprove = new HashMap<>();

    private static int pageIndexCarDeleted = 1;
    private static final int limitCarDeleted = 20;
    private static int totalPageCarDeleted = 0;
    private static final Map<Integer, List<Car>> cacheCarDeleted = new HashMap<>();

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
                try {
                    vt.add(String.valueOf(appUsers.get(i).getId()));
                    vt.add(appUsers.get(i).getUsername());
                    vt.add(appUsers.get(i).getEmail());
                    vt.add(appUsers.get(i).getPhoneNumber());
                    vt.add(appUsers.get(i).getName());
                    vt.add(appUsers.get(i).getRole());
                } catch (Exception e) {
                }
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
                try {
                    vt.add(String.valueOf(detailTypes.get(i).getId()));
                    vt.add(detailTypes.get(i).getName());
                    vt.add(detailTypes.get(i).getCode());
                    vt.add(detailTypes.get(i).getIcon());
                    vt.add(detailTypes.get(i).getDescription());
                    if (detailTypes.get(i).getParent() != null) {
                        vt.add(String.valueOf(detailTypes.get(i).getParent().getId()));
                    }
                } catch (Exception e) {
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
                try {
                    vt.add(String.valueOf(details.get(i).getId()));
                    vt.add(String.valueOf(details.get(i).getVal()));
                    vt.add(String.valueOf(details.get(i).getDetailType().getId()));
                    vt.add(String.valueOf(details.get(i).getDetailType().getName()));
                    vt.add(String.valueOf(details.get(i).getDetailType().getCode()));
                } catch (Exception e) {
                }
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
                try {
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
                } catch (Exception e) {
                }
                model.addRow(vt);
            });
        } catch (ProtocolException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getAllCars(boolean isRefresh) {
        try {
            List<Car> cars = cacheAllCar.get(pageIndexAllCar);
            if (cars == null || isRefresh) {
                String url = "/Cars/cars";
                Map<String, String> params = new HashMap<>();
                params.put("pageIndex", String.valueOf(pageIndexAllCar));
                params.put("limit", String.valueOf(limitAllCar));

                Map<Program.HttpHeader, String> headers = new HashMap<>();
                headers.put(Program.HttpHeader.Authorization, Program.PrefixToken + Program.Token);
                headers.put(Program.HttpHeader.ContentType, "application/json; charset=utf-8");

                String jsonData = Program.SendHttpGet(url, params, headers);

                java.lang.reflect.Type type = new TypeToken<ResponseModel<PagingModel<Car>>>() {
                }.getType();
                ResponseModel<PagingModel<Car>> response = new Gson().fromJson(jsonData, type);
                if (!response.isSuccess()) {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
                }
                cars = response.getData().getItems();
                totalPageAllCar = response.getData().getTotalPage();
                cacheAllCar.put(pageIndexAllCar, cars);
            }

            DefaultTableModel model = (DefaultTableModel) tableAllCar.getModel();
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
            model.addColumn("Description");
            model.addColumn("Price");
            model.addColumn("TotalBooking");
            model.addColumn("Status");
            model.addColumn("Address");
            model.addColumn("Brand");
            model.addColumn("OwnerCar");

            // model.addColumn("Avatar");
            int size = cars.size();
            for (int i = 0; i < size; i++) {
                Vector vt = new Vector<>();
                try {
                    vt.add(String.valueOf(cars.get(i).getId()));
                    vt.add(cars.get(i).getName());
                    vt.add(cars.get(i).getDescription());
                    vt.add(String.valueOf(cars.get(i).getPrice()));
                    vt.add(String.valueOf(cars.get(i).getTotalBooking()));
                    vt.add(String.valueOf(cars.get(i).getStatus()));
                    vt.add(String.valueOf(cars.get(i).getAddress()));
                    vt.add(cars.get(i).getBrand().getName());
                    vt.add(cars.get(i).getOwnerCar().getUsername());
                } catch (Exception e) {
                }
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

    private void getCarsWaitConfirm(boolean isRefresh) {
        try {
            List<Car> cars = cacheCarWaitConfirm.get(pageIndexCarWaitConfirm);
            if (cars == null || isRefresh) {
                String url = "/Cars/cars-by-status";
                Map<String, String> params = new HashMap<>();
                params.put("pageIndex", String.valueOf(pageIndexCarWaitConfirm));
                params.put("limit", String.valueOf(limitCarWaitConfirm));
                params.put("status", "0");

                Map<Program.HttpHeader, String> headers = new HashMap<>();
                headers.put(Program.HttpHeader.Authorization, Program.PrefixToken + Program.Token);
                headers.put(Program.HttpHeader.ContentType, "application/json; charset=utf-8");

                String jsonData = Program.SendHttpGet(url, params, headers);

                java.lang.reflect.Type type = new TypeToken<ResponseModel<PagingModel<Car>>>() {
                }.getType();
                ResponseModel<PagingModel<Car>> response = new Gson().fromJson(jsonData, type);
                if (!response.isSuccess()) {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
                }
                cars = response.getData().getItems();
                totalPageCarWaitConfirm = response.getData().getTotalPage();
                cacheCarWaitConfirm.put(pageIndexCarWaitConfirm, cars);
            }

            DefaultTableModel model = (DefaultTableModel) tableCarWaitConfirm.getModel();
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
            model.addColumn("Description");
            model.addColumn("Price");
            model.addColumn("TotalBooking");
            model.addColumn("Status");
            model.addColumn("Address");
            model.addColumn("Brand");
            model.addColumn("OwnerCar");

            // model.addColumn("Avatar");
            int size = cars.size();
            for (int i = 0; i < size; i++) {
                Vector vt = new Vector<>();
                try {
                    vt.add(String.valueOf(cars.get(i).getId()));
                    vt.add(cars.get(i).getName());
                    vt.add(cars.get(i).getDescription());
                    vt.add(String.valueOf(cars.get(i).getPrice()));
                    vt.add(String.valueOf(cars.get(i).getTotalBooking()));
                    vt.add(String.valueOf(cars.get(i).getStatus()));
                    vt.add(String.valueOf(cars.get(i).getAddress()));
                    vt.add(cars.get(i).getBrand().getName());
                    vt.add(cars.get(i).getOwnerCar().getUsername());
                } catch (NullPointerException e) {

                }
                //vt.add(appUsers.get(i).getAvatar());
                model.addRow(vt);
            }
            lbPageIndexCarWaitConfirm.setText(String.valueOf(pageIndexCarWaitConfirm));

        } catch (ProtocolException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getCarsApprove(boolean isRefresh) {
        try {
            List<Car> cars = cacheCarApprove.get(pageIndexCarApprove);
            if (cars == null || isRefresh) {
                String url = "/Cars/cars-by-status";
                Map<String, String> params = new HashMap<>();
                params.put("pageIndex", String.valueOf(pageIndexCarApprove));
                params.put("limit", String.valueOf(limitCarApprove));
                params.put("status", "1");

                Map<Program.HttpHeader, String> headers = new HashMap<>();
                headers.put(Program.HttpHeader.Authorization, Program.PrefixToken + Program.Token);
                headers.put(Program.HttpHeader.ContentType, "application/json; charset=utf-8");

                String jsonData = Program.SendHttpGet(url, params, headers);

                java.lang.reflect.Type type = new TypeToken<ResponseModel<PagingModel<Car>>>() {
                }.getType();
                ResponseModel<PagingModel<Car>> response = new Gson().fromJson(jsonData, type);
                if (!response.isSuccess()) {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
                }
                cars = response.getData().getItems();
                totalPageCarApprove = response.getData().getTotalPage();
                cacheCarApprove.put(pageIndexCarApprove, cars);
            }

            DefaultTableModel model = (DefaultTableModel) tableCarApprove.getModel();
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
            model.addColumn("Description");
            model.addColumn("Price");
            model.addColumn("TotalBooking");
            model.addColumn("Status");
            model.addColumn("Address");
            model.addColumn("Brand");
            model.addColumn("OwnerCar");

            // model.addColumn("Avatar");
            int size = cars.size();
            for (int i = 0; i < size; i++) {
                Vector vt = new Vector<>();
                try {
                    vt.add(String.valueOf(cars.get(i).getId()));
                    vt.add(cars.get(i).getName());
                    vt.add(cars.get(i).getDescription());
                    vt.add(String.valueOf(cars.get(i).getPrice()));
                    vt.add(String.valueOf(cars.get(i).getTotalBooking()));
                    vt.add(String.valueOf(cars.get(i).getStatus()));
                    vt.add(String.valueOf(cars.get(i).getAddress()));
                    vt.add(cars.get(i).getBrand().getName());
                    vt.add(cars.get(i).getOwnerCar().getUsername());
                } catch (NullPointerException e) {

                }
                //vt.add(appUsers.get(i).getAvatar());
                model.addRow(vt);
            }
            lbPageIndexCarApprove.setText(String.valueOf(pageIndexCarApprove));

        } catch (ProtocolException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getCarsIgnoreApprove(boolean isRefresh) {
        try {
            List<Car> cars = cacheCarIgnoreApprove.get(pageIndexCarIgnoreApprove);
            if (cars == null || isRefresh) {
                String url = "/Cars/cars-by-status";
                Map<String, String> params = new HashMap<>();
                params.put("pageIndex", String.valueOf(pageIndexCarIgnoreApprove));
                params.put("limit", String.valueOf(limitCarIgnoreApprove));
                params.put("status", "-1");

                Map<Program.HttpHeader, String> headers = new HashMap<>();
                headers.put(Program.HttpHeader.Authorization, Program.PrefixToken + Program.Token);
                headers.put(Program.HttpHeader.ContentType, "application/json; charset=utf-8");

                String jsonData = Program.SendHttpGet(url, params, headers);

                java.lang.reflect.Type type = new TypeToken<ResponseModel<PagingModel<Car>>>() {
                }.getType();
                ResponseModel<PagingModel<Car>> response = new Gson().fromJson(jsonData, type);
                if (!response.isSuccess()) {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
                }
                cars = response.getData().getItems();
                totalPageCarIgnoreApprove = response.getData().getTotalPage();
                cacheCarIgnoreApprove.put(pageIndexCarIgnoreApprove, cars);
            }

            DefaultTableModel model = (DefaultTableModel) tableCarIgnoreApprove.getModel();
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
            model.addColumn("Description");
            model.addColumn("Price");
            model.addColumn("TotalBooking");
            model.addColumn("Status");
            model.addColumn("Address");
            model.addColumn("Brand");
            model.addColumn("OwnerCar");

            // model.addColumn("Avatar");
            int size = cars.size();
            for (int i = 0; i < size; i++) {
                Vector vt = new Vector<>();
                try {
                    vt.add(String.valueOf(cars.get(i).getId()));
                    vt.add(cars.get(i).getName());
                    vt.add(cars.get(i).getDescription());
                    vt.add(String.valueOf(cars.get(i).getPrice()));
                    vt.add(String.valueOf(cars.get(i).getTotalBooking()));
                    vt.add(String.valueOf(cars.get(i).getStatus()));
                    vt.add(String.valueOf(cars.get(i).getAddress()));
                    vt.add(cars.get(i).getBrand().getName());
                    vt.add(cars.get(i).getOwnerCar().getUsername());
                } catch (NullPointerException e) {

                }
                //vt.add(appUsers.get(i).getAvatar());
                model.addRow(vt);
            }
            lbPageIndexCarIgnoreApprove.setText(String.valueOf(pageIndexCarIgnoreApprove));

        } catch (ProtocolException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getCarsDeleted(boolean isRefresh) {
        try {
            List<Car> cars = cacheCarDeleted.get(pageIndexCarDeleted);
            if (cars == null || isRefresh) {
                String url = "/Cars/cars-by-status";
                Map<String, String> params = new HashMap<>();
                params.put("pageIndex", String.valueOf(pageIndexCarDeleted));
                params.put("limit", String.valueOf(limitCarDeleted));
                params.put("status", "2");

                Map<Program.HttpHeader, String> headers = new HashMap<>();
                headers.put(Program.HttpHeader.Authorization, Program.PrefixToken + Program.Token);
                headers.put(Program.HttpHeader.ContentType, "application/json; charset=utf-8");

                String jsonData = Program.SendHttpGet(url, params, headers);

                java.lang.reflect.Type type = new TypeToken<ResponseModel<PagingModel<Car>>>() {
                }.getType();
                ResponseModel<PagingModel<Car>> response = new Gson().fromJson(jsonData, type);
                if (!response.isSuccess()) {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
                }
                cars = response.getData().getItems();
                totalPageCarDeleted = response.getData().getTotalPage();
                cacheCarDeleted.put(pageIndexCarDeleted, cars);
            }

            DefaultTableModel model = (DefaultTableModel) tableCarDeleted.getModel();
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
            model.addColumn("Description");
            model.addColumn("Price");
            model.addColumn("TotalBooking");
            model.addColumn("Status");
            model.addColumn("Address");
            model.addColumn("Brand");
            model.addColumn("OwnerCar");

            // model.addColumn("Avatar");
            int size = cars.size();
            for (int i = 0; i < size; i++) {
                Vector vt = new Vector<>();
                try {
                    vt.add(String.valueOf(cars.get(i).getId()));
                    vt.add(cars.get(i).getName());
                    vt.add(cars.get(i).getDescription());
                    vt.add(String.valueOf(cars.get(i).getPrice()));
                    vt.add(String.valueOf(cars.get(i).getTotalBooking()));
                    vt.add(String.valueOf(cars.get(i).getStatus()));
                    vt.add(String.valueOf(cars.get(i).getAddress()));
                    vt.add(cars.get(i).getBrand().getName());
                    vt.add(cars.get(i).getOwnerCar().getUsername());
                } catch (NullPointerException e) {

                }
                //vt.add(appUsers.get(i).getAvatar());
                model.addRow(vt);
            }
            lbPageIndexCarDeleted.setText(String.valueOf(pageIndexCarDeleted));

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
        btnUserRemove1 = new javax.swing.JButton();
        btnUserRefresh1 = new javax.swing.JButton();
        btnUserPrev1 = new javax.swing.JButton();
        lbPageIndex1 = new javax.swing.JLabel();
        btnUserNext1 = new javax.swing.JButton();
        txtSearchTableAllCar = new javax.swing.JTextField();
        btnTableUserSearch1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableAllCar = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        toolBarUser2 = new javax.swing.JToolBar();
        cboxCarWaitConfirm = new javax.swing.JComboBox<>();
        btnCarWaitConfirmRefresh = new javax.swing.JButton();
        btnCarWaitConfirmPrev = new javax.swing.JButton();
        lbPageIndexCarWaitConfirm = new javax.swing.JLabel();
        btnCarWaitConfirmNext = new javax.swing.JButton();
        txtSearchTableCarWaitConfirm = new javax.swing.JTextField();
        btnTableUserSearch2 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableCarWaitConfirm = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        toolBarUser3 = new javax.swing.JToolBar();
        btnUserRemove3 = new javax.swing.JButton();
        btnUserRefresh3 = new javax.swing.JButton();
        btnCarApprovePrev = new javax.swing.JButton();
        lbPageIndexCarApprove = new javax.swing.JLabel();
        btnCarApproveNext = new javax.swing.JButton();
        txtSearchTableCarApprove = new javax.swing.JTextField();
        btnTableUserSearch3 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableCarApprove = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        toolBarUser4 = new javax.swing.JToolBar();
        cboxCarIgnoreApprove = new javax.swing.JComboBox<>();
        btnCarIgnoreApprove = new javax.swing.JButton();
        btnCarIgnoreApproveRefresh = new javax.swing.JButton();
        btnCarApprovePrev1 = new javax.swing.JButton();
        lbPageIndexCarIgnoreApprove = new javax.swing.JLabel();
        btnCarApproveNext1 = new javax.swing.JButton();
        txtSearchTableCarIgnoreApprove = new javax.swing.JTextField();
        btnTableUserSearch4 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableCarIgnoreApprove = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        toolBarUser5 = new javax.swing.JToolBar();
        btnUserRefresh5 = new javax.swing.JButton();
        btnCarApprovePrev2 = new javax.swing.JButton();
        lbPageIndexCarDeleted = new javax.swing.JLabel();
        btnCarApproveNext2 = new javax.swing.JButton();
        txtSearchTableCarDeleted = new javax.swing.JTextField();
        btnTableUserSearch5 = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableCarDeleted = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        toolBarUser6 = new javax.swing.JToolBar();
        btnBrandAdd = new javax.swing.JButton();
        btnBrandEdit = new javax.swing.JButton();
        btnBrandRemove = new javax.swing.JButton();
        btnBrandRefresh = new javax.swing.JButton();
        btnBrandPrev = new javax.swing.JButton();
        lbBrandPageIndex = new javax.swing.JLabel();
        btnBrandNext = new javax.swing.JButton();
        txtSearchTableBrand = new javax.swing.JTextField();
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
        txtSearchTableDetailType = new javax.swing.JTextField();
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
        txtSearchTableDetail = new javax.swing.JTextField();
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
        txtSearchTableUser.setText("nhập giá trị 1 cột bất kì để tìm kiếm");
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

        txtSearchTableAllCar.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableAllCar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableAllCar.setText("nhập giá trị 1 cột bất kì để tìm kiếm");
        txtSearchTableAllCar.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableAllCar.setColumns(40);
        txtSearchTableAllCar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableAllCarMouseClicked(evt);
            }
        });
        txtSearchTableAllCar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableAllCarKeyPressed(evt);
            }
        });
        toolBarUser1.add(txtSearchTableAllCar);

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

        tableAllCar.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableAllCar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableAllCar.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableAllCar.setGridColor(java.awt.SystemColor.control);
        tableAllCar.setRowHeight(30);
        tableAllCar.setRowMargin(2);
        tableAllCar.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane3.setViewportView(tableAllCar);

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

        cboxCarWaitConfirm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn", "Chấp nhận", "Từ chối" }));
        cboxCarWaitConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxCarWaitConfirmActionPerformed(evt);
            }
        });
        toolBarUser2.add(cboxCarWaitConfirm);

        btnCarWaitConfirmRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-refresh-24.png"))); // NOI18N
        btnCarWaitConfirmRefresh.setText("Làm mới");
        btnCarWaitConfirmRefresh.setFocusable(false);
        btnCarWaitConfirmRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCarWaitConfirmRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarWaitConfirmRefreshActionPerformed(evt);
            }
        });
        toolBarUser2.add(btnCarWaitConfirmRefresh);

        btnCarWaitConfirmPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-rewind-button-round-24.png"))); // NOI18N
        btnCarWaitConfirmPrev.setFocusable(false);
        btnCarWaitConfirmPrev.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCarWaitConfirmPrev.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCarWaitConfirmPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarWaitConfirmPrevActionPerformed(evt);
            }
        });
        toolBarUser2.add(btnCarWaitConfirmPrev);

        lbPageIndexCarWaitConfirm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndexCarWaitConfirm.setText("page");
        lbPageIndexCarWaitConfirm.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndexCarWaitConfirm.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser2.add(lbPageIndexCarWaitConfirm);

        btnCarWaitConfirmNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-fast-forward-round-24.png"))); // NOI18N
        btnCarWaitConfirmNext.setFocusable(false);
        btnCarWaitConfirmNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCarWaitConfirmNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCarWaitConfirmNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarWaitConfirmNextActionPerformed(evt);
            }
        });
        toolBarUser2.add(btnCarWaitConfirmNext);

        txtSearchTableCarWaitConfirm.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableCarWaitConfirm.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableCarWaitConfirm.setText("nhập giá trị 1 cột bất kì để tìm kiếm");
        txtSearchTableCarWaitConfirm.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableCarWaitConfirm.setColumns(40);
        txtSearchTableCarWaitConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableCarWaitConfirmMouseClicked(evt);
            }
        });
        txtSearchTableCarWaitConfirm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableCarWaitConfirmKeyPressed(evt);
            }
        });
        toolBarUser2.add(txtSearchTableCarWaitConfirm);

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

        tableCarWaitConfirm.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableCarWaitConfirm.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableCarWaitConfirm.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableCarWaitConfirm.setGridColor(java.awt.SystemColor.control);
        tableCarWaitConfirm.setRowHeight(30);
        tableCarWaitConfirm.setRowMargin(2);
        tableCarWaitConfirm.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane4.setViewportView(tableCarWaitConfirm);

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

        btnCarApprovePrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-rewind-button-round-24.png"))); // NOI18N
        btnCarApprovePrev.setFocusable(false);
        btnCarApprovePrev.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCarApprovePrev.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCarApprovePrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarApprovePrevActionPerformed(evt);
            }
        });
        toolBarUser3.add(btnCarApprovePrev);

        lbPageIndexCarApprove.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndexCarApprove.setText("page");
        lbPageIndexCarApprove.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndexCarApprove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser3.add(lbPageIndexCarApprove);

        btnCarApproveNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-fast-forward-round-24.png"))); // NOI18N
        btnCarApproveNext.setFocusable(false);
        btnCarApproveNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCarApproveNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCarApproveNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarApproveNextActionPerformed(evt);
            }
        });
        toolBarUser3.add(btnCarApproveNext);

        txtSearchTableCarApprove.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableCarApprove.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableCarApprove.setText("nhập giá trị 1 cột bất kì để tìm kiếm");
        txtSearchTableCarApprove.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableCarApprove.setColumns(40);
        txtSearchTableCarApprove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableCarApproveMouseClicked(evt);
            }
        });
        txtSearchTableCarApprove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableCarApproveKeyPressed(evt);
            }
        });
        toolBarUser3.add(txtSearchTableCarApprove);

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

        tableCarApprove.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableCarApprove.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableCarApprove.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableCarApprove.setGridColor(java.awt.SystemColor.control);
        tableCarApprove.setRowHeight(30);
        tableCarApprove.setRowMargin(2);
        tableCarApprove.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane5.setViewportView(tableCarApprove);

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

        toolBarUser4.setBorder(null);
        toolBarUser4.setRollover(true);

        cboxCarIgnoreApprove.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn", "Khôi Phục" }));
        cboxCarIgnoreApprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxCarIgnoreApproveActionPerformed(evt);
            }
        });
        toolBarUser4.add(cboxCarIgnoreApprove);

        btnCarIgnoreApprove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/remove.png"))); // NOI18N
        btnCarIgnoreApprove.setText("Xoá");
        btnCarIgnoreApprove.setFocusable(false);
        btnCarIgnoreApprove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCarIgnoreApprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarIgnoreApproveActionPerformed(evt);
            }
        });
        toolBarUser4.add(btnCarIgnoreApprove);

        btnCarIgnoreApproveRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-refresh-24.png"))); // NOI18N
        btnCarIgnoreApproveRefresh.setText("Làm mới");
        btnCarIgnoreApproveRefresh.setFocusable(false);
        btnCarIgnoreApproveRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCarIgnoreApproveRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarIgnoreApproveRefreshActionPerformed(evt);
            }
        });
        toolBarUser4.add(btnCarIgnoreApproveRefresh);

        btnCarApprovePrev1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-rewind-button-round-24.png"))); // NOI18N
        btnCarApprovePrev1.setFocusable(false);
        btnCarApprovePrev1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCarApprovePrev1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCarApprovePrev1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarApprovePrev1ActionPerformed(evt);
            }
        });
        toolBarUser4.add(btnCarApprovePrev1);

        lbPageIndexCarIgnoreApprove.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndexCarIgnoreApprove.setText("page");
        lbPageIndexCarIgnoreApprove.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndexCarIgnoreApprove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser4.add(lbPageIndexCarIgnoreApprove);

        btnCarApproveNext1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-fast-forward-round-24.png"))); // NOI18N
        btnCarApproveNext1.setFocusable(false);
        btnCarApproveNext1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCarApproveNext1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCarApproveNext1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarApproveNext1ActionPerformed(evt);
            }
        });
        toolBarUser4.add(btnCarApproveNext1);

        txtSearchTableCarIgnoreApprove.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableCarIgnoreApprove.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableCarIgnoreApprove.setText("nhập giá trị 1 cột bất kì để tìm kiếm");
        txtSearchTableCarIgnoreApprove.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableCarIgnoreApprove.setColumns(40);
        txtSearchTableCarIgnoreApprove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableCarIgnoreApproveMouseClicked(evt);
            }
        });
        txtSearchTableCarIgnoreApprove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableCarIgnoreApproveKeyPressed(evt);
            }
        });
        toolBarUser4.add(txtSearchTableCarIgnoreApprove);

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

        tableCarIgnoreApprove.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableCarIgnoreApprove.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableCarIgnoreApprove.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableCarIgnoreApprove.setGridColor(java.awt.SystemColor.control);
        tableCarIgnoreApprove.setRowHeight(30);
        tableCarIgnoreApprove.setRowMargin(2);
        tableCarIgnoreApprove.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane6.setViewportView(tableCarIgnoreApprove);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(toolBarUser4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabApprove.addTab("Từ chối duyệt", jPanel16);

        toolBarUser5.setBorder(null);
        toolBarUser5.setRollover(true);

        btnUserRefresh5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-refresh-24.png"))); // NOI18N
        btnUserRefresh5.setText("Làm mới");
        btnUserRefresh5.setFocusable(false);
        btnUserRefresh5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUserRefresh5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserRefresh5ActionPerformed(evt);
            }
        });
        toolBarUser5.add(btnUserRefresh5);

        btnCarApprovePrev2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-rewind-button-round-24.png"))); // NOI18N
        btnCarApprovePrev2.setFocusable(false);
        btnCarApprovePrev2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCarApprovePrev2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCarApprovePrev2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarApprovePrev2ActionPerformed(evt);
            }
        });
        toolBarUser5.add(btnCarApprovePrev2);

        lbPageIndexCarDeleted.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageIndexCarDeleted.setText("page");
        lbPageIndexCarDeleted.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbPageIndexCarDeleted.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUser5.add(lbPageIndexCarDeleted);

        btnCarApproveNext2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons8-fast-forward-round-24.png"))); // NOI18N
        btnCarApproveNext2.setFocusable(false);
        btnCarApproveNext2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCarApproveNext2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCarApproveNext2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarApproveNext2ActionPerformed(evt);
            }
        });
        toolBarUser5.add(btnCarApproveNext2);

        txtSearchTableCarDeleted.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableCarDeleted.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableCarDeleted.setText("nhập giá trị 1 cột bất kì để tìm kiếm");
        txtSearchTableCarDeleted.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableCarDeleted.setColumns(40);
        txtSearchTableCarDeleted.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableCarDeletedMouseClicked(evt);
            }
        });
        txtSearchTableCarDeleted.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableCarDeletedKeyPressed(evt);
            }
        });
        toolBarUser5.add(txtSearchTableCarDeleted);

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

        tableCarDeleted.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.SystemColor.controlHighlight));
        tableCarDeleted.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableCarDeleted.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableCarDeleted.setGridColor(java.awt.SystemColor.control);
        tableCarDeleted.setRowHeight(30);
        tableCarDeleted.setRowMargin(2);
        tableCarDeleted.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane7.setViewportView(tableCarDeleted);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBarUser5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(toolBarUser5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabApprove.addTab("Đã xoá", jPanel23);

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

        txtSearchTableBrand.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableBrand.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableBrand.setText("nhập giá trị 1 cột bất kì để tìm kiếm");
        txtSearchTableBrand.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableBrand.setColumns(40);
        txtSearchTableBrand.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableBrandMouseClicked(evt);
            }
        });
        txtSearchTableBrand.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableBrandKeyPressed(evt);
            }
        });
        toolBarUser6.add(txtSearchTableBrand);

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

        txtSearchTableDetailType.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableDetailType.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableDetailType.setText("nhập giá trị 1 cột bất kì để tìm kiếm");
        txtSearchTableDetailType.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableDetailType.setColumns(40);
        txtSearchTableDetailType.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableDetailTypeMouseClicked(evt);
            }
        });
        txtSearchTableDetailType.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableDetailTypeKeyPressed(evt);
            }
        });
        toolBarUser9.add(txtSearchTableDetailType);

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

        txtSearchTableDetail.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchTableDetail.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearchTableDetail.setText("nhập giá trị 1 cột bất kì để tìm kiếm");
        txtSearchTableDetail.setMinimumSize(new java.awt.Dimension(500, 500));
        txtSearchTableDetail.setColumns(40);
        txtSearchTableDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchTableDetailMouseClicked(evt);
            }
        });
        txtSearchTableDetail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTableDetailKeyPressed(evt);
            }
        });
        toolBarUser10.add(txtSearchTableDetail);

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

    private void btnUserRemove1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRemove1ActionPerformed
        int result = JOptionPane.showConfirmDialog(null, "Xoá những hàng đã chọn", "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
        if (result == JOptionPane.OK_OPTION) {
            int[] rows = tableAllCar.getSelectedRows();
            if (rows == null || rows.length <= 0) {
                return;
            }

            int index = 0;
            CarPutStatusModel[] models = new CarPutStatusModel[rows.length];
            int status = 2; // deleted

            for (int row : rows) {
                int carId = Integer.parseInt(((String) tableAllCar.getModel().getValueAt(row, 0)).trim());
                CarPutStatusModel model = new CarPutStatusModel(carId, status);
                models[index++] = model;
            }
            updateRangeCarStatus(models);
        }

    }//GEN-LAST:event_btnUserRemove1ActionPerformed

    private void btnUserRefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRefresh1ActionPerformed
        new Thread(() -> {
            getAllCars(true);
        }).start();
    }//GEN-LAST:event_btnUserRefresh1ActionPerformed

    private void btnUserPrev1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserPrev1ActionPerformed
        int temp = pageIndexAllCar - 1;
        if ((temp + 1) > 1) {
            pageIndexAllCar--;
            new Thread(() -> {
                getAllCars(false);
            }).start();
        } else {
            pageIndexAllCar = 1;
        }
    }//GEN-LAST:event_btnUserPrev1ActionPerformed

    private void btnUserNext1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserNext1ActionPerformed
        int temp = pageIndexAllCar + 1;
        if (temp <= totalPageAllCar) {
            pageIndexAllCar++;
            new Thread(() -> {
                getAllCars(false);
            }).start();
        } else {
            pageIndexAllCar = totalPageAllCar;
        }
    }//GEN-LAST:event_btnUserNext1ActionPerformed

    private void txtSearchTableAllCarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableAllCarMouseClicked
        if (txtSearchTableAllCar.getForeground() != Color.BLACK) {
            txtSearchTableAllCar.setForeground(Color.BLACK);
            txtSearchTableAllCar.setText("");
        }
    }//GEN-LAST:event_txtSearchTableAllCarMouseClicked

    private void txtSearchTableAllCarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableAllCarKeyPressed
        if (txtSearchTableAllCar.getForeground() != Color.BLACK) {
            txtSearchTableAllCar.setForeground(Color.BLACK);
            txtSearchTableAllCar.setText("");
            txtSearchTableAllCar.setText(String.valueOf(evt.getKeyChar()));
        }
    }//GEN-LAST:event_txtSearchTableAllCarKeyPressed

    private void btnTableUserSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch1ActionPerformed
        String text = txtSearchTableAllCar.getText();
        searchTableContents(text, tableAllCar);
    }//GEN-LAST:event_btnTableUserSearch1ActionPerformed

    private void btnCarWaitConfirmRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarWaitConfirmRefreshActionPerformed
        new Thread(() -> {
            getCarsWaitConfirm(true);
        }).start();
    }//GEN-LAST:event_btnCarWaitConfirmRefreshActionPerformed

    private void btnCarWaitConfirmPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarWaitConfirmPrevActionPerformed
        int temp = pageIndexCarWaitConfirm - 1;
        if ((temp + 1) > 1) {
            pageIndexCarWaitConfirm--;
            new Thread(() -> {
                getCarsWaitConfirm(false);
            }).start();
        } else {
            pageIndexCarWaitConfirm = 1;
        }
    }//GEN-LAST:event_btnCarWaitConfirmPrevActionPerformed

    private void btnCarWaitConfirmNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarWaitConfirmNextActionPerformed
        int temp = pageIndexCarWaitConfirm + 1;
        if (temp <= totalPageCarWaitConfirm) {
            pageIndexCarWaitConfirm++;
            new Thread(() -> {
                getCarsWaitConfirm(false);
            }).start();
        } else {
            pageIndexCarWaitConfirm = totalPageCarWaitConfirm;
        }
    }//GEN-LAST:event_btnCarWaitConfirmNextActionPerformed

    private void txtSearchTableCarWaitConfirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableCarWaitConfirmMouseClicked
        if (txtSearchTableCarWaitConfirm.getForeground() != Color.BLACK) {
            txtSearchTableCarWaitConfirm.setForeground(Color.BLACK);
            txtSearchTableCarWaitConfirm.setText("");
        }
    }//GEN-LAST:event_txtSearchTableCarWaitConfirmMouseClicked

    private void txtSearchTableCarWaitConfirmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableCarWaitConfirmKeyPressed

        if (txtSearchTableCarWaitConfirm.getForeground() != Color.BLACK) {
            txtSearchTableCarWaitConfirm.setForeground(Color.BLACK);
            txtSearchTableCarWaitConfirm.setText("");
            txtSearchTableCarWaitConfirm.setText(String.valueOf(evt.getKeyChar()));
        }
    }//GEN-LAST:event_txtSearchTableCarWaitConfirmKeyPressed

    private void btnTableUserSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch2ActionPerformed
        String text = txtSearchTableCarWaitConfirm.getText();
        searchTableContents(text, tableCarWaitConfirm);
    }//GEN-LAST:event_btnTableUserSearch2ActionPerformed

    private void btnUserRemove3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRemove3ActionPerformed
        int result = JOptionPane.showConfirmDialog(null, "Xoá những hàng đã chọn", "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
        if (result == JOptionPane.OK_OPTION) {
            int[] rows = tableCarApprove.getSelectedRows();
            if (rows == null || rows.length <= 0) {
                return;
            }

            int index = 0;
            CarPutStatusModel[] models = new CarPutStatusModel[rows.length];
            int status = 2; // deleted

            for (int row : rows) {
                int carId = Integer.parseInt(((String) tableCarApprove.getModel().getValueAt(row, 0)).trim());
                CarPutStatusModel model = new CarPutStatusModel(carId, status);
                models[index++] = model;
            }
            updateRangeCarStatus(models);
        }

    }//GEN-LAST:event_btnUserRemove3ActionPerformed

    private void btnUserRefresh3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRefresh3ActionPerformed
        new Thread(() -> {
            getCarsApprove(true);
        }).start();
    }//GEN-LAST:event_btnUserRefresh3ActionPerformed

    private void btnCarApprovePrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarApprovePrevActionPerformed
        int temp = pageIndexCarApprove - 1;
        if ((temp + 1) > 1) {
            pageIndexCarApprove--;
            new Thread(() -> {
                getCarsApprove(false);
            }).start();
        } else {
            pageIndexCarApprove = 1;
        }
    }//GEN-LAST:event_btnCarApprovePrevActionPerformed

    private void btnCarApproveNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarApproveNextActionPerformed
        int temp = pageIndexCarApprove + 1;
        if (temp <= totalPageCarApprove) {
            pageIndexCarApprove++;
            new Thread(() -> {
                getCarsApprove(false);
            }).start();
        } else {
            pageIndexCarApprove = totalPageCarApprove;
        }
    }//GEN-LAST:event_btnCarApproveNextActionPerformed

    private void txtSearchTableCarApproveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableCarApproveMouseClicked
        if (txtSearchTableCarApprove.getForeground() != Color.BLACK) {
            txtSearchTableCarApprove.setForeground(Color.BLACK);
            txtSearchTableCarApprove.setText("");
        }
    }//GEN-LAST:event_txtSearchTableCarApproveMouseClicked

    private void txtSearchTableCarApproveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableCarApproveKeyPressed
        if (txtSearchTableCarApprove.getForeground() != Color.BLACK) {
            txtSearchTableCarApprove.setForeground(Color.BLACK);
            txtSearchTableCarApprove.setText("");
            txtSearchTableCarApprove.setText(String.valueOf(evt.getKeyChar()));
        }
    }//GEN-LAST:event_txtSearchTableCarApproveKeyPressed

    private void btnTableUserSearch3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch3ActionPerformed
        String text = txtSearchTableCarApprove.getText();
        searchTableContents(text, tableCarApprove);
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
        if (temp <= totalPageBrand) {
            pageIndexBrand++;
            new Thread(() -> {
                getBrands(false);
            }).start();
        } else {
            pageIndexBrand = totalPageBrand;
        }
    }//GEN-LAST:event_btnBrandNextActionPerformed

    private void txtSearchTableBrandMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableBrandMouseClicked
        if (txtSearchTableBrand.getForeground() != Color.BLACK) {
            txtSearchTableBrand.setForeground(Color.BLACK);
            txtSearchTableBrand.setText("");
        }
    }//GEN-LAST:event_txtSearchTableBrandMouseClicked

    private void txtSearchTableBrandKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableBrandKeyPressed
        if (txtSearchTableBrand.getForeground() != Color.BLACK) {
            txtSearchTableBrand.setForeground(Color.BLACK);
            txtSearchTableBrand.setText("");
            txtSearchTableBrand.setText(String.valueOf(evt.getKeyChar()));
        }
    }//GEN-LAST:event_txtSearchTableBrandKeyPressed

    private void btnTableUserSearch6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch6ActionPerformed
        String text = txtSearchTableBrand.getText();
        searchTableContents(text, tableBrand);
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
        new Thread(() -> {
            getLogs();
        }).start();
    }//GEN-LAST:event_btnLogRefreshActionPerformed

    private void btnTableUserSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearchActionPerformed
        String text = txtSearchTableUser.getText();
        searchTableContents(text, tableUser);
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

    private void txtSearchTableDetailTypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableDetailTypeMouseClicked
        String text = txtSearchTableDetailType.getText();
        searchTableContents(text, tableDetailType);
    }//GEN-LAST:event_txtSearchTableDetailTypeMouseClicked

    private void txtSearchTableDetailTypeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableDetailTypeKeyPressed
        if (txtSearchTableDetailType.getForeground() != Color.BLACK) {
            txtSearchTableDetailType.setForeground(Color.BLACK);
            txtSearchTableDetailType.setText("");
            txtSearchTableDetailType.setText(String.valueOf(evt.getKeyChar()));
        }
    }//GEN-LAST:event_txtSearchTableDetailTypeKeyPressed

    private void btnTableUserSearch9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch9ActionPerformed
        if (txtSearchTableDetailType.getForeground() != Color.BLACK) {
            txtSearchTableDetailType.setForeground(Color.BLACK);
            txtSearchTableDetailType.setText("");
        }
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

    private void txtSearchTableDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableDetailMouseClicked
        if (txtSearchTableDetail.getForeground() != Color.BLACK) {
            txtSearchTableDetail.setForeground(Color.BLACK);
            txtSearchTableDetail.setText("");
        }
    }//GEN-LAST:event_txtSearchTableDetailMouseClicked

    private void txtSearchTableDetailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableDetailKeyPressed
        if (txtSearchTableDetail.getForeground() != Color.BLACK) {
            txtSearchTableDetail.setForeground(Color.BLACK);
            txtSearchTableDetail.setText("");
            txtSearchTableDetail.setText(String.valueOf(evt.getKeyChar()));
        }
    }//GEN-LAST:event_txtSearchTableDetailKeyPressed

    private void btnTableUserSearch10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch10ActionPerformed
        String text = txtSearchTableDetail.getText();
        searchTableContents(text, tableDetail);
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

    private void cboxCarWaitConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxCarWaitConfirmActionPerformed
        int[] rows = tableCarWaitConfirm.getSelectedRows();
        if (rows == null || rows.length <= 0) {
            return;
        }

        int index = 0;
        CarPutStatusModel[] models = new CarPutStatusModel[rows.length];
        int status = 0;
        switch (cboxCarWaitConfirm.getSelectedIndex()) {
            case 1: {
                status = 1;
                break;
            }
            case 2: {
                status = -1;
                break;
            }
            default:
                return;
        }
        for (int row : rows) {
            int carId = Integer.parseInt(((String) tableCarWaitConfirm.getModel().getValueAt(row, 0)).trim());
            CarPutStatusModel model = new CarPutStatusModel(carId, status);
            models[index++] = model;
        }
        updateRangeCarStatus(models);
        cboxCarWaitConfirm.getModel().setSelectedItem("Chọn");
    }//GEN-LAST:event_cboxCarWaitConfirmActionPerformed

    private void btnCarIgnoreApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarIgnoreApproveActionPerformed
        int result = JOptionPane.showConfirmDialog(null, "Xoá những hàng đã chọn", "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
        if (result == JOptionPane.OK_OPTION) {
            int[] rows = tableCarIgnoreApprove.getSelectedRows();
            if (rows == null || rows.length <= 0) {
                return;
            }

            int index = 0;
            CarPutStatusModel[] models = new CarPutStatusModel[rows.length];
            int status = 2;

            for (int row : rows) {
                int carId = Integer.parseInt(((String) tableCarIgnoreApprove.getModel().getValueAt(row, 0)).trim());
                CarPutStatusModel model = new CarPutStatusModel(carId, status);
                models[index++] = model;
            }
            updateRangeCarStatus(models);
        }

    }//GEN-LAST:event_btnCarIgnoreApproveActionPerformed

    private void btnCarIgnoreApproveRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarIgnoreApproveRefreshActionPerformed
        new Thread(() -> {
            getCarsIgnoreApprove(true);
        }).start();
    }//GEN-LAST:event_btnCarIgnoreApproveRefreshActionPerformed

    private void btnCarApprovePrev1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarApprovePrev1ActionPerformed
        int temp = pageIndexCarIgnoreApprove - 1;
        if ((temp + 1) > 1) {
            pageIndexCarIgnoreApprove--;
            new Thread(() -> {
                getCarsIgnoreApprove(false);
            }).start();
        } else {
            pageIndexCarIgnoreApprove = 1;
        }
    }//GEN-LAST:event_btnCarApprovePrev1ActionPerformed

    private void btnCarApproveNext1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarApproveNext1ActionPerformed
        int temp = pageIndexCarIgnoreApprove + 1;
        if (temp <= totalPageCarIgnoreApprove) {
            pageIndexCarIgnoreApprove++;
            new Thread(() -> {
                getCarsIgnoreApprove(false);
            }).start();
        } else {
            pageIndexCarIgnoreApprove = totalPageCarIgnoreApprove;
        }
    }//GEN-LAST:event_btnCarApproveNext1ActionPerformed

    private void txtSearchTableCarIgnoreApproveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableCarIgnoreApproveMouseClicked
        if (txtSearchTableCarIgnoreApprove.getForeground() != Color.BLACK) {
            txtSearchTableCarIgnoreApprove.setForeground(Color.BLACK);
            txtSearchTableCarIgnoreApprove.setText("");
        }
    }//GEN-LAST:event_txtSearchTableCarIgnoreApproveMouseClicked

    private void txtSearchTableCarIgnoreApproveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableCarIgnoreApproveKeyPressed
        if (txtSearchTableCarIgnoreApprove.getForeground() != Color.BLACK) {
            txtSearchTableCarIgnoreApprove.setForeground(Color.BLACK);
            txtSearchTableCarIgnoreApprove.setText("");
            txtSearchTableCarIgnoreApprove.setText(String.valueOf(evt.getKeyChar()));
        }
    }//GEN-LAST:event_txtSearchTableCarIgnoreApproveKeyPressed

    private void btnTableUserSearch4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch4ActionPerformed
        String text = txtSearchTableCarIgnoreApprove.getText();
        searchTableContents(text, tableCarIgnoreApprove);
    }//GEN-LAST:event_btnTableUserSearch4ActionPerformed

    private void btnUserRefresh5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserRefresh5ActionPerformed
        new Thread(() -> {
            getCarsDeleted(true);
        }).start();
    }//GEN-LAST:event_btnUserRefresh5ActionPerformed

    private void btnCarApprovePrev2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarApprovePrev2ActionPerformed
        int temp = pageIndexCarDeleted - 1;
        if ((temp + 1) > 1) {
            pageIndexCarDeleted--;
            new Thread(() -> {
                getCarsDeleted(false);
            }).start();
        } else {
            pageIndexCarDeleted = 1;
        }
    }//GEN-LAST:event_btnCarApprovePrev2ActionPerformed

    private void btnCarApproveNext2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarApproveNext2ActionPerformed
        int temp = pageIndexCarDeleted + 1;
        if (temp <= totalPageCarDeleted) {
            pageIndexCarDeleted++;
            new Thread(() -> {
                getCarsDeleted(false);
            }).start();
        } else {
            pageIndexCarDeleted = totalPageCarDeleted;
        }
    }//GEN-LAST:event_btnCarApproveNext2ActionPerformed

    private void txtSearchTableCarDeletedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchTableCarDeletedMouseClicked
        if (txtSearchTableCarDeleted.getForeground() != Color.BLACK) {
            txtSearchTableCarDeleted.setForeground(Color.BLACK);
            txtSearchTableCarDeleted.setText("");
        }
    }//GEN-LAST:event_txtSearchTableCarDeletedMouseClicked

    private void txtSearchTableCarDeletedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTableCarDeletedKeyPressed
        if (txtSearchTableCarDeleted.getForeground() != Color.BLACK) {
            txtSearchTableCarDeleted.setForeground(Color.BLACK);
            txtSearchTableCarDeleted.setText("");
            txtSearchTableCarDeleted.setText(String.valueOf(evt.getKeyChar()));
        }
    }//GEN-LAST:event_txtSearchTableCarDeletedKeyPressed

    private void btnTableUserSearch5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableUserSearch5ActionPerformed
        String text = txtSearchTableUser.getText();
        searchTableContents(text, tableCarDeleted);
    }//GEN-LAST:event_btnTableUserSearch5ActionPerformed

    private void cboxCarIgnoreApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxCarIgnoreApproveActionPerformed
        int[] rows = tableCarIgnoreApprove.getSelectedRows();
        if (rows == null || rows.length <= 0) {
            return;
        }

        int index = 0;
        CarPutStatusModel[] models = new CarPutStatusModel[rows.length];
        int status = 0;
        if (cboxCarIgnoreApprove.getSelectedIndex() == 0) {
            return;
        }

        for (int row : rows) {
            int carId = Integer.parseInt(((String) tableCarIgnoreApprove.getModel().getValueAt(row, 0)).trim());
            CarPutStatusModel model = new CarPutStatusModel(carId, status);
            models[index++] = model;
        }
        updateRangeCarStatus(models);
        cboxCarIgnoreApprove.getModel().setSelectedItem("Chọn");
    }//GEN-LAST:event_cboxCarIgnoreApproveActionPerformed

    private void updateRangeCarStatus(CarPutStatusModel[] model) {
        try {
            String url = "/Cars/update-status-cars";
            Program.HttpMethod method = Program.HttpMethod.PUT;
            String jsonPayload = new Gson().toJson(model);
            byte[] data = jsonPayload.getBytes("UTF-8");

            Map<Program.HttpHeader, String> headers = new HashMap<>();
            headers.put(Program.HttpHeader.Authorization, Program.PrefixToken + Program.Token);
            headers.put(Program.HttpHeader.ContentType, "application/json; charset=utf-8");

            String jsonData = Program.SendHttp(url, method, data, null, headers);
            Map<String, Object> response = new Gson().fromJson(jsonData, Map.class);
            boolean isSuccess = (boolean) response.get("success");
            if (!isSuccess) {
                String message = response.get("message").toString();
                JOptionPane.showMessageDialog(this, message, "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
            } else {

                JOptionPane.showMessageDialog(this, "Cập nhật thành công", "Thông báo", TrayIcon.MessageType.INFO.ordinal(), null);
            }
        } catch (ProtocolException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Thông báo", TrayIcon.MessageType.ERROR.ordinal(), null);
        }
    }

    public void searchTableContents(String searchString, JTable table) {
        new Thread(() -> {
            DefaultTableModel temp = (DefaultTableModel) table.getModel();
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

            ((DefaultTableModel) table.getModel()).setRowCount(0);

            list.forEach(row -> {
                ((DefaultTableModel) table.getModel()).addRow(row);
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

        new Thread(() -> {
            getAllCars(false);
        }).start();

        new Thread(() -> {
            getCarsWaitConfirm(false);
        }).start();

        new Thread(() -> {
            getCarsApprove(false);
        }).start();

        new Thread(() -> {
            getCarsIgnoreApprove(false);
        }).start();

        new Thread(() -> {
            getCarsDeleted(false);
        }).start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrandAdd;
    private javax.swing.JButton btnBrandEdit;
    private javax.swing.JButton btnBrandNext;
    private javax.swing.JButton btnBrandPrev;
    private javax.swing.JButton btnBrandRefresh;
    private javax.swing.JButton btnBrandRemove;
    private javax.swing.JButton btnCarApproveNext;
    private javax.swing.JButton btnCarApproveNext1;
    private javax.swing.JButton btnCarApproveNext2;
    private javax.swing.JButton btnCarApprovePrev;
    private javax.swing.JButton btnCarApprovePrev1;
    private javax.swing.JButton btnCarApprovePrev2;
    private javax.swing.JButton btnCarIgnoreApprove;
    private javax.swing.JButton btnCarIgnoreApproveRefresh;
    private javax.swing.JButton btnCarWaitConfirmNext;
    private javax.swing.JButton btnCarWaitConfirmPrev;
    private javax.swing.JButton btnCarWaitConfirmRefresh;
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
    private javax.swing.JButton btnTableUserSearch4;
    private javax.swing.JButton btnTableUserSearch5;
    private javax.swing.JButton btnTableUserSearch6;
    private javax.swing.JButton btnTableUserSearch9;
    private javax.swing.JButton btnUserAdd;
    private javax.swing.JButton btnUserEdit;
    private javax.swing.JButton btnUserNext;
    private javax.swing.JButton btnUserNext1;
    private javax.swing.JButton btnUserPrev;
    private javax.swing.JButton btnUserPrev1;
    private javax.swing.JButton btnUserRefresh;
    private javax.swing.JButton btnUserRefresh1;
    private javax.swing.JButton btnUserRefresh3;
    private javax.swing.JButton btnUserRefresh5;
    private javax.swing.JButton btnUserRemove;
    private javax.swing.JButton btnUserRemove1;
    private javax.swing.JButton btnUserRemove3;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboxCarIgnoreApprove;
    private javax.swing.JComboBox<String> cboxCarWaitConfirm;
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
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
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
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lbBrandPageIndex;
    private javax.swing.JLabel lbDetailPageIndex;
    private javax.swing.JLabel lbDetailTypePageIndex;
    private javax.swing.JLabel lbPageIndex;
    private javax.swing.JLabel lbPageIndex1;
    private javax.swing.JLabel lbPageIndexCarApprove;
    private javax.swing.JLabel lbPageIndexCarDeleted;
    private javax.swing.JLabel lbPageIndexCarIgnoreApprove;
    private javax.swing.JLabel lbPageIndexCarWaitConfirm;
    private javax.swing.JTabbedPane tabApprove;
    private javax.swing.JTabbedPane tabDetail;
    private javax.swing.JTable tableAllCar;
    private javax.swing.JTable tableBrand;
    private javax.swing.JTable tableCarApprove;
    private javax.swing.JTable tableCarDeleted;
    private javax.swing.JTable tableCarIgnoreApprove;
    private javax.swing.JTable tableCarWaitConfirm;
    private javax.swing.JTable tableDetail;
    private javax.swing.JTable tableDetailType;
    private javax.swing.JTable tableLog;
    private javax.swing.JTable tableUser;
    private javax.swing.JToolBar toolBarUser;
    private javax.swing.JToolBar toolBarUser1;
    private javax.swing.JToolBar toolBarUser10;
    private javax.swing.JToolBar toolBarUser2;
    private javax.swing.JToolBar toolBarUser3;
    private javax.swing.JToolBar toolBarUser4;
    private javax.swing.JToolBar toolBarUser5;
    private javax.swing.JToolBar toolBarUser6;
    private javax.swing.JToolBar toolBarUser8;
    private javax.swing.JToolBar toolBarUser9;
    private javax.swing.JTextField txtSearchTableAllCar;
    private javax.swing.JTextField txtSearchTableBrand;
    private javax.swing.JTextField txtSearchTableCarApprove;
    private javax.swing.JTextField txtSearchTableCarDeleted;
    private javax.swing.JTextField txtSearchTableCarIgnoreApprove;
    private javax.swing.JTextField txtSearchTableCarWaitConfirm;
    private javax.swing.JTextField txtSearchTableDetail;
    private javax.swing.JTextField txtSearchTableDetailType;
    private javax.swing.JTextField txtSearchTableUser;
    // End of variables declaration//GEN-END:variables

}
