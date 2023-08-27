/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jframe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static jframe.DBConnection.con;

/**
 *
 * @author ASUS
 */
public class ReturnBook extends javax.swing.JFrame {

    /**
     * Creates new form IssueBook
     */
    DefaultTableModel model;
    public ReturnBook() {
        initComponents();
         setIssueBookDetalsToTable();
    }
    
    
    
     //validation
    
    public boolean validateSignup(){
        
         String bookId= txt_bookId.getText();
        String studentId= txt_studentId.getText();
      //  Date issuedate = date_issueDate.getDatoFecha();
    //    Date duedate = date_dueDate.getDatoFecha();
        
        
         if(bookId.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Book Id");
            return false;
        }
        if(studentId.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Student Id");
            return false;
        }
     
        
        return true;
    }
    
    
             //to set the details into the table from database
        
    public void setIssueBookDetalsToTable(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryms","root","");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from issue_book where status='"+"Pending"+"'");
            
            
           while(rs.next()){
               String bookId= rs.getString("book_id");
               String studentId= rs.getString("Student_id");
              
               
               Object[] obj= {bookId, studentId};
               model=(DefaultTableModel) tbl_issuebookdetail.getModel();
               model.addRow(obj);
          }
                    
        }
     catch(Exception e){
    e.printStackTrace();
}

    }
    
    
    //TO FETCH ISSUE BOOK DETAILS FROM DB AND DISPLAY IT TO PANEL
    
    public void getIssueBookDetails(){
        
        
        int bookId = Integer.parseInt(txt_bookId.getText());
        int studentId = Integer.parseInt(txt_studentId.getText());
        
         try{
             Connection con = DBConnection.getConnection();
             String sql = "select * from issue_book where book_id=? and student_id=? and status=?";
             PreparedStatement pst = con.prepareStatement(sql);
             pst.setInt(1, bookId);
             pst.setInt(2, studentId);
             pst.setString(3, "pending");
             
             ResultSet rs= pst.executeQuery();
             
             if(rs.next()){
                
                 lbl_issueId.setText(rs.getString("issueid"));
                 lbl_bookName.setText(rs.getString("book_name"));       
                 lbl_studentName.setText(rs.getString("student_name"));
                 lbl_issueDate.setText(rs.getString("issue_date"));
                 lbl_dueDate.setText(rs.getString("due_date"));
                
                 lbl_bookError.setText("");
                 
                }
             else{
                 lbl_bookError.setText("NO Record Found!!");
                 
                 lbl_issueId.setText("");
                 lbl_bookName.setText("");       
                 lbl_studentName.setText("");
                 lbl_issueDate.setText("");
                 lbl_dueDate.setText("");   
             }
         }catch(Exception e){
             e.printStackTrace();
         }
        
    }
    
    
    //RETURN THE BOOK, 
    //INSERT TO RETURN BOOK DATABASE
    
    public boolean returnBook(){
        
        boolean isReturned= false;
        int bookId= Integer.parseInt(txt_bookId.getText());
        int studentId = Integer.parseInt(txt_studentId.getText());
       
        String issueId= lbl_issueId.getText();
        String fine= txt_fine.getText();
        
        Date uReturnDate= date_returnDate.getDatoFecha();
              
        Long l1= uReturnDate.getTime();
       
        java.sql.Date sReturnDate = new java.sql.Date(l1);
         
               
         try{
             Connection con = DBConnection.getConnection();
             String sql = "insert into return_book(issueid, "+" returndate, fine) values(?,?,?)";
            
             PreparedStatement pst = con.prepareStatement(sql);
             pst.setString(1, issueId);
             pst.setDate(2, sReturnDate);
             pst.setString(3, fine);
                
             int rowCount = pst.executeUpdate();
             if(rowCount>0){
                 isReturned=true;
             }else{
                 isReturned=false;
             }
             
         }catch(Exception e){
             e.printStackTrace();
         }
         
         // TO UPDATE ISSUE BOOK DATABASE
         try{
             Connection con = DBConnection.getConnection();
            
             String sql1 = "update issue_book set status=? where student_id=? and book_id=? and status=?";
             PreparedStatement pst1 = con.prepareStatement(sql1);
             pst1.setString(1, "Returned");
             pst1.setInt(2, studentId);
              pst1.setInt(3, bookId);
             pst1.setString(4, "Pending");
             
                          
             int rowCount = pst1.executeUpdate();
             if(rowCount>0){
                 isReturned=true;
             }else{
                 isReturned=false;
             }
             
             
         }catch(Exception e){
             e.printStackTrace();
         }
         return isReturned;         
        }
   
    
    
    //TO UPDATE BOOK COUNT AFTER ISSUEING
     public void updateBookCount(){
         int bookId= Integer.parseInt(txt_bookId.getText());
        
         
         try{
             Connection con = DBConnection.getConnection();
             String sql = "update book_details set quantity = quantity+1  where book_id= ?";
             PreparedStatement pst = con.prepareStatement(sql);
             pst.setInt(1, bookId);
             
             int rowCount = pst.executeUpdate();
             if(rowCount>0){
                 JOptionPane.showMessageDialog(this, "Book Count Updated!!");
                
                 
                 
                 
             }else{
                 JOptionPane.showMessageDialog(this, "Cannot Update Book Count!!");
             }
         }catch(Exception e){
             e.printStackTrace();
         }
     }
    
     
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_main = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lbl_bookError = new javax.swing.JLabel();
        lbl_issueDate = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbl_issueId = new javax.swing.JLabel();
        lbl_bookName = new javax.swing.JLabel();
        lbl_studentName = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lbl_dueDate = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txt_bookId = new app.bolivia.swing.JCTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_studentId = new app.bolivia.swing.JCTextField();
        rSMaterialButtonCircle2 = new rojerusan.RSMaterialButtonCircle();
        rSMaterialButtonCircle3 = new rojerusan.RSMaterialButtonCircle();
        jLabel18 = new javax.swing.JLabel();
        date_returnDate = new rojeru_san.componentes.RSDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_issuebookdetail = new rojerusan.RSTableMetro();
        rSMaterialButtonCircle1 = new rojerusan.RSMaterialButtonCircle();
        txt_fine = new app.bolivia.swing.JCTextField();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_main.setBackground(new java.awt.Color(255, 255, 255));
        panel_main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(240, 51, 51));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(51, 0, 204));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Rewind_48px.png"))); // NOI18N
        jLabel11.setText("Back");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 100, 40));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 40));

        jLabel12.setFont(new java.awt.Font("Algerian", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Literature_100px_1.png"))); // NOI18N
        jLabel12.setText(" BOOK DETAILS");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 290, 100));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 350, 5));

        lbl_bookError.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lbl_bookError.setForeground(new java.awt.Color(255, 255, 51));
        jPanel4.add(lbl_bookError, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 600, 310, 40));

        lbl_issueDate.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lbl_issueDate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(lbl_issueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 450, 280, 40));

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Book Name:");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 130, 20));

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Student Name:");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 160, 20));

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Issue ID:");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 110, 20));

        lbl_issueId.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lbl_issueId.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(lbl_issueId, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 300, 280, 40));

        lbl_bookName.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lbl_bookName.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(lbl_bookName, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 350, 280, 40));

        lbl_studentName.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lbl_studentName.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(lbl_studentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, 280, 40));

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("IssueDate:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 120, 20));

        lbl_dueDate.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lbl_dueDate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(lbl_dueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 500, 280, 40));

        jLabel21.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("DueDate:");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 120, 20));

        panel_main.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 790));

        jLabel4.setFont(new java.awt.Font("Algerian", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Student_Registration_100px_2.png"))); // NOI18N
        jLabel4.setText(" STUDENT DETAILS");
        panel_main.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 330, 100));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        panel_main.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 350, 5));

        jLabel2.setFont(new java.awt.Font("Algerian", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel2.setText(" return BOOK");
        panel_main.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 100, 310, 70));

        jPanel3.setBackground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        panel_main.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 180, -1, 5));

        jPanel9.setBackground(new java.awt.Color(102, 0, 204));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText(" X");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jPanel9.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 70, 40));

        panel_main.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 0, 90, 40));

        txt_bookId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));
        txt_bookId.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txt_bookId.setPhColor(new java.awt.Color(51, 51, 51));
        txt_bookId.setPlaceholder("Enter Book ID"); // NOI18N
        txt_bookId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_bookIdFocusLost(evt);
            }
        });
        txt_bookId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookIdActionPerformed(evt);
            }
        });
        panel_main.add(txt_bookId, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 310, 290, -1));

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 51));
        jLabel14.setText("Book ID:");
        panel_main.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 310, 130, 30));

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 51));
        jLabel10.setText("Student ID:");
        panel_main.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 400, 140, 30));

        txt_studentId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));
        txt_studentId.setForeground(new java.awt.Color(51, 51, 51));
        txt_studentId.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txt_studentId.setPhColor(new java.awt.Color(51, 51, 51));
        txt_studentId.setPlaceholder("Enter Student ID"); // NOI18N
        txt_studentId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_studentIdFocusLost(evt);
            }
        });
        txt_studentId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_studentIdActionPerformed(evt);
            }
        });
        panel_main.add(txt_studentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 400, 290, -1));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(102, 0, 204));
        rSMaterialButtonCircle2.setText("FIND DETAILS");
        rSMaterialButtonCircle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle2ActionPerformed(evt);
            }
        });
        panel_main.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 470, 160, 60));

        rSMaterialButtonCircle3.setBackground(new java.awt.Color(255, 0, 0));
        rSMaterialButtonCircle3.setText("RETURN BOOK");
        rSMaterialButtonCircle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle3ActionPerformed(evt);
            }
        });
        panel_main.add(rSMaterialButtonCircle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 700, 200, 60));

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 0, 51));
        jLabel18.setText("Return Date :");
        panel_main.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 580, 140, 30));

        date_returnDate.setColorBackground(new java.awt.Color(255, 0, 0));
        date_returnDate.setColorForeground(new java.awt.Color(51, 51, 51));
        date_returnDate.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        date_returnDate.setFuente(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        date_returnDate.setPlaceholder("Select Return Date");
        panel_main.add(date_returnDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 580, 340, -1));

        tbl_issuebookdetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book Id ", "Student Id"
            }
        ));
        tbl_issuebookdetail.setColorBackgoundHead(new java.awt.Color(102, 0, 204));
        tbl_issuebookdetail.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_issuebookdetail.setColorSelBackgound(new java.awt.Color(255, 0, 102));
        tbl_issuebookdetail.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        tbl_issuebookdetail.setFuenteFilas(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        tbl_issuebookdetail.setFuenteFilasSelect(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        tbl_issuebookdetail.setFuenteHead(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        tbl_issuebookdetail.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbl_issuebookdetail.setRowHeight(40);
        tbl_issuebookdetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_issuebookdetailMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_issuebookdetail);

        panel_main.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 290, 320, 230));

        rSMaterialButtonCircle1.setBackground(new java.awt.Color(51, 0, 204));
        rSMaterialButtonCircle1.setText("CLEAR");
        rSMaterialButtonCircle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle1ActionPerformed(evt);
            }
        });
        panel_main.add(rSMaterialButtonCircle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 470, 150, 60));

        txt_fine.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));
        txt_fine.setForeground(new java.awt.Color(51, 51, 51));
        txt_fine.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txt_fine.setPhColor(new java.awt.Color(51, 51, 51));
        txt_fine.setPlaceholder("Enter Student ID"); // NOI18N
        txt_fine.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_fineFocusLost(evt);
            }
        });
        txt_fine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fineActionPerformed(evt);
            }
        });
        panel_main.add(txt_fine, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 660, 290, -1));

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 51));
        jLabel13.setText("Fine Amount:");
        panel_main.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 660, 140, 30));

        getContentPane().add(panel_main, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1350, 790));

        setSize(new java.awt.Dimension(1342, 788));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
          int a = JOptionPane.showConfirmDialog(null, "Do you really want to close application","Select",JOptionPane.YES_NO_OPTION);
        if(a==0)
            System.exit(0);
                      
    }//GEN-LAST:event_jLabel9MouseClicked

    private void txt_bookIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_bookIdFocusLost
       
       
    }//GEN-LAST:event_txt_bookIdFocusLost

    private void txt_bookIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookIdActionPerformed

    private void txt_studentIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_studentIdFocusLost
          
    }//GEN-LAST:event_txt_studentIdFocusLost

    private void txt_studentIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_studentIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentIdActionPerformed

    private void rSMaterialButtonCircle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2ActionPerformed
      if (validateSignup()==true){
      getIssueBookDetails();}
    }//GEN-LAST:event_rSMaterialButtonCircle2ActionPerformed

    private void rSMaterialButtonCircle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle3ActionPerformed
       if (validateSignup()==true)
               {
        if(returnBook()==true){
            JOptionPane.showMessageDialog(this, "Book Returned Successfully");
            updateBookCount();
        }else{
             JOptionPane.showMessageDialog(this, "Book Return UnSuccessful");
        }
         setVisible(false);
        new ReturnBook().setVisible(true);
       }
    }//GEN-LAST:event_rSMaterialButtonCircle3ActionPerformed

    private void tbl_issuebookdetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_issuebookdetailMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tbl_issuebookdetailMouseClicked

    private void rSMaterialButtonCircle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1ActionPerformed
        setVisible(false);
        new ReturnBook().setVisible(true);
    }//GEN-LAST:event_rSMaterialButtonCircle1ActionPerformed

    private void txt_fineFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_fineFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fineFocusLost

    private void txt_fineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fineActionPerformed

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
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-foltxt_bookId* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReturnBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.componentes.RSDateChooser date_returnDate;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_bookError;
    private javax.swing.JLabel lbl_bookName;
    private javax.swing.JLabel lbl_dueDate;
    private javax.swing.JLabel lbl_issueDate;
    private javax.swing.JLabel lbl_issueId;
    private javax.swing.JLabel lbl_studentName;
    private javax.swing.JPanel panel_main;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle1;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle3;
    private rojerusan.RSTableMetro tbl_issuebookdetail;
    private app.bolivia.swing.JCTextField txt_bookId;
    private app.bolivia.swing.JCTextField txt_fine;
    private app.bolivia.swing.JCTextField txt_studentId;
    // End of variables declaration//GEN-END:variables
}
