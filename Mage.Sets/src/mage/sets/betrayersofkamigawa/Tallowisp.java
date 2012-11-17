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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterSpiritOrArcaneCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class Tallowisp extends CardImpl<Tallowisp> {

    private static final FilterCard filterAura = new FilterCard("Aura card");
    private static final FilterSpell filterTrigger = new FilterSpiritOrArcaneCard();

    static {
        filterAura.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filterAura.add(new SubtypePredicate("Aura"));
        filterAura.add(new TallowispAbilityPredicate());
    }

    public Tallowisp(UUID ownerId) {
        super(ownerId, 25, "Tallowisp", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Spirit");
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a Spirit or Arcane spell, you may search your library for an Aura card with enchant creature, reveal it, and put it into your hand. If you do, shuffle your library.
        this.addAbility(new SpellCastTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filterAura), true, true), filterTrigger, true));
    }

    public Tallowisp(final Tallowisp card) {
        super(card);
    }

    @Override
    public Tallowisp copy() {
        return new Tallowisp(this);
    }
}

class TallowispAbilityPredicate implements Predicate<MageObject> {

    public TallowispAbilityPredicate() {
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        Abilities<Ability> abilities = input.getAbilities();
        for (int i = 0; i < abilities.size(); i++) {
            if (abilities.get(i) instanceof EnchantAbility) {
                String enchantText = abilities.get(i).getRule();
                if (enchantText.startsWith("Enchant") && enchantText.contains("creature")) {
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
