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

import mage.constants.*;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class UrborgTombOfYawgmoth extends CardImpl {

    public UrborgTombOfYawgmoth(UUID ownerId) {
        super(ownerId, 165, "Urborg, Tomb of Yawgmoth", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "PLC";
        this.supertype.add("Legendary");

        // Each land is a Swamp in addition to its other land types.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(new BlackManaAbility(), Duration.WhileOnBattlefield, new FilterLandPermanent(),
                "Each land is a Swamp in addition to its other land types"));
        ability.addEffect(new AddCardSubtypeAllEffect());
        this.addAbility(ability);

    }

    public UrborgTombOfYawgmoth(final UrborgTombOfYawgmoth card) {
        super(card);
    }

    @Override
    public UrborgTombOfYawgmoth copy() {
        return new UrborgTombOfYawgmoth(this);
    }
}

class AddCardSubtypeAllEffect extends ContinuousEffectImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();
    private static final String addedSubtype = "Swamp";

    public AddCardSubtypeAllEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "";
        addDependencyType(DependencyType.BecomeSwamp);
    }

    public AddCardSubtypeAllEffect(final AddCardSubtypeAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            if (perm != null && !perm.getSubtype(game).contains(addedSubtype)) {
                perm.getSubtype(game).add(addedSubtype);
            }
        }
        return true;
    }

    @Override
    public AddCardSubtypeAllEffect copy() {
        return new AddCardSubtypeAllEffect(this);
    }

}
