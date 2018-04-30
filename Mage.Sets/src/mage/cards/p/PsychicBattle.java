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
package mage.cards.p;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class PsychicBattle extends CardImpl {

    public PsychicBattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // Whenever a player chooses one or more targets, each player reveals the top card of his or her library. The player who reveals the card with the highest converted mana cost may change the target or targets. If two or more cards are tied for highest cost, the target or targets remain unchanged. Changing targets this way doesn't trigger abilities of permanents named Psychic Battle.
        this.addAbility(new PsychicBattleTriggeredAbility());
    }

    public PsychicBattle(final PsychicBattle card) {
        super(card);
    }

    @Override
    public PsychicBattle copy() {
        return new PsychicBattle(this);
    }
}

class PsychicBattleTriggeredAbility extends TriggeredAbilityImpl {

    public PsychicBattleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PsychicBattleEffect(), false);
    }

    public PsychicBattleTriggeredAbility(PsychicBattleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PsychicBattleTriggeredAbility copy() {
        return new PsychicBattleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject != null) {
            if (!stackObject.isTargetChanged() && !stackObject.getName().equals("Psychic Battle")) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getSourceId()));
                stackObject.setTargetChanged(false); // resets the targetChanged flag
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player chooses one or more targets, " + super.getRule() + " Changing targets this way doesn't trigger abilities of permanents named Psychic Battle.";
    }
}

class PsychicBattleEffect extends OneShotEffect {

    public PsychicBattleEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player reveals the top card of his or her library. The player who reveals the card with the highest converted mana cost may change the target or targets. If two or more cards are tied for highest cost, the target or targets remain unchanged";
    }

    public PsychicBattleEffect(final PsychicBattleEffect effect) {
        super(effect);
    }

    @Override
    public PsychicBattleEffect copy() {
        return new PsychicBattleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null) {
            Map<Player, Integer> manacostMap = new HashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && player.getLibrary().hasCards()) {
                    Card card = player.getLibrary().getFromTop(game);
                    player.revealCards(sourceObject.getIdName() + " (" + player.getName() + ')', new CardsImpl(card), game);
                    manacostMap.put(player, card.getConvertedManaCost());
                }
            }

            Player highestCostPlayer = null;
            int maxValue = Collections.max(manacostMap.values());
            boolean tie = false;
            for (Map.Entry<Player, Integer> entry : manacostMap.entrySet()) {
                if (entry.getValue() == maxValue) {
                    if (highestCostPlayer != null) { // the result is tied, so nobody changes the targets
                        tie = true;
                        break;
                    } else {
                        highestCostPlayer = entry.getKey();
                    }
                }
            }

            if (highestCostPlayer != null && !tie) {
                StackObject stackObject = game.getStack().getStackObject(this.getTargetPointer().getFirst(game, source));
                if (stackObject != null) {
                    stackObject.setTargetChanged(true); // this makes the new target "invisible" for the Psychic Battle ability
                    stackObject.chooseNewTargets(game, highestCostPlayer.getId(), false, false, null);
                }
                return true;
            }
            return tie;
        }
        return false;
    }
}
