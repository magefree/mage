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

package mage.client.game;


import java.awt.*;
import javax.swing.*;
import mage.client.components.MageTextArea;

/**
 * Panel with buttons that copy the state of feedback panel.
 *
 * @author ayrat
 */
public class HelperPanel extends JPanel {

    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnRight;
    private javax.swing.JButton btnSpecial;
    //private javax.swing.JButton btnEndTurn;
    //private javax.swing.JButton btnStopTimer;

    private MageTextArea textArea;
    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JButton linkLeft;
    private javax.swing.JButton linkRight;
    private javax.swing.JButton linkSpecial;

    public HelperPanel() {
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(0, 0, 0, 100));
        //setLayout(new GridBagLayout());
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setOpaque(false);

        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(100, 30));
        container.setLayout(new GridBagLayout());
        container.setOpaque(false);

        JPanel jPanel = new JPanel();

        textArea = new MageTextArea();
        textArea.setText("<Empty>");

        jPanel.setOpaque(false);
        jPanel.setBackground(new Color(0, 0, 0, 80));
        jPanel.add(textArea);
        add(jPanel);

        add(container);
        
        btnSpecial = new JButton("Special");
        btnSpecial.setVisible(false);
        container.add(btnSpecial);
        btnLeft = new JButton("OK");
        btnLeft.setVisible(false);
        container.add(btnLeft);
        btnRight = new JButton("Cancel");
        btnRight.setVisible(false);
        container.add(btnRight);

        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (linkLeft != null) {{
                    setState("",false,"",false);
                    setSpecial("", false);
                    linkLeft.doClick();
                }}
            }
        });

        btnRight.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (linkRight != null) {{
                    setState("",false,"",false);
                    setSpecial("", false);
                    linkRight.doClick();
                }}
            }
        });

        btnSpecial.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (linkSpecial != null) {{
                    setState("",false,"",false);
                    setSpecial("", false);
                    linkSpecial.doClick();
                }}
            }
        });
    }

    public void setState(String txtLeft, boolean leftVisible, String txtRight, boolean rightVisible) {
        this.btnLeft.setVisible(leftVisible);
        if (!txtLeft.isEmpty()) {
            this.btnLeft.setText(txtLeft);
        }
        this.btnRight.setVisible(rightVisible);
        if (!txtRight.isEmpty()) {
            this.btnRight.setText(txtRight);
        }
    }

    public void setSpecial(String txtSpecial, boolean specialVisible) {
        this.btnSpecial.setVisible(specialVisible);
        this.btnSpecial.setText(txtSpecial);
    }

    public void setRight(String txtRight, boolean rightVisible) {
        this.btnRight.setVisible(rightVisible);
        if (!txtRight.isEmpty()) {
            this.btnRight.setText(txtRight);
        }
    }

    public void setLinks(JButton left, JButton right, JButton special) {
        this.linkLeft = left;
        this.linkRight = right;
        this.linkSpecial = special;
    }
    
    public void setMessage(String message) {
        if (message.startsWith("Use alternative cost")) {
            textArea.setText("Use alternative cost?");
        } else if (message.length() > 30 && message.contains("Use")) {
            textArea.setText("Use ability?");
        } else {
            textArea.setText(message);
        }
    }

    @Override
    public void requestFocus() {
        this.btnRight.requestFocus();
    }
}
