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
package mage.sets.mirage;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth

 */
public class SpiritOfTheNight extends CardImpl<SpiritOfTheNight> {
    
    private static final FilterCard filter = new FilterCard("Black");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }
    
    String rule = "Spirit of the Night has first strike as long as it's attacking";

    public SpiritOfTheNight(UUID ownerId) {
        super(ownerId, 44, "Spirit of the Night", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{6}{B}{B}{B}");
        this.expansionSetCode = "MIR";
        this.supertype.add("Legendary");
        this.subtype.add("Demon");
        this.subtype.add("Spirit");

        this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // protection from black
        this.addAbility(new ProtectionAbility(filter));
        
        // Spirit of the Night has first strike as long as it's attacking.
        ConditionalContinousEffect effect = new ConditionalContinousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()), new SpiritOfTheNightCondition(), rule);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, effect));
    }

    public SpiritOfTheNight(final SpiritOfTheNight card) {
        super(card);
    }

    @Override
    public SpiritOfTheNight copy() {
        return new SpiritOfTheNight(this);
    }
}

class SpiritOfTheNightCondition implements Condition {
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent spiritOfTheNight = game.getPermanent(source.getSourceId());
        if (spiritOfTheNight.isAttacking()) {
            return true;
        }
        return false;
    }
}
