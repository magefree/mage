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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;

/**
 *
 * @author L_J
 */
public class RootwaterShaman extends CardImpl {

   private static final FilterCard filter = new FilterCard("Aura spells with enchant creature");
    static {
        filter.add(new SubtypePredicate(SubType.AURA));
        filter.add(new RootwaterShamanAbilityPredicate());
    }

    public RootwaterShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // You may cast Aura spells with enchant creature as though they had flash.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter, false)));
    }

    public RootwaterShaman(final RootwaterShaman card) {
        super(card);
    }

    @Override
    public RootwaterShaman copy() {
        return new RootwaterShaman(this);
    }

}

class RootwaterShamanAbilityPredicate implements Predicate<MageObject> {

    public RootwaterShamanAbilityPredicate() {
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        Abilities<Ability> abilities = input.getAbilities();
        for (int i = 0; i < abilities.size(); i++) {
            if (abilities.get(i) instanceof EnchantAbility) {
                String enchantText = abilities.get(i).getRule();
                if (enchantText.contentEquals("Enchant creature")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Aura card with enchant creature";
    }
}
