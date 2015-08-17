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
package mage.sets.nemesis;

import java.util.UUID;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public class RefreshingRain extends CardImpl {

    private static final FilterPermanent filterForest = new FilterPermanent();
    private static final FilterPermanent filterSwamp = new FilterPermanent();

    static {
        filterForest.add(new SubtypePredicate(("Forest")));
        filterSwamp.add(new SubtypePredicate(("Swamp")));
    }

    public RefreshingRain(UUID ownerId) {
        super(ownerId, 110, "Refreshing Rain", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{G}");
        this.expansionSetCode = "NMS";

        // If an opponent controls a Swamp and you control a Forest, you may cast Refreshing Rain without paying its mana cost.
        Condition condition = new CompoundCondition("If an opponent controls a Swamp and you control a Forest",
                new OpponentControlsPermanentCondition(filterSwamp),
                new PermanentsOnTheBattlefieldCondition(filterForest));
        this.addAbility(new AlternativeCostSourceAbility(null, condition));

        // Target player gains 6 life.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public RefreshingRain(final RefreshingRain card) {
        super(card);
    }

    @Override
    public RefreshingRain copy() {
        return new RefreshingRain(this);
    }
}
