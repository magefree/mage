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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public class StratusDancer extends CardImpl {
    
    private final static FilterSpell filter = new FilterSpell("instant or sorcery spell");
    
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), 
                new CardTypePredicate(CardType.SORCERY)));
    }

    public StratusDancer(UUID ownerId) {
        super(ownerId, 80, "Stratus Dancer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Djinn");
        this.subtype.add("Monk");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Megamorph {1}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{1}{U}"), true));
        
        // When Stratus Dancer is turned face up, counter target instant or sorcery spell
        Effect effect = new CounterTargetEffect();
        effect.setText("counter target instant or sorcery spell");
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect, false);
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
        
    }

    public StratusDancer(final StratusDancer card) {
        super(card);
    }

    @Override
    public StratusDancer copy() {
        return new StratusDancer(this);
    }
}
