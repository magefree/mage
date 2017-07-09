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
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.effects.common.AffinityEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */

public class AffinityForLandTypeAbility extends SimpleStaticAbility implements AdjustingSourceCosts {

    private final FilterControlledPermanent filter;

    String text;
    SubType landType;

    public AffinityForLandTypeAbility(SubType landType, String text) {
        super(Zone.OUTSIDE, new AffinityEffect(getFilter(landType)));
        this.filter = getFilter(landType);
        setRuleAtTheTop(true);
        this.text = text;
        this.landType = landType;
    }

   private static FilterControlledPermanent getFilter(SubType landType) {
        FilterControlledPermanent affinityfilter = new FilterControlledPermanent();
        affinityfilter.add(new SubtypePredicate(landType));
        return affinityfilter;

    }

    public AffinityForLandTypeAbility(final AffinityForLandTypeAbility ability) {
        super(ability);
        this.text = ability.text;
        this.filter = ability.filter.copy();
    }

    @Override
    public SimpleStaticAbility copy() {
        return new AffinityForLandTypeAbility(this);
    }

    @Override
    public String getRule() {
        return "Affinity for " + text + " <i>(This spell costs 1 less to cast for each " + landType + " you control.)</i>";
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            int count = game.getBattlefield().getAllActivePermanents(filter, ability.getControllerId(), game).size();
            if (count > 0) {
                CardUtil.adjustCost((SpellAbility)ability, count);
            }
        }
    }
}
