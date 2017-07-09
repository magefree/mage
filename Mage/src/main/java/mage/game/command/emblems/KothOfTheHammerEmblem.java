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
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ..AS IS.. AND ANY EXPRESS OR IMPLIED
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
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.*;

import static mage.constants.Layer.AbilityAddingRemovingEffects_6;

import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author spjspj
 */
public class KothOfTheHammerEmblem extends Emblem {
    // "Mountains you control have '{T}: This land deals 1 damage to target creature or player.'"

    public KothOfTheHammerEmblem() {
        this.setName("Emblem Koth");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new KothOfTheHammerThirdEffect()));
    }
}

class KothOfTheHammerThirdEffect extends ContinuousEffectImpl {

    static final FilterLandPermanent mountains = new FilterLandPermanent("Mountain you control");

    static {
        mountains.add(new SubtypePredicate(SubType.MOUNTAIN));
        mountains.add(new ControllerPredicate(TargetController.YOU));
    }

    public KothOfTheHammerThirdEffect() {
        super(Duration.EndOfGame, Outcome.AddAbility);
        staticText = "You get an emblem with \"Mountains you control have '{T}: This land deals 1 damage to target creature or player.'\"";
    }

    public KothOfTheHammerThirdEffect(final KothOfTheHammerThirdEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case AbilityAddingRemovingEffects_6:
                if (sublayer == SubLayer.NA) {
                    for (Permanent permanent : game.getBattlefield().getActivePermanents(mountains, source.getControllerId(), source.getSourceId(), game)) {
                        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
                        ability.addTarget(new TargetCreatureOrPlayer());
                        permanent.addAbility(ability, source.getSourceId(), game);
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public KothOfTheHammerThirdEffect copy() {
        return new KothOfTheHammerThirdEffect(this);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6;
    }

}
