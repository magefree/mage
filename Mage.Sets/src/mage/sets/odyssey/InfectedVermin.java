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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;

/**
 *
 * @author cbt33
 */
public class InfectedVermin extends CardImpl<InfectedVermin> {

    public InfectedVermin(UUID ownerId) {
        super(ownerId, 144, "Infected Vermin", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Rat");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{B}: Infected Vermin deals 1 damage to each creature and each player.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageEverythingEffect(1), new ManaCostsImpl("{2}{B}")));
        // Threshold - {3}{B}: Infected Vermin deals 3 damage to each creature and each player. Activate this ability only if seven or more cards are in your graveyard.
        this.addAbility(new ConditionalActivatedAbility(Zone.BATTLEFIELD, 
                new DamageEverythingEffect(3),
                new ManaCostsImpl("{3}{B}"),
                new CardsInControllerGraveCondition(7),
                "<br/><br/><i>Threshold</i> - {3}{B}: Infected Vermin deals 3 damage to each creature and each player. Activate this ability only if seven or more cards are in your graveyard."));
    }

    public InfectedVermin(final InfectedVermin card) {
        super(card);
    }

    @Override
    public InfectedVermin copy() {
        return new InfectedVermin(this);
    }
}
