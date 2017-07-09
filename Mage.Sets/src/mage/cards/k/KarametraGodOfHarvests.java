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
package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class KarametraGodOfHarvests extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Forest or Plains card");

    static {
        filter.add(Predicates.or(
                new SubtypePredicate(SubType.FOREST),
                new SubtypePredicate(SubType.PLAINS)));
    }

    public KarametraGodOfHarvests(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("God");

        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to green and white is less than seven, Karametra isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.G, ColoredManaSymbol.W), 7);
        effect.setText("As long as your devotion to green and white is less than seven, Karametra isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // Whenever you cast a creature spell, you may search your library for a Forest or Plains card, put it onto the battlefield tapped, then shuffle your library.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true), StaticFilters.FILTER_SPELL_A_CREATURE, true));
    }

    public KarametraGodOfHarvests(final KarametraGodOfHarvests card) {
        super(card);
    }

    @Override
    public KarametraGodOfHarvests copy() {
        return new KarametraGodOfHarvests(this);
    }
}
