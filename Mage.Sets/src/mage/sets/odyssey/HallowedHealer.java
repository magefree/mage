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
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalGainActivatedAbility;
import mage.abilities.effects.common.PreventDamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author cbt33
 */
 
public class HallowedHealer extends CardImpl<HallowedHealer> {

    public HallowedHealer(UUID ownerId) {
        super(ownerId, 25, "Hallowed Healer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Prevent the next 2 damage that would be dealt to target creature or player this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageTargetEffect(Duration.EndOfTurn,2), new TapSourceCost());
        ability.addTarget(new TargetCreatureOrPlayer(true));
        this.addAbility(ability);
        // Threshold - {tap}: Prevent the next 4 damage that would be dealt to target creature or player this turn. Activate this ability only if seven or more cards are in your graveyard.
        Ability thresholdAbility = new ConditionalGainActivatedAbility(Zone.BATTLEFIELD, 
                                                                        new PreventDamageTargetEffect(Duration.EndOfTurn,4), 
                                                                        new TapSourceCost(), 
                                                                        new CardsInControllerGraveCondition(7), 
                                                                        "<i>Threshold</i> - {T}: Prevent the next 4 damage that would be dealt to target creature or player this turn. Activate this ability only if seven or more cards are in your graveyard.");
        thresholdAbility.addTarget(new TargetCreatureOrPlayer(true));
        this.addAbility(thresholdAbility);
    }

    public HallowedHealer(final HallowedHealer card) {
        super(card);
    }

    @Override
    public HallowedHealer copy() {
        return new HallowedHealer(this);
    }
}
