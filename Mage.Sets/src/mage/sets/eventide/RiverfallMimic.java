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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.UnblockableAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth

 */
public class RiverfallMimic extends CardImpl<RiverfallMimic> {
    
    private static final FilterSpell filter = new FilterSpell("a spell that's both blue and red");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(new ColorPredicate(ObjectColor.RED));
    }
    
    private String rule = "Whenever you cast a spell that's both blue and red, {this} becomes 3/3 and is unblockable until end of turn.";

    public RiverfallMimic(UUID ownerId) {
        super(ownerId, 111, "Riverfall Mimic", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U/R}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Shapeshifter");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a spell that's both blue and red, Riverfall Mimic becomes 3/3 and is unblockable until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new SetPowerToughnessSourceEffect(3, 3, Duration.EndOfTurn), filter, false, rule);
        ability.addEffect(new GainAbilitySourceEffect(new UnblockableAbility(), Duration.EndOfTurn, false, true));
        this.addAbility(ability);
    }

    public RiverfallMimic(final RiverfallMimic card) {
        super(card);
    }

    @Override
    public RiverfallMimic copy() {
        return new RiverfallMimic(this);
    }
}
