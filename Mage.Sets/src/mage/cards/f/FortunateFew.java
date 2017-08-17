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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author spjspj
 */
public class FortunateFew extends CardImpl {

    public FortunateFew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Choose a nonland permanent you don't control, then each other player chooses a nonland permanent he or she doesn't control that hasn't been chosen this way. Destroy all other nonland permanents.
        this.getSpellAbility().addEffect(new FortunateFewEffect());
    }

    public FortunateFew(final FortunateFew card) {
        super(card);
    }

    @Override
    public FortunateFew copy() {
        return new FortunateFew(this);
    }
}

class FortunateFewEffect extends OneShotEffect {

    public FortunateFewEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Choose a nonland permanent you don't control, then each other player chooses a nonland permanent he or she doesn't control that hasn't been chosen this way. Destroy all other nonland permanents";
    }

    public FortunateFewEffect(FortunateFewEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<Permanent, Integer> chosenCards = new HashMap<>(2);
            int maxCount = 0;

            // Players each choose a legal permanent
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {

                    FilterNonlandPermanent filter = new FilterNonlandPermanent("a nonland permanent you don't control");
                    filter.add(Predicates.not(new ControllerIdPredicate(player.getId())));

                    for (Permanent chosenPerm : chosenCards.keySet()) {
                        filter.add(Predicates.not(new PermanentIdPredicate(chosenPerm.getId())));
                    }

                    Target target = new TargetNonlandPermanent(filter);
                    target.setNotTarget(true);
                    if (player.choose(Outcome.Exile, target, source.getSourceId(), game)) {
                        Permanent permanent = game.getPermanent(target.getFirstTarget());
                        if (permanent != null) {
                            chosenCards.put(permanent, 1);
                            game.informPlayers(player.getLogName() + " has chosen: " + permanent.getName());
                        }
                    }
                }
            }

            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterNonlandPermanent(), source.getControllerId(), source.getSourceId(), game)) {
                if (!chosenCards.containsKey(permanent)) {
                    permanent.destroy(source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public FortunateFewEffect copy() {
        return new FortunateFewEffect(this);
    }
}
