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

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Quercitron
 */
public class FadeAway extends CardImpl {

    public FadeAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // For each creature, its controller sacrifices a permanent unless he or she pays {1}.
        this.getSpellAbility().addEffect(new FadeAwayEffect());
    }

    public FadeAway(final FadeAway card) {
        super(card);
    }

    @Override
    public FadeAway copy() {
        return new FadeAway(this);
    }
}

class FadeAwayEffect extends OneShotEffect {

    private static final FilterCreaturePermanent FILTER_CREATURE = new FilterCreaturePermanent();

    public FadeAwayEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "For each creature, its controller sacrifices a permanent unless he or she pays {1}";
    }

    public FadeAwayEffect(final FadeAwayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int creaturesNumber = game.getBattlefield().getAllActivePermanents(FILTER_CREATURE, playerId, game).size();
                if (creaturesNumber > 0) {
                    String message = "For how many creatures will you pay (up to " + creaturesNumber + ")?";
                    int payAmount = 0;
                    boolean paid = false;
                    while (!paid) {
                        payAmount = player.getAmount(0, creaturesNumber, message, game);
                        ManaCostsImpl cost = new ManaCostsImpl();
                        cost.add(new GenericManaCost(payAmount));
                        cost.clearPaid();
                        if (cost.payOrRollback(source, game, source.getSourceId(), playerId)) {
                            paid = true;
                        }
                    }

                    int sacrificeNumber = creaturesNumber - payAmount;
                    game.informPlayers(player.getLogName() + " pays {" + payAmount + "} and sacrifices "
                            + sacrificeNumber + " permanent" + (sacrificeNumber == 1 ? "" : "s"));

                    if (sacrificeNumber > 0) {
                        int permanentsNumber = game.getBattlefield().getAllActivePermanents(playerId).size();
                        int targetsNumber = Math.min(sacrificeNumber, permanentsNumber);
                        TargetControlledPermanent target = new TargetControlledPermanent(targetsNumber);
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        for (UUID permanentId : target.getTargets()) {
                            Permanent permanent = game.getPermanent(permanentId);
                            if (permanent != null) {
                                permanent.sacrifice(source.getSourceId(), game);
                            }
                        }
                    }
                } else {
                    game.informPlayers(player.getLogName() + " has no creatures");
                }
            }
        }
        return true;
    }

    @Override
    public Effect copy() {
        return new FadeAwayEffect(this);
    }
}
