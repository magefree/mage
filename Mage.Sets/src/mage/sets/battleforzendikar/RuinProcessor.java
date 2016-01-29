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
import mage.abilities.costs.common.ExileOpponentsCardFromExileToGraveyardCost;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

/**
 *
 * @author LevelX2
 */
public class RuinProcessor extends CardImpl {

    public RuinProcessor(UUID ownerId) {
        super(ownerId, 12, "Ruin Processor", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{7}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Eldrazi");
        this.subtype.add("Processor");
        this.power = new MageInt(7);
        this.toughness = new MageInt(8);

        // When you cast Ruin Processor, you may put a card an opponent owns from exile into that player's graveyard. If you do, you gain 5 life.
        this.addAbility(new CastSourceTriggeredAbility(
                new DoIfCostPaid(new GainLifeEffect(5), new ExileOpponentsCardFromExileToGraveyardCost(true)), false));

    }

    public RuinProcessor(final RuinProcessor card) {
        super(card);
    }

    @Override
    public RuinProcessor copy() {
        return new RuinProcessor(this);
    }
}
