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

package mage.view;

import mage.Constants;
import mage.Constants.PhaseStep;
import mage.Constants.TurnPhase;
import mage.MageObject;
import mage.cards.Card;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.GameState;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameView implements Serializable {
    private static final long serialVersionUID = 1L;

	private List<PlayerView> players = new ArrayList<PlayerView>();
	private SimpleCardsView hand;
	private Map<String, SimpleCardsView> opponentHands;
	private CardsView stack = new CardsView();
    //private List<UUID> stackOrder = new ArrayList<UUID>();
	private List<ExileView> exiles = new ArrayList<ExileView>();
	private List<RevealedView> revealed = new ArrayList<RevealedView>();
	private List<LookedAtView> lookedAt = new ArrayList<LookedAtView>();
	private List<CombatGroupView> combat = new ArrayList<CombatGroupView>();
	private TurnPhase phase;
	private PhaseStep step;
	private String activePlayerName = "";
	private String priorityPlayerName = "";
	private int turn;
	private boolean special = false;

	public GameView(GameState state, Game game) {
		for (Player player: state.getPlayers().values()) {
			players.add(new PlayerView(player, state, game));
		}
		for (StackObject stackObject: state.getStack()) {
			if (stackObject instanceof StackAbility) {
				MageObject object = game.getObject(stackObject.getSourceId());
				Card card = game.getCard(stackObject.getSourceId());
				if (card != null) {
					if (object != null) {
						stack.put(stackObject.getId(), new StackAbilityView((StackAbility)stackObject, object.getName(), new CardView(card)));
                    } else {
						stack.put(stackObject.getId(), new StackAbilityView((StackAbility)stackObject, "", new CardView(card)));
                    }
                    if (card.canTransform()) {
                        updateLatestCardView(game, card, stackObject.getId());
                    }
                } else if (object != null) {
                    StackAbility stackAbility = ((StackAbility)object);
                    stackAbility.newId();
					stack.put(stackObject.getId(), new CardView(stackAbility));
				}
			}
			else {
				stack.put(stackObject.getId(), new CardView((Spell)stackObject));
			}
            //stackOrder.add(stackObject.getId());
		}
        //Collections.reverse(stackOrder);
		for (ExileZone exileZone: state.getExile().getExileZones()) {
			exiles.add(new ExileView(exileZone, game));
		}
		for (String name: state.getRevealed().keySet()) {
			revealed.add(new RevealedView(name, state.getRevealed().get(name), game));
		}
		this.phase = state.getTurn().getPhaseType();
		this.step = state.getTurn().getStepType();
		this.turn = state.getTurnNum();
		if (state.getActivePlayerId() != null)
			this.activePlayerName = state.getPlayer(state.getActivePlayerId()).getName();
		else
			this.activePlayerName = "";
		if (state.getPriorityPlayerId() != null)
			this.priorityPlayerName = state.getPlayer(state.getPriorityPlayerId()).getName();
		else
			this.priorityPlayerName = "";
		for (CombatGroup combatGroup: state.getCombat().getGroups()) {
			combat.add(new CombatGroupView(combatGroup, game));
		}
		this.special = state.getSpecialActions().getControlledBy(state.getPriorityPlayerId()).size() > 0;
	}

    private void updateLatestCardView(Game game, Card card, UUID stackId) {
        if (!card.canTransform()) {
            return;
        }
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            permanent = (Permanent)game.getLastKnownInformation(card.getId(), Constants.Zone.BATTLEFIELD);
        }
        if (permanent != null) {
            if (permanent.isTransformed()) {
                StackAbilityView stackAbilityView = (StackAbilityView) stack.get(stackId);
                stackAbilityView.getSourceCard().setTransformed(true);
            }
        }
    }

	public List<PlayerView> getPlayers() {
		return players;
	}

	public SimpleCardsView getHand() {
		return hand;
	}

	public void setHand(SimpleCardsView hand) {
		this.hand = hand;
	}

	public Map<String, SimpleCardsView> getOpponentHands() {
		return opponentHands;
	}

	public void setOpponentHands(Map<String, SimpleCardsView> opponentHands) {
		this.opponentHands = opponentHands;
	}

	public TurnPhase getPhase() {
		return phase;
	}

	public PhaseStep getStep() {
		return step;
	}

	public CardsView getStack() {
		return stack;
	}

	public List<ExileView> getExile() {
		return exiles;
	}

	public List<RevealedView> getRevealed() {
		return revealed;
	}

	public List<LookedAtView> getLookedAt() {
		return lookedAt;
	}

	public void setLookedAt(List<LookedAtView> list) {
		this.lookedAt = list;
	}

	public List<CombatGroupView> getCombat() {
		return combat;
	}

	public int getTurn() {
		return this.turn;
	}

	public String getActivePlayerName() {
		return activePlayerName;
	}

	public String getPriorityPlayerName() {
		return priorityPlayerName;
	}

	public boolean getSpecial() {
		return special;
	}

    /*public List<UUID> getStackOrder() {
        return stackOrder;
    }*/
}
