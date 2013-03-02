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

package mage.watchers.common;

import mage.Constants;
import mage.Constants.WatcherScope;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.watchers.WatcherImpl;

/**
 * Reacts on various events to pair or unpair creatures on the battlefield.
 *
 * @author noxx
 */
public class SoulbondWatcher extends WatcherImpl<SoulbondWatcher> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another not paired creature you control");

    static {
        filter.add(new AnotherPredicate());
        filter.add(Predicates.not(new PairedPredicate()));
    }

    public SoulbondWatcher() {
        super("SoulbondWatcher", WatcherScope.GAME);
    }

    public SoulbondWatcher(final SoulbondWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getCardType().contains(Constants.CardType.CREATURE)) {
                if (permanent.getAbilities().contains(SoulbondAbility.getInstance())) {
                    Player controller = game.getPlayer(permanent.getControllerId());
                    if (controller != null) {
                        Cards cards = new CardsImpl(Constants.Zone.PICK);
                        cards.add(permanent);
                        controller.lookAtCards("Soulbond", cards, game);
                        if (controller.chooseUse(Constants.Outcome.Benefit, "Use Soulbond?", game)) {
                            TargetControlledPermanent target = new TargetControlledPermanent(filter);
                            target.setNotTarget(true);
                            if (target.canChoose(permanent.getId(), controller.getId(), game)) {
                                if (controller.choose(Constants.Outcome.Benefit, target, permanent.getId(), game)) {
                                    Permanent chosen = game.getPermanent(target.getFirstTarget());
                                    if (chosen != null) {
                                        chosen.setPairedCard(permanent.getId());
                                        permanent.setPairedCard(chosen.getId());
                                    }
                                }
                            }
                        }
                    }
                }

                // if still unpaired
                if (permanent.getPairedCard() == null) {
                    // try to find creature with Soulbond and unpaired
                    Player controller = null;
                    for (Permanent chosen : game.getBattlefield().getActivePermanents(filter, permanent.getControllerId(), permanent.getId(), game)) {
                        if (!chosen.getId().equals(permanent.getId()) && chosen.getAbilities().contains(SoulbondAbility.getInstance()) && chosen.getPairedCard() == null) {
                            if (controller == null) {
                                controller = game.getPlayer(permanent.getControllerId());
                            }
                            if (controller != null) {
                                Cards cards = new CardsImpl(Constants.Zone.PICK);
                                cards.add(chosen);
                                controller.lookAtCards("Soulbond", cards, game);
                                if (controller.chooseUse(Constants.Outcome.Benefit, "Use Soulbond for recent " + permanent.getName() + "?", game)) {
                                    chosen.setPairedCard(permanent.getId());
                                    permanent.setPairedCard(chosen.getId());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public SoulbondWatcher copy() {
        return new SoulbondWatcher(this);
    }
}

class PairedPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getPairedCard() != null;
    }

    @Override
    public String toString() {
        return "Paired";
    }
}
