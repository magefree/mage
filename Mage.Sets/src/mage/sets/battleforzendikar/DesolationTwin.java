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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;

/**
 *
 * @author fireshoes
 */
public class DesolationTwin extends CardImpl {

    public DesolationTwin(UUID ownerId) {
        super(ownerId, 6, "Desolation Twin", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{10}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Eldrazi");
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // When you cast Desolation Twin, put a 10/10 colorless Eldrazi creature token onto the battlefield.
        this.addAbility(new DesolationTwinOnCastAbility());
    }

    public DesolationTwin(final DesolationTwin card) {
        super(card);
    }

    @Override
    public DesolationTwin copy() {
        return new DesolationTwin(this);
    }
}

class DesolationTwinOnCastAbility extends TriggeredAbilityImpl {

    DesolationTwinOnCastAbility() {
        super(Zone.STACK, new CreateTokenEffect(new EldraziToken()));
    }

    DesolationTwinOnCastAbility(DesolationTwinOnCastAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = (Spell) game.getObject(event.getTargetId());
        return this.getSourceId().equals(spell.getSourceId());
    }

    @Override
    public DesolationTwinOnCastAbility copy() {
        return new DesolationTwinOnCastAbility(this);
    }

    @Override
    public String getRule() {
        return "When you cast {this}, " + super.getRule();
    }
}

class EldraziToken extends Token {

    public EldraziToken() {
        super("Eldrazi", "10/10 colorless Eldrazi creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Eldrazi");
        power = new MageInt(10);
        toughness = new MageInt(10);
    }
}