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
package mage.cards.b;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class BarrinsUnmaking extends CardImpl {

    public BarrinsUnmaking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target permanent to its owner's hand if that permanent shares a color with the most common color among all permanents or a color tied for most common.
        this.getSpellAbility().addEffect(new BarrinsUnmakingEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    public BarrinsUnmaking(final BarrinsUnmaking card) {
        super(card);
    }

    @Override
    public BarrinsUnmaking copy() {
        return new BarrinsUnmaking(this);
    }
}

class BarrinsUnmakingEffect extends OneShotEffect {

    public BarrinsUnmakingEffect() {
        super(Outcome.Detriment);
        this.staticText = "Return target permanent to its owner's hand if that permanent shares a color with the most common color among all permanents or a color tied for most common.";
    }

    public BarrinsUnmakingEffect(final BarrinsUnmakingEffect effect) {
        super(effect);
    }

    @Override
    public BarrinsUnmakingEffect copy() {
        return new BarrinsUnmakingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            FilterPermanent[] colorFilters = new FilterPermanent[6];
            int i = 0;
            for (ObjectColor color : ObjectColor.getAllColors()) {
                colorFilters[i] = new FilterPermanent();
                colorFilters[i].add(new ColorPredicate(color));
                i++;
            }
            int[] colorCounts = new int[6];
            i = 0;
            for (ObjectColor color : ObjectColor.getAllColors()) {
                colorFilters[i].add(new ColorPredicate(color));
                colorCounts[i] = new PermanentsOnBattlefieldCount(colorFilters[i]).calculate(game, source, this);
                i++;
            }
            int max = 0;
            for (i = 0; i < 5; i++) {
                if (colorCounts[i] > max) {
                    max = colorCounts[i] * 1;
                }
            }
            i = 0;
            ObjectColor commonest = new ObjectColor();
            for (ObjectColor color : ObjectColor.getAllColors()) {
                if (colorCounts[i] == max) {
                    commonest.addColor(color);
                }
                i++;
            }
            ObjectColor permColor = permanent.getColor(game);
            if (permColor.shares(commonest)) {
                Effect effect = new ReturnToHandTargetEffect();
                effect.setTargetPointer(new FixedTarget(permanent.getId()));
                return effect.apply(game, source);
            }
        }
        return false;
    }
}
