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
package mage.sets.commander;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTopCardOfTargetPlayerLibraryIntoGraveEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class SewerNemesis extends CardImpl<SewerNemesis> {

    public SewerNemesis(UUID ownerId) {
        super(ownerId, 98, "Sewer Nemesis", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "CMD";
        this.subtype.add("Horror");

        this.color.setBlack(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Sewer Nemesis enters the battlefield, choose a player.
        this.addAbility(new AsEntersBattlefieldAbility(new SewerNemesisChoosePlayerEffect()));
        // Sewer Nemesis's power and toughness are each equal to the number of cards in the chosen player's graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new CardsInTargetOpponentsGraveyardCount(), Duration.WhileOnBattlefield)));
        // Whenever the chosen player casts a spell, that player puts the top card of his or her library into his or her graveyard.
        this.addAbility(new SewerNemesisTriggeredAbility());

    }

    public SewerNemesis(final SewerNemesis card) {
        super(card);
    }

    @Override
    public SewerNemesis copy() {
        return new SewerNemesis(this);
    }
}

class SewerNemesisChoosePlayerEffect extends OneShotEffect<SewerNemesisChoosePlayerEffect> {

    public SewerNemesisChoosePlayerEffect() {
        super(Outcome.Detriment);
        this.staticText = "choose a player";
    }

    public SewerNemesisChoosePlayerEffect(final SewerNemesisChoosePlayerEffect effect) {
        super(effect);
    }

    @Override
    public SewerNemesisChoosePlayerEffect copy() {
        return new SewerNemesisChoosePlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            TargetPlayer target = new TargetPlayer();
            target.setRequired(true);
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

class CardsInTargetOpponentsGraveyardCount implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        if (sourceAbility != null) {
            UUID playerId = (UUID) game.getState().getValue(sourceAbility.getSourceId() + "_player");
            Player chosenPlayer = game.getPlayer(playerId);
            if (chosenPlayer != null) {
                return chosenPlayer.getGraveyard().size();
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new CardsInTargetOpponentsGraveyardCount();
    }

    @Override
    public String getMessage() {
        return "cards in the chosen player's graveyard";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class SewerNemesisTriggeredAbility extends TriggeredAbilityImpl<SewerNemesisTriggeredAbility> {

    public SewerNemesisTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PutTopCardOfTargetPlayerLibraryIntoGraveEffect(1), false);
    }

    public SewerNemesisTriggeredAbility(final SewerNemesisTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // chosen player casts a spell
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            UUID playerId = (UUID) game.getState().getValue(getSourceId() + "_player");
            if (playerId.equals(event.getPlayerId())) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(playerId));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever the chosen player casts a spell, that player puts the top card of his or her library into his or her graveyard.";
    }

    @Override
    public SewerNemesisTriggeredAbility copy() {
        return new SewerNemesisTriggeredAbility(this);
    }
}
