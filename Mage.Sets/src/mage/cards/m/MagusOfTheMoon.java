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
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class MagusOfTheMoon extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
    }

    public MagusOfTheMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Nonbasic lands are Mountains.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MagusOfTheMoonEffect()));
    }

    public MagusOfTheMoon(final MagusOfTheMoon card) {
        super(card);
    }

    @Override
    public MagusOfTheMoon copy() {
        return new MagusOfTheMoon(this);
    }

    static class MagusOfTheMoonEffect extends ContinuousEffectImpl {

        MagusOfTheMoonEffect() {
            super(Duration.WhileOnBattlefield, Outcome.Detriment);
            this.staticText = "Nonbasic lands are Mountains";
            dependencyTypes.add(DependencyType.BecomeMountain);
        }

        MagusOfTheMoonEffect(final MagusOfTheMoonEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        @Override
        public MagusOfTheMoonEffect copy() {
            return new MagusOfTheMoonEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            for (Permanent land : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
                        // So the ability removing has to be done before Layer 6
                        land.removeAllAbilities(source.getSourceId(), game);
                        land.getSubtype(game).removeAll(SubType.getLandTypes(false));
                        land.getSubtype(game).add("Mountain");
                        break;
                    case AbilityAddingRemovingEffects_6:
                        land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                        break;
                }
            }
            return true;
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
        }
    }

}
