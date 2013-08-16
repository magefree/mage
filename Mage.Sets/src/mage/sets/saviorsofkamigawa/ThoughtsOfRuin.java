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
package mage.sets.saviorsofkamigawa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public class ThoughtsOfRuin extends CardImpl<ThoughtsOfRuin> {

    public ThoughtsOfRuin(UUID ownerId) {
        super(ownerId, 118, "Thoughts of Ruin", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");
        this.expansionSetCode = "SOK";

        this.color.setRed(true);

        // Each player sacrifices a land for each card in your hand.
        this.getSpellAbility().addEffect(new ThoughtsOfRuinEffect());

    }

    public ThoughtsOfRuin(final ThoughtsOfRuin card) {
        super(card);
    }

    @Override
    public ThoughtsOfRuin copy() {
        return new ThoughtsOfRuin(this);
    }
}

class ThoughtsOfRuinEffect extends OneShotEffect<ThoughtsOfRuinEffect> {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    public ThoughtsOfRuinEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player sacrifices a land for each card in your hand";
    }

    public ThoughtsOfRuinEffect(final ThoughtsOfRuinEffect effect) {
        super(effect);
    }

    @Override
    public ThoughtsOfRuinEffect copy() {
        return new ThoughtsOfRuinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = controller.getHand().size();
            if (amount > 0) {
                List<Permanent> permanentsToSacrifice = new ArrayList<Permanent>();
                // select all lands to sacrifice
                for (UUID playerId : controller.getInRange()) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        int lands = game.getState().getBattlefield().countAll(filter, playerId, game);
                        if (amount >= lands) {
                            permanentsToSacrifice.addAll(game.getState().getBattlefield().getAllActivePermanents(filter, playerId, game));
                        } else {
                            FilterLandPermanent playerFilter = filter.copy();
                            playerFilter.add(new ControllerIdPredicate(playerId));
                            Target target = new TargetLandPermanent(amount, amount, playerFilter, true);
                            target.setRequired(true);
                            player.choose(outcome, target, source.getSourceId(), game);
                            for (UUID landId : target.getTargets()) {
                                Permanent permanent = game.getPermanent(landId);
                                if (permanent != null) {
                                    permanentsToSacrifice.add(permanent);
                                }
                            }
                        }

                    }
                }
                // sacrifice all lands
                for (Permanent permanent :permanentsToSacrifice) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }
        }
        return false;
    }
}
