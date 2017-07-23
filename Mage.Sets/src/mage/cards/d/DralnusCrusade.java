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
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author brikr
 */
public class DralnusCrusade extends CardImpl {

    public DralnusCrusade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{R}");

        // Goblin creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS, false)));

        // All Goblins are black and are Zombies in addition to their other creature types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DralnusCrusadeEffect()));
    }

    public DralnusCrusade(final DralnusCrusade card) {
        super(card);
    }

    @Override
    public DralnusCrusade copy() {
        return new DralnusCrusade(this);
    }
}

class DralnusCrusadeEffect extends ContinuousEffectImpl {

    public DralnusCrusadeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "All Goblins are black and are Zombies in addition to their other creature types";
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getState().getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS, source.getControllerId(), source.getSourceId(), game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (!permanent.hasSubtype("Zombie", game)) {
                        permanent.getSubtype(game).add("Zombie");
                    }
                    break;
                case ColorChangingEffects_5:
                    permanent.getColor(game).setColor(ObjectColor.BLACK);
                    break;
            }
        }
        return true;
    }

    public DralnusCrusadeEffect(final DralnusCrusadeEffect effect) {
        super(effect);
    }

    @Override
    public DralnusCrusadeEffect copy() {
        return new DralnusCrusadeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.ColorChangingEffects_5;
    }
}
