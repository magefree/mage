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
package mage.sets.innistrad;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffect;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author nantuko
 */
public class FullMoonsRise extends CardImpl<FullMoonsRise> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Werewolf creatures");

    static {
        filter.getSubtype().add("Werewolf");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public FullMoonsRise(UUID ownerId) {
        super(ownerId, 180, "Full Moon's Rise", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "ISD";

        this.color.setGreen(true);

        // Werewolf creatures you control get +1/+0 and have trample.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Constants.Duration.WhileOnBattlefield, filter)));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GainAbilityControlledEffect(TrampleAbility.getInstance(), Constants.Duration.WhileOnBattlefield, filter)));

        // Sacrifice Full Moon's Rise: Regenerate all Werewolf creatures you control.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new FullMoonsRiseEffect(filter), new SacrificeSourceCost()));
    }

    public FullMoonsRise(final FullMoonsRise card) {
        super(card);
    }

    @Override
    public FullMoonsRise copy() {
        return new FullMoonsRise(this);
    }
}

class FullMoonsRiseEffect extends OneShotEffect<FullMoonsRiseEffect> {

    private FilterPermanent filter;

    public FullMoonsRiseEffect(FilterPermanent filter) {
        super(Constants.Outcome.Regenerate);
        this.filter = filter;
        staticText = "Regenerate all Werewolf creatures you control";
    }

    public FullMoonsRiseEffect(final FullMoonsRiseEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public FullMoonsRiseEffect copy() {
        return new FullMoonsRiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId())) {
            ReplacementEffect effect = new RegenerateTargetEffect();
            effect.setTargetPointer(new FixedTarget(permanent.getId()));
            game.addEffect(effect, source);
        }
        return true;
    }

}
