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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author L_J
 */
public class Rupture extends CardImpl {

    public Rupture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Sacrifice a creature. Rupture deals damage equal to that creature's power to each creature without flying and each player.
        this.getSpellAbility().addEffect(new RuptureEffect());
    }

    public Rupture(final Rupture card) {
        super(card);
    }

    @Override
    public Rupture copy() {
        return new Rupture(this);
    }
}

class RuptureEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public RuptureEffect() {
        super(Outcome.Damage);
        staticText = "Sacrifice a creature. Rupture deals damage equal to that creature's power to each creature without flying and each player";
    }

    public RuptureEffect(final RuptureEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int power = 0;
            TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent("creature to sacrifice"), true);
            if (target.canChoose(source.getSourceId(), player.getId(), game)) {
                while (!target.isChosen() && target.canChoose(player.getId(), game) && player.canRespond()) {
                    player.chooseTarget(Outcome.Sacrifice, target, source, game);
                }
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    power = permanent.getPower().getValue();
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }
            if (power > 0) {
                new DamageEverythingEffect(power, filter).apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public RuptureEffect copy() {
        return new RuptureEffect(this);
    }
}
