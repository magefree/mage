/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

/*
 * GameEndDialog.java
 *
 * Created on Jul 31, 2013, 9:41:00 AM
 */

package mage.client.dialog;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import mage.client.MageFrame;
import mage.client.game.GamePanel;
import mage.client.util.AudioManager;
import mage.client.util.Format;
import mage.client.util.ImageHelper;
import mage.client.util.gui.BufferedImageBuilder;
import mage.view.GameEndView;
import mage.view.PlayerView;

/**
 *
 * @author LevelX2
 */
public class GameEndDialog extends MageDialog {

    private final DateFormat df = DateFormat.getDateTimeInstance();;


    /** Creates new form GameEndDialog */
    public GameEndDialog(GameEndView gameEndView) {

        initComponents();
        this.modal = true;

        pnlText.setOpaque(true);
        pnlText.setBackground(new Color(240,240,240,140));
        
        Rectangle r = new Rectangle(610, 250);
        Image image = ImageHelper.getImageFromResources(gameEndView.hasWon() ?"/game_won.jpg":"/game_lost.jpg");
        BufferedImage imageResult = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
        ImageIcon icon = new ImageIcon(imageResult);
        lblResultImage.setIcon(icon);

        this.lblResultText.setText(gameEndView.getResultMessage());

        String autoSave = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_LOG_AUTO_SAVE, "true");
        if (autoSave.equals("true")) {
            this.saveGameLog(gameEndView);
        }
        

        // game duration
        txtDurationGame.setText(Format.getDuration(gameEndView.getStartTime(), gameEndView.getEndTime()));
        txtDurationGame.setToolTipText(new StringBuilder(df.format(gameEndView.getStartTime())).append(" - ").append(df.format(gameEndView.getEndTime())).toString() );

        // match duration
        Calendar cal = Calendar.getInstance();
        txtDurationMatch.setText(Format.getDuration(gameEndView.getMatchView().getStartTime(), cal.getTime()));
        txtDurationMatch.setToolTipText(new StringBuilder(df.format(gameEndView.getMatchView().getStartTime())).append(" - ").append(df.format(cal.getTime())).toString() );

        StringBuilder sb = new StringBuilder();
        for (PlayerView player : gameEndView.getPlayers()) {
            sb.append(player.getName()).append(" Life: ").append(player.getLife()).append(" ");
        }
        this.txtLife.setText(sb.toString());

        if (gameEndView.hasWon()) {
            AudioManager.playPlayerWon();
        } else {
            AudioManager.playPlayerLost();
        }

        txtMatchScore.setText(gameEndView.getMatchView().getResult());

        if (gameEndView.getNameMatchWinner() != null) {
            if (gameEndView.getClientPlayer().getName().equals(gameEndView.getNameMatchWinner())) {
                lblMatchInfo.setText("You won the match!");
            } else {
                lblMatchInfo.setText(new StringBuilder(gameEndView.getNameMatchWinner()).append(" won the match!").toString());
            }
        } else {
            int winsNeeded = gameEndView.getWinsNeeded() - gameEndView.getWins();
            lblMatchInfo.setText(new StringBuilder("You need ").append(winsNeeded == 1 ? "one win ":winsNeeded + " wins ").append("to win the match.").toString());
        }


    }

    private void saveGameLog(GameEndView gameEndView) {
        String dir = "gamelogs";
        File saveDir = new File(dir);
        //Here comes the existence check
        if(!saveDir.exists()) {
            saveDir.mkdirs();
        }
        // get game log
        try {            
            GamePanel gamePanel = MageFrame.getGame(gameEndView.getMatchView().getGames().get(gameEndView.getMatchView().getGames().size()-1));
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern( "yyyyMMdd_HHmmss" );
            String fileName = new StringBuilder(dir).append(File.separator)
                    .append(sdf.format(gameEndView.getStartTime()))
                    .append("_").append(gameEndView.getMatchView().getGameType())
                    .append("_").append(gameEndView.getMatchView().getGames().size())
                    .append(".txt").toString();
            PrintWriter out = new PrintWriter(fileName);
            out.print(gamePanel.getGameLog());
            out.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error while writing game log to file\n\n" + ex, "Error writing gamelog", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void showDialog() {
        this.setLocation(100, 100);
        this.setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        tabPane = new javax.swing.JTabbedPane();
        tabResult = new javax.swing.JLayeredPane();
        pnlText = new javax.swing.JLayeredPane();
        lblMatchInfo = new javax.swing.JLabel();
        lblResultText = new javax.swing.JLabel();
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

        lblMatchInfo.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        lblMatchInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMatchInfo.setText("matchInfo");
        lblMatchInfo.setBounds(11, 42, 550, 40);
        pnlText.add(lblMatchInfo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblResultText.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        lblResultText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResultText.setText("result text");
        lblResultText.setBounds(11, 1, 550, 40);
        pnlText.add(lblResultText, javax.swing.JLayeredPane.DEFAULT_LAYER);

        pnlText.setBounds(20, 150, 570, 90);
        tabResult.add(pnlText, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblResultImage.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblResultImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResultImage.setBounds(0, 0, 610, 250);
        tabResult.add(lblResultImage, javax.swing.JLayeredPane.DEFAULT_LAYER);

        tabPane.addTab("Result", tabResult);

        lblDurationGame.setText("Duration game:");

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
                .addGroup(tabStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabStatisticsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(tabStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDurationMatch, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMatchScore, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(tabStatisticsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(tabStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDurationGame, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLife, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabStatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPlayerInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDurationGame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
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
                .addContainerGap(99, Short.MAX_VALUE))
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
                .addGap(0, 25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        this.removeDialog();
    }//GEN-LAST:event_btnOkActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOk;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblDurationGame;
    private javax.swing.JLabel lblDurationMatch;
    private javax.swing.JLabel lblLife;
    private javax.swing.JLabel lblMatchInfo;
    private javax.swing.JLabel lblMatchScore;
    private javax.swing.JLabel lblPlayerInfo;
    private javax.swing.JLabel lblResultImage;
    private javax.swing.JLabel lblResultText;
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
