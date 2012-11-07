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

package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.cards.Card;


/**
 * 702.94. Overload
 *
 * 702.94a. Overload is a keyword that represents two static abilities: one that
 * functions from any zone in which the spell with overload can be cast and
 * another that functions while the card is on the stack. Overload [cost] means
 * "You may choose to pay [cost] rather than pay this spell's mana cost" and
 * "If you chose to pay this spell's overload cost, change its text by replacing
 * all instances of the word 'target' with the word 'each.'" Using the overload
 * ability follows the rules for paying alternative costs in rules 601.2b and 601.2e-g.
 *
 * 702.94b. If a player chooses to pay the overload cost of a spell, that spell
 * won't require any targets. It may affect objects that couldn't be chosen as
 * legal targets if the spell were cast without its overload cost being paid.
 *
 * 702.94c. Overload's second ability creates a text-changing effect. See rule
 *          612, "Text-Changing Effects."
 *
 * @author LevelX2
 *
 */

public class OverloadAbility extends SpellAbility {

    public OverloadAbility(Card card,Effect effect, ManaCosts costs) {
        super(costs, card.getName() + " with overload");
        this.addEffect(effect);
    }

    public OverloadAbility(final OverloadAbility ability) {
        super(ability);
    }

    @Override
    public OverloadAbility copy() {
        return new OverloadAbility(this);
    }

    @Override
    public String getRule() {
        return "Overload " + getManaCostsToPay().getText()+ " <i>(You may cast this spell for its overload cost. If you do, change its text by replacing all instances of \"target\" with \"each.\")</i>";
    }

}