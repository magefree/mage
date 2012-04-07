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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.Constants.Zone;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continious.PlayWithTheTopCardRevealedEffect;
import mage.cards.CardImpl;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.condition.common.TopLibraryCardTypeCondition;
import mage.abilities.decorator.ConditionalActivatedAbility;
import static mage.abilities.condition.common.TopLibraryCardTypeCondition.CheckType.*;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.AddManaOfAnyColorEffect;
import mage.choices.ChoiceColor;

/**
 *
 * @author jeffwadsworth
 */
public class MulDayaChannelers extends CardImpl<MulDayaChannelers> {
   
    private static final String rule1 = "As long as the top card of your library is a creature card, {this} gets +3/+3";
    private static final String rule2 = "As long as the top card of your library is a land card, {this} has T: Add two mana of any one color to your mana pool.";

    public MulDayaChannelers(UUID ownerId) {
        super(ownerId, 198, "Mul Daya Channelers", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Elf");
        this.subtype.add("Druid");
        this.subtype.add("Shaman");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
       
        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));
       
        // As long as the top card of your library is a creature card, Mul Daya Channelers gets +3/+3.
        ConditionalContinousEffect effect = new ConditionalContinousEffect(new BoostSourceEffect(3, 3, Constants.Duration.WhileOnBattlefield), new TopLibraryCardTypeCondition(CREATURE), rule1);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, effect));
       
        // As long as the top card of your library is a land card, Mul Daya Channelers has "T: Add two mana of any one color to your mana pool."
        ConditionalActivatedAbility ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost(), new TopLibraryCardTypeCondition(LAND), rule2);
        ability.addChoice(new ChoiceColor());
        this.addAbility(ability);
    }

    public MulDayaChannelers(final MulDayaChannelers card) {
        super(card);
    }

    @Override
    public MulDayaChannelers copy() {
        return new MulDayaChannelers(this);
    }
}