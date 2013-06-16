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
package mage.sets.planarchaos;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class Blightspeaker extends CardImpl<Blightspeaker> {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Rebel permanent card with converted mana cost 3 or less");
    static {
        filter.add(new SubtypePredicate("Rebel"));
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, 4));
    }

    public Blightspeaker(UUID ownerId) {
        super(ownerId, 64, "Blightspeaker", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "PLC";
        this.subtype.add("Human");
        this.subtype.add("Rebel");
        this.subtype.add("Cleric");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target player loses 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetPlayer(true));
        this.addAbility(ability);
        // {4}, {tap}: Search your library for a Rebel permanent card with converted mana cost 3 or less and put it onto the battlefield. Then shuffle your library.
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false),
                new ManaCostsImpl("{4}"));
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);
    }

    public Blightspeaker(final Blightspeaker card) {
        super(card);
    }

    @Override
    public Blightspeaker copy() {
        return new Blightspeaker(this);
    }
}
