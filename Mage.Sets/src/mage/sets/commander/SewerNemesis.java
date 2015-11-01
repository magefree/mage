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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChoosePlayerEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class SewerNemesis extends CardImpl {

    public SewerNemesis(UUID ownerId) {
        super(ownerId, 98, "Sewer Nemesis", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "CMD";
        this.subtype.add("Horror");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Sewer Nemesis enters the battlefield, choose a player.
        this.addAbility(new AsEntersBattlefieldAbility(new ChoosePlayerEffect(Outcome.Detriment)));
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

class CardsInTargetOpponentsGraveyardCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
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

class SewerNemesisTriggeredAbility extends TriggeredAbilityImpl {

    public SewerNemesisTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PutTopCardOfLibraryIntoGraveTargetEffect(1), false);
    }

    public SewerNemesisTriggeredAbility(final SewerNemesisTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID playerId = (UUID) game.getState().getValue(getSourceId() + "_player");
        if (playerId.equals(event.getPlayerId())) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(playerId));
            return true;
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
