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
package mage.sets.alliances;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author emerald000
 */
public class Dystopia extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("green or white permanent");
    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.GREEN), new ColorPredicate(ObjectColor.WHITE)));
    }

    public Dystopia(UUID ownerId) {
        super(ownerId, 6, "Dystopia", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");
        this.expansionSetCode = "ALL";

        // Cumulative upkeep-Pay 1 life.
        this.addAbility(new CumulativeUpkeepAbility(new PayLifeCost(1)));
        
        // At the beginning of each player's upkeep, that player sacrifices a green or white permanent.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeEffect(filter, 1, "that player"), TargetController.ANY, false));
    }

    public Dystopia(final Dystopia card) {
        super(card);
    }

    @Override
    public Dystopia copy() {
        return new Dystopia(this);
    }
}
