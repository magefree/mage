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
package mage.sets.worldwake;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public class SpellContortion extends CardImpl<SpellContortion> {

    public SpellContortion(UUID ownerId) {
        super(ownerId, 38, "Spell Contortion", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "WWK";

        this.color.setBlue(true);

        // Multikicker {1}{U}
        this.addAbility(new MultikickerAbility("{1}{U}"));
        
        // Counter target spell unless its controller pays {2}. Draw a card for each time Spell Contortion was kicked.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));
        this.getSpellAbility().addEffect(new DrawCardControllerEffect(new MultikickerCount()));
        this.getSpellAbility().addTarget(new TargetSpell());
        
    }

    public SpellContortion(final SpellContortion card) {
        super(card);
    }

    @Override
    public SpellContortion copy() {
        return new SpellContortion(this);
    }
    
    @Override
    public List<String> getRules() {
        List<String> rules = new ArrayList<String>();
        rules.add("Counter target spell unless its controller pays {2}. Draw a card for each time Spell Contortion was kicked.");
        return rules;
    }
}
