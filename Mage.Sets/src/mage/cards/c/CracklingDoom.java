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
package mage.cards.c;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class CracklingDoom extends CardImpl {

    public CracklingDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}{W}{B}");


        // Crackling Doom deals 2 damage to each opponent. Each opponent sacrifices a creature with the greatest power among creatures he or she controls.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(2, TargetController.OPPONENT));
        this.getSpellAbility().addEffect(new CracklingDoomEffect());

    }

    public CracklingDoom(final CracklingDoom card) {
        super(card);
    }

    @Override
    public CracklingDoom copy() {
        return new CracklingDoom(this);
    }
}

class CracklingDoomEffect extends OneShotEffect {

    public CracklingDoomEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Each opponent sacrifices a creature with the greatest power among creatures he or she controls";
    }

    public CracklingDoomEffect(final CracklingDoomEffect effect) {
        super(effect);
    }

    @Override
    public CracklingDoomEffect copy() {
        return new CracklingDoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ArrayList<Permanent> toSacrifice = new ArrayList<>();
            for (UUID playerId: game.getState().getPlayersInRange(controller.getId(), game)) {
                if (controller.hasOpponent(playerId, game)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        int greatestPower = Integer.MIN_VALUE;
                        int numberOfCreatures = 0;
                        Permanent permanentToSacrifice = null;
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), playerId, game)) {
                            if (permanent.getPower().getValue() > greatestPower) {
                                greatestPower = permanent.getPower().getValue();
                                numberOfCreatures = 1;
                                permanentToSacrifice = permanent;
                            } else if (permanent.getPower().getValue() == greatestPower) {
                                numberOfCreatures++;
                            }
                        }
                        if (numberOfCreatures == 1) {
                            if (permanentToSacrifice != null) {
                                toSacrifice.add(permanentToSacrifice);
                            }
                        } else if (greatestPower != Integer.MIN_VALUE) {
                            FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature to sacrifice with power equal to " + greatestPower);
                            filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, greatestPower));
                            Target target = new TargetControlledCreaturePermanent(filter);
                            if (opponent.choose(outcome, target, playerId, game)) {
                                Permanent permanent = game.getPermanent(target.getFirstTarget());
                                if (permanent != null) {
                                    toSacrifice.add(permanent);
                                }
                            }
                        }
                    }
                }
            }
            for (Permanent permanent :toSacrifice) {
                permanent.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }
}
