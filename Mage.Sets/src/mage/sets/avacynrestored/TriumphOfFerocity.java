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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.ControlsBiggestOrTiedCreatureCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.CardImpl;

import java.util.UUID;

/**
 * @author noxx
 */
public class TriumphOfFerocity extends CardImpl<TriumphOfFerocity> {

    private static final String ruleText = "draw a card if you control the creature with the greatest power or tied for the greatest power";

    public TriumphOfFerocity(UUID ownerId) {
        super(ownerId, 198, "Triumph of Ferocity", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.expansionSetCode = "AVR";

        this.color.setGreen(true);

        // At the beginning of your upkeep, draw a card if you control the creature with the greatest power or tied for the greatest power.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new DrawCardControllerEffect(1), Constants.TargetController.YOU, false);
        this.addAbility(new ConditionalTriggeredAbility(ability, ControlsBiggestOrTiedCreatureCondition.getInstance(), ruleText));
    }

    public TriumphOfFerocity(final TriumphOfFerocity card) {
        super(card);
    }

    @Override
    public TriumphOfFerocity copy() {
        return new TriumphOfFerocity(this);
    }
}
