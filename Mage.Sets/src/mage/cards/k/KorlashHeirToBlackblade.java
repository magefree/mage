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
import mage.abilities.abilityword.GrandeurAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class KorlashHeirToBlackblade extends CardImpl {
    
    private static final FilterControlledPermanent filterPermanent = new FilterControlledPermanent("Swamps you control");
    private static final FilterCard filterCard = new FilterLandCard("Swamp cards");
    static {
        filterPermanent.add(new SubtypePredicate(SubType.SWAMP));
        filterCard.add(new SubtypePredicate(SubType.SWAMP));
    }

    public KorlashHeirToBlackblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Zombie");
        this.subtype.add("Warrior");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Korlash, Heir to Blackblade's power and toughness are each equal to the number of Swamps you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterPermanent), Duration.EndOfGame)));

        // {1}{B}: Regenerate Korlash.
        Effect effect = new RegenerateSourceEffect();
        effect.setText("Regenerate Korlash.");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{1}{B}")));
        
        // Grandeur - Discard another card named Korlash, Heir to Blackblade: Search your library for up to two Swamp cards, put them onto the battlefield tapped, then shuffle your library.
        effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, filterCard), true, true);
        effect.setText("Search your library for up to two Swamp cards, put them onto the battlefield tapped, then shuffle your library.");
        this.addAbility(new GrandeurAbility(effect, "Korlash, Heir to Blackblade"));
    }

    public KorlashHeirToBlackblade(final KorlashHeirToBlackblade card) {
        super(card);
    }

    @Override
    public KorlashHeirToBlackblade copy() {
        return new KorlashHeirToBlackblade(this);
    }
}
