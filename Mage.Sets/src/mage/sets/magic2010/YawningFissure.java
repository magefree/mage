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
package mage.sets.magic2010;

import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public class YawningFissure extends CardImpl<YawningFissure> {

    public YawningFissure(UUID ownerId) {
        super(ownerId, 164, "Yawning Fissure", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{4}{R}");
        this.expansionSetCode = "M10";

        this.color.setRed(true);

        // Each opponent sacrifices a land.
        this.getSpellAbility().addEffect(new YawningFissureEffect());
    }

    public YawningFissure(final YawningFissure card) {
        super(card);
    }

    @Override
    public YawningFissure copy() {
        return new YawningFissure(this);
    }
}

class YawningFissureEffect extends OneShotEffect<YawningFissureEffect> {

    public YawningFissureEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Each opponent sacrifices a land";
    }

    public YawningFissureEffect(final YawningFissureEffect effect) {
        super(effect);
    }

    @Override
    public YawningFissureEffect copy() {
        return new YawningFissureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledPermanent filter = new FilterControlledPermanent("land you control");
        filter.add(new CardTypePredicate(CardType.LAND));
        filter.setTargetController(TargetController.YOU);

        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        for (UUID opponentId : opponents) {
            Player player = game.getPlayer(opponentId);
            Target target = new TargetControlledPermanent(filter);

            if (target.canChoose(player.getId(), game)) {
                while (!target.isChosen() && target.canChoose(player.getId(), game)) {
                    player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
                }

                Permanent permanent = game.getPermanent(target.getFirstTarget());

                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }
        }
        return true;
    }
}
