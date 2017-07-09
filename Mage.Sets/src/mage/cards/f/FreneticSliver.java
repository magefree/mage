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
package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldOwnerNextEndStepSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author anonymous
 */
public class FreneticSliver extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SLIVER, "All Slivers");

    public FreneticSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{R}");
        this.subtype.add("Sliver");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Slivers have "{0}: If this permanent is on the battlefield, flip a coin. If you win the flip, exile this permanent and return it to the battlefield under its owner's control at the beginning of the next end step. If you lose the flip, sacrifice it."
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new FreneticSliverEffect(), new ManaCostsImpl("{0}"), SourceOnBattlefieldCondition.instance, "{0}: If this permanent is on the battlefield, flip a coin. If you win the flip, exile this permanent and return it to the battlefield under its owner's control at the beginning of the next end step. If you lose the flip, sacrifice it.");

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(ability, Duration.WhileOnBattlefield, filter, "All Slivers have \"{0}: If this permanent is on the battlefield, flip a coin. If you win the flip, exile this permanent and return it to the battlefield under its owner's control at the beginning of the next end step. If you lose the flip, sacrifice it.\"")));
    }

    public FreneticSliver(final FreneticSliver card) {
        super(card);
    }

    @Override
    public FreneticSliver copy() {
        return new FreneticSliver(this);
    }
}

class FreneticSliverEffect extends OneShotEffect {

    public FreneticSliverEffect() {
        super(Outcome.Neutral);
        staticText = "Flip a coin. If you win the flip, exile this permanent and return it to the battlefield under its owner's control at the beginning of the next end step. If you lose the flip, sacrifice it";
    }

    public FreneticSliverEffect(final FreneticSliverEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.flipCoin(game)) {
                return new ExileReturnBattlefieldOwnerNextEndStepSourceEffect(true).apply(game, source);
            } else {
                Permanent perm = game.getPermanent(source.getSourceId());
                if (perm != null) {
                    perm.sacrifice(source.getSourceId(), game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public FreneticSliverEffect copy() {
        return new FreneticSliverEffect(this);
    }
}
