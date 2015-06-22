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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.Token;

/**
 *
 * @author LoneFox

 */
public class OpalGuardian extends CardImpl {

    public OpalGuardian(UUID ownerId) {
        super(ownerId, 30, "Opal Guardian", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{W}{W}{W}");
        this.expansionSetCode = "TSP";

        // When an opponent casts a creature spell, if Opal Guardian is an enchantment, Opal Guardian becomes a 3/4 Gargoyle creature with flying and protection from red.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new OpalGuardianGargoyle(), "", Duration.WhileOnBattlefield, true),
            new FilterCreatureSpell(), false);
        this.addAbility(new ConditionalTriggeredAbility(ability, new SourceMatchesFilterCondition(new FilterEnchantmentPermanent()),
            "When an opponent casts a creature spell, if {this} is an enchantment, {this} becomes a 3/4 Gargoyle creature with flying and protection from red."));
    }

    public OpalGuardian(final OpalGuardian card) {
        super(card);
    }

    @Override
    public OpalGuardian copy() {
        return new OpalGuardian(this);
    }
}

class OpalGuardianGargoyle extends Token {

    private static final FilterCard filter = new FilterCard("red");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public OpalGuardianGargoyle() {
        super("Gargoyle", "a 3/4 Gargoyle creature with flying and protection from red");
        cardType.add(CardType.CREATURE);
        subtype.add("Knight");
        power = new MageInt(3);
        toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new ProtectionAbility(filter));
    }
}
