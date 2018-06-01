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
package mage.cards.v;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChooseFriendsAndFoes;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class VirtussManeuver extends CardImpl {

    public VirtussManeuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // For each player, choose friend or foe. Each friend returns a creature card from their graveyard to their hand. Each foe sacrifices a creature they control.
        this.getSpellAbility().addEffect(new VirtussManeuverEffect());
    }

    public VirtussManeuver(final VirtussManeuver card) {
        super(card);
    }

    @Override
    public VirtussManeuver copy() {
        return new VirtussManeuver(this);
    }
}

class VirtussManeuverEffect extends OneShotEffect {

    VirtussManeuverEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each player, choose friend or foe."
                + " Each friend returns a creature card from their graveyard to their hand. "
                + "Each foe sacrifices a creature they control";
    }

    VirtussManeuverEffect(final VirtussManeuverEffect effect) {
        super(effect);
    }

    @Override
    public VirtussManeuverEffect copy() {
        return new VirtussManeuverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getSourceId());
        ChooseFriendsAndFoes choice = new ChooseFriendsAndFoes();
        if (!choice.chooseFriendOrFoe(controller, source, game)) {
            return false;
        }
        Map<UUID, Card> getBackMap = new HashMap<>();
        for (Player player : choice.getFriends()) {
            if (player == null) {
                continue;
            }
            FilterCard filter = new FilterCard("card in your graveyard");
            filter.add(new OwnerIdPredicate(player.getId()));
            TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
            getBackMap.put(player.getId(), null);
            if (player.choose(Outcome.ReturnToHand, target, source.getSourceId(), game)) {
                getBackMap.put(player.getId(), game.getCard(target.getFirstTarget()));
            }
        }
        for (Player player : choice.getFriends()) {
            if (player == null) {
                continue;
            }
            Card card = getBackMap.getOrDefault(player.getId(), null);
            if (card == null) {
                continue;
            }
            player.moveCards(card, Zone.HAND, source, game);
        }
        List<UUID> perms = new ArrayList<>();
        for (Player player : choice.getFoes()) {
            if (player == null) {
                continue;
            }
            TargetControlledPermanent target = new TargetControlledPermanent(1, 1, StaticFilters.FILTER_CONTROLLED_A_CREATURE, true);
            player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
            perms.addAll(target.getTargets());
        }
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
            }
        }
        return true;
    }
}
