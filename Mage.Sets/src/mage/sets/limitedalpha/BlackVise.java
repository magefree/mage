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
package mage.sets.limitedalpha;

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
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author dustinconrad
 */
public class BlackVise extends CardImpl {

    public BlackVise(UUID ownerId) {
        super(ownerId, 233, "Black Vise", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "LEA";

        // As Black Vise enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new BlackViseChooseOpponent()));
        // At the beginning of the chosen player's upkeep, Black Vise deals X damage to that player, where X is the number of cards in his or her hand minus 4.
        this.addAbility(new BlackViseTriggeredAbility());
    }

    public BlackVise(final BlackVise card) {
        super(card);
    }

    @Override
    public BlackVise copy() {
        return new BlackVise(this);
    }
}

class BlackViseChooseOpponent extends OneShotEffect {

    public BlackViseChooseOpponent() {
        super(Outcome.Neutral);
        this.staticText = "choose an opponent";
    }

    public BlackViseChooseOpponent(final BlackViseChooseOpponent effect) {
        super(effect);
    }

    @Override
    public BlackViseChooseOpponent copy() {
        return new BlackViseChooseOpponent(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            TargetOpponent target = new TargetOpponent();
            target.setNotTarget(true);
            if (player.choose(this.outcome, target, source.getSourceId(), game)) {
                Player chosenPlayer = game.getPlayer(target.getFirstTarget());
                if (chosenPlayer != null) {
                    game.informPlayers(permanent.getName() + ": " + player.getLogName() + " has chosen " + chosenPlayer.getLogName());
                    game.getState().setValue(permanent.getId() + "_player", target.getFirstTarget());
                    return true;
                }
            }
        }
        return false;
    }
}

class BlackViseTriggeredAbility extends TriggeredAbilityImpl {

    public BlackViseTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BlackViseEffect(), false);
    }

    public BlackViseTriggeredAbility(final BlackViseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BlackViseTriggeredAbility copy() {
        return new BlackViseTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(game.getState().getValue(getSourceId().toString() + "_player"));
    }

    @Override
    public String getRule() {
        return new StringBuilder("At the beginning of the chosen player's upkeep, ").append(super.getRule()).toString();
    }
}

class BlackViseEffect extends OneShotEffect {

    public BlackViseEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals X damage to that player, where X is the number of cards in his or her hand minus 4";
    }

    public BlackViseEffect(final BlackViseEffect effect) {
        super(effect);
    }

    @Override
    public BlackViseEffect copy() {
        return new BlackViseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId().toString() + "_player");
        Player chosenPlayer = game.getPlayer(playerId);
        if (chosenPlayer != null) {
            int damage = chosenPlayer.getHand().size() - 4;
            if (damage > 0) {
                chosenPlayer.damage(damage, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
