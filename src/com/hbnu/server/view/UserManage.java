package com.hbnu.server.view;

import com.hbnu.server.Server;
import com.hbnu.server.jdbc.factory.DAOFactory;
import com.hbnu.server.jdbc.vo.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author luanhao
 * @date 2022年11月09日 11:53
 */
public class UserManage extends Box {
    final int WIDTH = 600;
    final int HEIGHT = 600;

    private static JTable table;
    private static Vector<String> titles;
    private static Vector<Vector> tableData;
    private static TableModel tableModel;

    public UserManage() {
        super(BoxLayout.Y_AXIS);

        JPanel btnPanel = new JPanel();
        btnPanel.setMaximumSize(new Dimension(WIDTH, 80));
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton addBtn = new JButton("添加");
        JButton updateBtn = new JButton("修改");
        JButton deleteBtn = new JButton("删除");

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUserDialog(MainInterface.jf, "添加用户", true).setVisible(true);
                requestData();
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(MainInterface.jf, "请选择要修改的用户");
                    return;
                }

                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String username = String.valueOf(id);
                new UpdateUserDialog(MainInterface.jf, "修改用户", true, username).setVisible(true);
                requestData();
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(MainInterface.jf, "请选择要删除的用户");
                    return;
                }
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                User user = new User();
                user.setId(id);
                try {
                    if (!DAOFactory.getUserDAOInstance().doDelete(user)) {
                        JOptionPane.showMessageDialog(MainInterface.jf, "删除失败");
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(MainInterface.jf, "删除成功");
                Map<String, String> params = new HashMap<>();
                params.put("username", String.valueOf(id));
                params.put("message", "删除成功");
                MainInterface.sendLog(params);
                requestData();
            }
        });

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);

        add(btnPanel);

//        组装表格
        String[] ts = {"用户名", "昵称", "密码", "个人介绍", "密保问题", "密保答案"};
        titles = new Vector<>();
        for (String title : ts) {
            titles.add(title);
        }

        tableData = new Vector<>();

        tableModel = new DefaultTableModel(tableData, titles);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
//        设置只能选中一行
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        requestData();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
    public static void requestData() {
        try {
            List<User> users = DAOFactory.getUserDAOInstance().doQuery();
            Vector<Vector> vectors = new Vector<>();
            for (User user : users) {
                Vector vector = new Vector();
                vector.add(user.getId());
                vector.add(user.getName());
                vector.add(user.getPassword());
                vector.add(user.getIntroduction());
                vector.add(user.getSecurityQuestion());
                vector.add(user.getClassifiedAnswer());
                vectors.add(vector);
            }
            tableData.clear();
            for (Vector vector : vectors) {
                tableData.add(vector);
            }
            tableModel = new DefaultTableModel(tableData, titles);
            table.setModel(tableModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
