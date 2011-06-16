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

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Map.Entry;
import java.util.UUID;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import mage.client.MageFrame;
import mage.remote.Session;
import mage.client.util.gui.GuiDisplayUtil;
import mage.view.AbilityPickerView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AbilityPicker extends JPopupMenu implements PopupMenuListener {

	private Session session;
	private UUID gameId;

	public AbilityPicker() {
		this.addPopupMenuListener(this);
	}

	public void init(Session session, UUID gameId) {
		this.session = session;
		this.gameId = gameId;
	}

	public void show(AbilityPickerView choices, Point p) {
		if (p == null) return;
		this.removeAll();
		for (Entry<UUID, String> choice: choices.getChoices().entrySet()) {
			this.add(new AbilityPickerAction(choice.getKey(), choice.getValue()));
		}
		this.show(MageFrame.getDesktop(), p.x, p.y);
		GuiDisplayUtil.keepComponentInsideScreen(p.x, p.y, this);
	}

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {  }

	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {
		session.sendPlayerBoolean(gameId, false);
	}

	private class AbilityPickerAction extends AbstractAction {

		private UUID id;

		public AbilityPickerAction(UUID id, String choice) {
			this.id = id;
			putValue(Action.NAME, choice);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			session.sendPlayerUUID(gameId, id);
			setVisible(false);
		}

	}

}
