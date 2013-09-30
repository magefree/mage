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
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CantBlockSourceEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;

/**
 *
 * @author cbt33
 */
public class DirtyWererat extends CardImpl<DirtyWererat> {

    public DirtyWererat(UUID ownerId) {
        super(ownerId, 130, "Dirty Wererat", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Human");
        this.subtype.add("Rat");
        this.subtype.add("Minion");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {B}, Discard a card: Regenerate Dirty Wererat.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{B}"));
        ability.addCost(new DiscardCardCost());
        // Threshold - As long as seven or more cards are in your graveyard, Dirty Wererat gets +2/+2 and can't block.
        Ability thresholdAbility = new SimpleStaticAbility(Zone.BATTLEFIELD,
                    new ConditionalContinousEffect(
                    new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                    new CardsInControllerGraveCondition(7),
                    "<br/><br/><i>Threshold</i> - If seven or more cards are in your graveyard, Dirty Wererat gets +2/+2 and can't block"
                ));
        Effect effect = new ConditionalRestrictionEffect(
                    new CantBlockSourceEffect(Duration.WhileOnBattlefield),
                    new CardsInControllerGraveCondition(7));
        effect.setText("and can't block");
        thresholdAbility.addEffect(effect);
        this.addAbility(thresholdAbility);
    }

    public DirtyWererat(final DirtyWererat card) {
        super(card);
    }

    @Override
    public DirtyWererat copy() {
        return new DirtyWererat(this);
    }
}
