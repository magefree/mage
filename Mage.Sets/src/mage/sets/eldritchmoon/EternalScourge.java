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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public class EternalScourge extends CardImpl {

    public EternalScourge(UUID ownerId) {
        super(ownerId, 7, "Eternal Scourge", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Eldrazi");
        this.subtype.add("Horror");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // You may cast Eternal Scourge from exile.
        this.addAbility(new SimpleStaticAbility(Zone.EXILED, new EternalScourgePlayEffect()));

        // When Eternal Scourge becomes the target of a spell or ability an opponent controls, exile Eternal Scourge.
        this.addAbility(new EternalScourgeAbility());
    }

    public EternalScourge(final EternalScourge card) {
        super(card);
    }

    @Override
    public EternalScourge copy() {
        return new EternalScourge(this);
    }
}

class EternalScourgePlayEffect extends AsThoughEffectImpl {

    public EternalScourgePlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from exile";
    }

    public EternalScourgePlayEffect(final EternalScourgePlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public EternalScourgePlayEffect copy() {
        return new EternalScourgePlayEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && card.getOwnerId().equals(source.getControllerId()) && game.getState().getZone(source.getSourceId()) == Zone.EXILED) {
                return true;
            }
        }
        return false;
    }
}
class EternalScourgeAbility extends TriggeredAbilityImpl {

    public EternalScourgeAbility() {
        super(Zone.BATTLEFIELD, new ExileSourceEffect(), false);
    }

    public EternalScourgeAbility(final EternalScourgeAbility ability) {
        super(ability);
    }

    @Override
    public EternalScourgeAbility copy() {
        return new EternalScourgeAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId()) && game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes the target of a spell or ability an opponent controls, exile {this}.";
    }
}
