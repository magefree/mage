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
package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author Loki
 */
public class ConsecratedSphinx extends CardImpl {

    public ConsecratedSphinx(UUID ownerId) {
        super(ownerId, 21, "Consecrated Sphinx", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Sphinx");

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an opponent draws a card, you may draw two cards.
        this.addAbility(new ConsecratedSphinxTriggeredAbility());
    }

    public ConsecratedSphinx(final ConsecratedSphinx card) {
        super(card);
    }

    @Override
    public ConsecratedSphinx copy() {
        return new ConsecratedSphinx(this);
    }

}

class ConsecratedSphinxTriggeredAbility extends TriggeredAbilityImpl {

    ConsecratedSphinxTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(2), true);
    }

    ConsecratedSphinxTriggeredAbility(final ConsecratedSphinxTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ConsecratedSphinxTriggeredAbility copy() {
        return new ConsecratedSphinxTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getOpponents(this.getControllerId()).contains(event.getPlayerId());
    }

    @Override
    public String getRule() {
        return "Whenever an opponent draws a card, you may draw two cards.";
    }
}
