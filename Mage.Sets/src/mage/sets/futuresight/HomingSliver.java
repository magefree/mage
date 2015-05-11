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
package mage.sets.futuresight;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author anonymous
 */
public class HomingSliver extends CardImpl {

    private static final FilterCard filter = new FilterCard("Sliver card");

    static {
        filter.add(new SubtypePredicate("Sliver"));
    }

    private static final FilterPermanent filter2 = new FilterPermanent("Sliver card");

    static {
        filter2.add(new SubtypePredicate("Sliver"));
    }

    public HomingSliver(UUID ownerId) {
        super(ownerId, 118, "Homing Sliver", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "FUT";
        this.subtype.add("Sliver");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each Sliver card in each player's hand has slivercycling {3}.
        Ability ability = new CyclingAbility(new ManaCostsImpl("{3}"), filter, "Slivercycling");
        ability.addCost(new DiscardSourceCost());
        this.addAbility(new SimpleStaticAbility(Zone.HAND, new GainAbilityAllEffect(ability, Duration.WhileOnBattlefield, filter2)));
        // Slivercycling {3}
        this.addAbility(ability);
        /**
         * 01/02/2009	Slivercycling is a form of cycling. Any ability that
         * triggers on a card being cycled also triggers on Slivercycling this
         * card. Any ability that stops a cycling ability from being activated
         * also stops Plainscycling from being activated.
         */

        /**
         * 01/02/2009 Slivercycling is an activated ability. Effects that
         * interact with activated abilities (such as Stifle or Rings of
         * Brighthearth) will interact with Slivercycling. Effects that interact
         * with spells (such as Remove Soul or Faerie Tauntings) will not.
         */
        
        /**
         * 01/02/2009	You can choose to find any card with the Sliver creature
         * type, even if it isn't a creature card. This includes, for example,
         * Tribal cards with the Changeling ability. You can also choose not to
         * find a card, even if there is a Sliver card in your graveyard.
         *
         */
    }

    public HomingSliver(final HomingSliver card) {
        super(card);
    }

    @Override
    public HomingSliver copy() {
        return new HomingSliver(this);
    }
}
