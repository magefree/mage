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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ScaleBlessing extends CardImpl {

    public ScaleBlessing(UUID ownerId) {
        super(ownerId, 35, "Scale Blessing", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{W}");
        this.expansionSetCode = "DTK";

        // Bolster 1, then put a +1/+1 counter on each creature you control with a +1/+1 counter on it. <i.(To bolster 1, choose a creature with the least toughness among creatures you control and put +1/+1 counter on it.)</i>
        Effect effect = new BolsterEffect(1);
        effect.setText("Bolster 1");
        this.getSpellAbility().addEffect(effect);

    }

    public ScaleBlessing(final ScaleBlessing card) {
        super(card);
    }

    @Override
    public ScaleBlessing copy() {
        return new ScaleBlessing(this);
    }
}

class ScaleBlessingEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new CounterPredicate(CounterType.P1P1));
    }

    public ScaleBlessingEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then put a +1/+1 counter on each creature you control with a +1/+1 counter on it. <i.(To bolster 1, choose a creature with the least toughness among creatures you control and put +1/+1 counter on it.)</i>";
    }

    public ScaleBlessingEffect(final ScaleBlessingEffect effect) {
        super(effect);
    }

    @Override
    public ScaleBlessingEffect copy() {
        return new ScaleBlessingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            for(Permanent permanent: game.getState().getBattlefield().getAllActivePermanents(filter , controller.getId(), game)) {
                permanent.addCounters(CounterType.P1P1.createInstance(), game);
                game.informPlayers(sourceObject.getName() + ": Put a +1/+1 counter on " + permanent.getLogName());
            }
        }
        return true;
    }
}
