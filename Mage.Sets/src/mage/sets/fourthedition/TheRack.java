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
package mage.sets.fourthedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class TheRack extends CardImpl<TheRack> {

    public TheRack(UUID ownerId) {
        super(ownerId, 370, "The Rack", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "4ED";

        // As The Rack enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponent()));
        // At the beginning of the chosen player's upkeep, The Rack deals X damage to that player, where X is 3 minus the number of cards in his or her hand.
        this.addAbility(new TheRackTriggeredAbility());
    }

    public TheRack(final TheRack card) {
        super(card);
    }

    @Override
    public TheRack copy() {
        return new TheRack(this);
    }
}

class TheRackTriggeredAbility extends TriggeredAbilityImpl<TheRackTriggeredAbility> {


    public TheRackTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TheRackEffect(), false);
    }

    public TheRackTriggeredAbility(final TheRackTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheRackTriggeredAbility copy() {
        return new TheRackTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
            return event.getPlayerId().equals((UUID) game.getState().getValue(new StringBuilder(this.getSourceId().toString()).append("_player").toString()));
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("At the beginning of the chosen player's upkeep, ").append(super.getRule()).toString();
    }

}

class ChooseOpponent extends OneShotEffect<ChooseOpponent> {

    public ChooseOpponent() {
        super(Outcome.Neutral);
        this.staticText = "choose an opponent";
    }

    public ChooseOpponent(final ChooseOpponent effect) {
        super(effect);
    }

    @Override
    public ChooseOpponent copy() {
        return new ChooseOpponent(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            TargetOpponent target = new TargetOpponent();
            target.setRequired(true);
            target.setNotTarget(true);
            if (player.choose(this.outcome, target, source.getSourceId(), game)) {
                Player chosenPlayer = game.getPlayer(target.getFirstTarget());
                if (chosenPlayer != null) {
                    game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + chosenPlayer.getName());
                    game.getState().setValue(permanent.getId() + "_player", target.getFirstTarget());
                    return true;
                }
            }
        }
        return false;
    }
}

class TheRackEffect extends OneShotEffect<TheRackEffect> {

    public TheRackEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals X damage to that player, where X is 3 minus the number of cards in his or her hand";
    }

    public TheRackEffect(final TheRackEffect effect) {
        super(effect);
    }

    @Override
    public TheRackEffect copy() {
        return new TheRackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = (UUID) game.getState().getValue(new StringBuilder(source.getSourceId().toString()).append("_player").toString());
        Player chosenPlayer = game.getPlayer(playerId);
        if (chosenPlayer != null) {
            int damage = 3 - chosenPlayer.getHand().size();
            if (damage > 0) {
                chosenPlayer.damage(damage, source.getSourceId(), game, false, true);
            }
        }

        return false;
    }
}
