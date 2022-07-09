 package mage.client.dialog;

 import mage.client.MageFrame;
 import mage.client.game.GamePanel;
 import mage.client.util.Format;
 import mage.client.util.ImageHelper;
 import mage.client.util.audio.AudioManager;
 import mage.client.util.gui.BufferedImageBuilder;
 import mage.view.GameEndView;
 import mage.view.PlayerView;

 import javax.swing.*;
 import java.awt.*;
 import java.awt.image.BufferedImage;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.PrintWriter;
 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;

 /**
  * Game GUI: end game window
  *
  * @author LevelX2
  */
 public class GameEndDialog extends MageDialog {

     /**
      * Creates new form GameEndDialog
      *
      * @param gameEndView
      */
     public GameEndDialog(GameEndView gameEndView) {
         initComponents();
         this.modal = true;

         pnlText.setOpaque(true);
         pnlText.setBackground(new Color(240, 240, 240, 140));

         Rectangle r = new Rectangle(610, 250);

         Image image;
         if (gameEndView.hasWon()) {
             image = ImageHelper.getImageFromResources(PreferencesDialog.getCurrentTheme().getWinlossPath("game_won.jpg"));
         } else {
             image = ImageHelper.getImageFromResources(PreferencesDialog.getCurrentTheme().getWinlossPath("game_lost.jpg"));
         }

         BufferedImage imageResult = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
         ImageIcon icon = new ImageIcon(imageResult);
         lblResultImage.setIcon(icon);

         this.lblGameInfo.setText(gameEndView.getGameInfo());
         this.lblMatchInfo.setText(gameEndView.getMatchInfo());
         this.lblAdditionalInfo.setText(gameEndView.getAdditionalInfo());

         String autoSave = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_LOG_AUTO_SAVE, "true");
         if (autoSave.equals("true")) {
             this.saveGameLog(gameEndView);
         }

         DateFormat df = DateFormat.getDateTimeInstance();

         // game duration
         txtDurationGame.setText(" " + Format.getDuration(gameEndView.getStartTime(), gameEndView.getEndTime()));
         txtDurationGame.setToolTipText(new StringBuilder(df.format(gameEndView.getStartTime())).append(" - ").append(df.format(gameEndView.getEndTime())).toString());

         // match duration
         Calendar cal = Calendar.getInstance();
         txtDurationMatch.setText(" " + Format.getDuration(gameEndView.getMatchView().getStartTime(), cal.getTime()));
         txtDurationMatch.setToolTipText(new StringBuilder(df.format(gameEndView.getMatchView().getStartTime())).append(" - ").append(df.format(cal.getTime())).toString());

         StringBuilder sb = new StringBuilder(" ");
         for (PlayerView player : gameEndView.getPlayers()) {
             sb.append(player.getName()).append(" Life: ").append(player.getLife()).append(' ');
         }
         this.txtLife.setText(sb.toString());

         if (gameEndView.hasWon()) {
             AudioManager.playPlayerWon();
         } else {
             AudioManager.playPlayerLost();
         }

         txtMatchScore.setText(gameEndView.getMatchView().getResult());
     }

     private void saveGameLog(GameEndView gameEndView) {
         String dir = "gamelogs";
         File saveDir = new File(dir);
         //Here comes the existence check
         if (!saveDir.exists()) {
             saveDir.mkdirs();
         }
         // get game log
         try {
             if (gameEndView.getMatchView().getGames().size() > 0) {
                 GamePanel gamePanel = MageFrame.getGame(gameEndView.getMatchView().getGames().get(gameEndView.getMatchView().getGames().size() - 1));
                 if (gamePanel != null) {
                     SimpleDateFormat sdf = new SimpleDateFormat();
                     sdf.applyPattern("yyyyMMdd_HHmmss");
                     String fileName = new StringBuilder(dir).append(File.separator)
                             .append(sdf.format(gameEndView.getStartTime()))
                             .append('_').append(gameEndView.getMatchView().getGameType())
                             .append('_').append(gameEndView.getMatchView().getGames().size())
                             .append(".html").toString();
                     PrintWriter out = new PrintWriter(fileName);
                     String log = gamePanel.getGameLog();
                     log = log.replace("<body>", "<body style=\"background-color:black\">");
                     log = log.replace("<font color=\"#CCCC33\">", "<br><font color=\"#CCCC33\">"); //The color is TIMESTAMP_COLOR and we can utilize it to add line breaks to new lines
                     out.print(log);
                     out.close();
                 }
             }
         } catch (FileNotFoundException ex) {
             JOptionPane.showMessageDialog(this, "Error while writing game log to file\n\n" + ex, "Error writing gamelog", JOptionPane.ERROR_MESSAGE);
         }

     }

     public void showDialog() {
         this.setLocation(100, 100);
         this.setVisible(true);
     }

     /**
      * This method is called from within the constructor to initialize the form.
      * WARNING: Do NOT modify this code. The content of this method is always
      * regenerated by the Form Editor.
      */
     @SuppressWarnings("unchecked")
     // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
     private void initComponents() {

         jPanel2 = new javax.swing.JPanel();
         tabPane = new javax.swing.JTabbedPane();
         tabResult = new javax.swing.JLayeredPane();
         pnlText = new javax.swing.JLayeredPane();
         lblGameInfo = new javax.swing.JLabel();
         lblMatchInfo = new javax.swing.JLabel();
         lblAdditionalInfo = new javax.swing.JLabel();
         lblResultImage = new javax.swing.JLabel();
         tabStatistics = new javax.swing.JPanel();
         lblDurationGame = new javax.swing.JLabel();
         txtDurationGame = new javax.swing.JLabel();
         lblLife = new javax.swing.JLabel();
         txtLife = new javax.swing.JLabel();
         lblDurationMatch = new javax.swing.JLabel();
         txtDurationMatch = new javax.swing.JLabel();
         lblMatchScore = new javax.swing.JLabel();
         txtMatchScore = new javax.swing.JLabel();
         lblPlayerInfo = new javax.swing.JLabel();
         txtPlayerInfo = new javax.swing.JLabel();
         btnOk = new javax.swing.JButton();

         javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
         jPanel2.setLayout(jPanel2Layout);
         jPanel2Layout.setHorizontalGroup(
                 jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addGap(0, 100, Short.MAX_VALUE)
         );
         jPanel2Layout.setVerticalGroup(
                 jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addGap(0, 100, Short.MAX_VALUE)
         );

         setTitle("Game end information");

         pnlText.setBackground(new java.awt.Color(200, 100, 100));
         pnlText.setBorder(javax.swing.BorderFactory.createEtchedBorder());
         pnlText.setOpaque(true);

         lblGameInfo.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
         lblGameInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
         lblGameInfo.setText("gameInfo");
         pnlText.add(lblGameInfo);
         lblGameInfo.setBounds(11, 1, 550, 25);

         lblMatchInfo.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
         lblMatchInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
         lblMatchInfo.setText("matchInfo");
         pnlText.add(lblMatchInfo);
         lblMatchInfo.setBounds(10, 30, 550, 25);

         lblAdditionalInfo.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
         lblAdditionalInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
         lblAdditionalInfo.setText("additionalInfo");
         pnlText.add(lblAdditionalInfo);
         lblAdditionalInfo.setBounds(10, 60, 550, 25);

         tabResult.add(pnlText);
         pnlText.setBounds(20, 150, 570, 90);

         lblResultImage.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
         lblResultImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
         tabResult.add(lblResultImage);
         lblResultImage.setBounds(0, 0, 610, 250);

         tabPane.addTab("Result", tabResult);

         lblDurationGame.setText("Duration game:");

         txtDurationGame.setText("Duration Game");
         txtDurationGame.setBorder(javax.swing.BorderFactory.createEtchedBorder());

         lblLife.setText("Life at end:");

         txtLife.setBorder(javax.swing.BorderFactory.createEtchedBorder());

         lblDurationMatch.setText("Duration match:");

         txtDurationMatch.setBorder(javax.swing.BorderFactory.createEtchedBorder());

         lblMatchScore.setText("Match score:");

         txtMatchScore.setBorder(javax.swing.BorderFactory.createEtchedBorder());

         lblPlayerInfo.setText("Player info:");

         txtPlayerInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

         javax.swing.GroupLayout tabStatisticsLayout = new javax.swing.GroupLayout(tabStatistics);
         tabStatistics.setLayout(tabStatisticsLayout);
         tabStatisticsLayout.setHorizontalGroup(
                 tabStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addGroup(tabStatisticsLayout.createSequentialGroup()
                                 .addContainerGap()
                                 .addGroup(tabStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                         .addComponent(lblPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                         .addComponent(lblDurationMatch, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                         .addComponent(lblMatchScore, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                         .addComponent(lblDurationGame, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                         .addComponent(lblLife, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addGroup(tabStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                         .addComponent(txtPlayerInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                         .addComponent(txtDurationGame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                                         .addComponent(txtLife, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                                         .addComponent(txtDurationMatch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                                         .addComponent(txtMatchScore, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE))
                                 .addContainerGap())
         );
         tabStatisticsLayout.setVerticalGroup(
                 tabStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addGroup(tabStatisticsLayout.createSequentialGroup()
                                 .addGroup(tabStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                         .addComponent(lblDurationGame, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                         .addComponent(txtDurationGame, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addGroup(tabStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                         .addComponent(lblLife, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                         .addComponent(txtLife, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addGroup(tabStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                         .addComponent(lblDurationMatch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                         .addComponent(txtDurationMatch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addGroup(tabStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                         .addComponent(lblMatchScore, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                         .addComponent(txtMatchScore, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addGroup(tabStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                         .addComponent(lblPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                         .addComponent(txtPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                 .addContainerGap(105, Short.MAX_VALUE))
         );

         tabPane.addTab("Statistics", tabStatistics);

         btnOk.setText("OK");
         btnOk.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 btnOkActionPerformed(evt);
             }
         });

         javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
         getContentPane().setLayout(layout);
         layout.setHorizontalGroup(
                 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                 .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                 .addComponent(btnOk)
                                 .addContainerGap())
                         .addGroup(layout.createSequentialGroup()
                                 .addComponent(tabPane, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                                 .addGap(0, 0, Short.MAX_VALUE))
         );
         layout.setVerticalGroup(
                 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addGroup(layout.createSequentialGroup()
                                 .addComponent(tabPane, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addComponent(btnOk)
                                 .addGap(0, 8, Short.MAX_VALUE))
         );

         pack();
     }// </editor-fold>//GEN-END:initComponents

     private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
         this.removeDialog();
     }//GEN-LAST:event_btnOkActionPerformed

     // Variables declaration - do not modify//GEN-BEGIN:variables
     private javax.swing.JButton btnOk;
     private javax.swing.JPanel jPanel2;
     private javax.swing.JLabel lblAdditionalInfo;
     private javax.swing.JLabel lblDurationGame;
     private javax.swing.JLabel lblDurationMatch;
     private javax.swing.JLabel lblGameInfo;
     private javax.swing.JLabel lblLife;
     private javax.swing.JLabel lblMatchInfo;
     private javax.swing.JLabel lblMatchScore;
     private javax.swing.JLabel lblPlayerInfo;
     private javax.swing.JLabel lblResultImage;
     private javax.swing.JLayeredPane pnlText;
     private javax.swing.JTabbedPane tabPane;
     private javax.swing.JLayeredPane tabResult;
     private javax.swing.JPanel tabStatistics;
     private javax.swing.JLabel txtDurationGame;
     private javax.swing.JLabel txtDurationMatch;
     private javax.swing.JLabel txtLife;
     private javax.swing.JLabel txtMatchScore;
     private javax.swing.JLabel txtPlayerInfo;
     // End of variables declaration//GEN-END:variables

 }
