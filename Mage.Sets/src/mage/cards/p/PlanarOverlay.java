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
package mage.cards.p;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Styxo
 */
public class PlanarOverlay extends CardImpl {

    public PlanarOverlay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Each player chooses a land he or she controls of each basic land type. Return those lands to their owners' hands.
        this.getSpellAbility().addEffect(new PlanarOverlayEffect());
    }

    public PlanarOverlay(final PlanarOverlay card) {
        super(card);
    }

    @Override
    public PlanarOverlay copy() {
        return new PlanarOverlay(this);
    }
}

class PlanarOverlayEffect extends OneShotEffect {

    public PlanarOverlayEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Each player chooses a land he or she controls of each basic land type. Return those lands to their owners' hands";
    }

    public PlanarOverlayEffect(final PlanarOverlayEffect effect) {
        super(effect);
    }

    @Override
    public PlanarOverlayEffect copy() {
        return new PlanarOverlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> lands = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (String landName : SubType.getBasicLands(false)) {
                        FilterLandPermanent filter = new FilterLandPermanent(landName + " to return to hand");
                        filter.add(new SubtypePredicate(SubType.byDescription(landName)));
                        filter.add(new ControllerPredicate(TargetController.YOU));
                        Target target = new TargetLandPermanent(1, 1, filter, true);
                        if (target.canChoose(source.getSourceId(), player.getId(), game)) {
                            player.chooseTarget(outcome, target, source, game);
                            lands.add(game.getPermanent(target.getFirstTarget()));
                        }
                    }
                }
            }
            controller.moveCards(lands, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
