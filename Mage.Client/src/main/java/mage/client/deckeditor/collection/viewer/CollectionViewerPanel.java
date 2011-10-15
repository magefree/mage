/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/
package mage.client.deckeditor.collection.viewer;

import mage.client.cards.BigCard;
import mage.client.util.sets.ConstructedFormats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pane with big card and mage book.
 *
 * @author nantuko
 */
public final class CollectionViewerPanel extends JPanel {


    public CollectionViewerPanel() {
        initComponents();
    }

    public void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel1.setOpaque(false);
        bigCard = new BigCard();
        BoxLayout boxlayout = new BoxLayout(jPanel1, BoxLayout.PAGE_AXIS);
        jPanel1.setLayout(boxlayout);
        btnExit = new javax.swing.JButton();
        btnExit.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel1.add(btnExit);

        JLabel label1 = new JLabel("Choose format:");
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel1.add(label1);

        formats = new JComboBox(ConstructedFormats.getTypes());
        formats.setSelectedItem(ConstructedFormats.getDefault());
        formats.setPreferredSize(new Dimension(100, 25));
        formats.setMaximumSize(new Dimension(100, 25));
        formats.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel1.add(formats);

        JLabel label2 = new JLabel("Choose size:");
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel1.add(label2);

        small3x3 = new JRadioButton("3x3");
        small3x3.setSelected(true);
        small3x3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                big4x4.setSelected(false);
                mageBook.updateSize("small");
            }
        });
        jPanel1.add(small3x3);

        big4x4 = new JRadioButton("4x4");
        big4x4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                small3x3.setSelected(false);
                mageBook.updateSize("big");
            }
        });
        jPanel1.add(big4x4);

        JLabel label3 = new JLabel("Switch tabs:");
        label3.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel1.add(label3);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(200, 100));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        //buttonPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel1.add(buttonPanel);

        JButton prev = new JButton("Prev");
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mageBook.prev();
            }
        });
        buttonPanel.add(prev);

        JButton next = new JButton("Next");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mageBook.next();
            }
        });
        buttonPanel.add(next);

        formats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mageBook != null) {
                    String format = (String)formats.getSelectedItem();
                    mageBook.updateDispayedSets(format);
                }
            }
        });

		jPanel1.add(Box.createVerticalGlue());

        bigCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bigCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        bigCard.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        jPanel1.add(bigCard);

        jPanel2 = new MageBookContainer();
        jPanel2.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 604, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
        );

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

    }

	private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {
		Component c = this.getParent();
		while (c != null && !(c instanceof CollectionViewerPane)) {
			c = c.getParent();
		}
		if (c != null)
			c.setVisible(false);
	}

    private final class MageBookContainer extends JPanel {
        public MageBookContainer() {
            super();
            initComponents();
        }

        public void initComponents() {
            jPanel = new JPanel();
            jScrollPane1 = new JScrollPane(jPanel);
            jScrollPane1.getViewport().setBackground(new Color(0,0,0,0));

            jPanel.setLayout(new GridBagLayout()); // centers mage book
            jPanel.setBackground(new Color(0, 0, 0, 0));
            mageBook = new MageBook(bigCard);
            jPanel.add(mageBook);

            setLayout(new java.awt.BorderLayout());
            add(jScrollPane1, java.awt.BorderLayout.CENTER);
        }

        private JPanel jPanel;
        private javax.swing.JScrollPane jScrollPane1;
    }

    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private mage.client.cards.BigCard bigCard;
	private javax.swing.JButton btnExit;
    private JComboBox formats;
    private MageBook mageBook;
    private JRadioButton small3x3;
    private JRadioButton big4x4;

}
