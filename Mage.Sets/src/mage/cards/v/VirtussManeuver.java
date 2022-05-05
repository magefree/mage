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
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.OwnerIdPredicate;
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

    private VirtussManeuver(final VirtussManeuver card) {
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
        Player controller = game.getPlayer(source.getControllerId());
        ChooseFriendsAndFoes choice = new ChooseFriendsAndFoes();
        if (controller != null && !choice.chooseFriendOrFoe(controller, source, game)) {
            return false;
        }
        Map<UUID, Card> getBackMap = new HashMap<>();
        for (Player player : choice.getFriends()) {
            if (player == null) {
                continue;
            }
            FilterCard filter = new FilterCreatureCard("creature card in your graveyard");
            filter.add(new OwnerIdPredicate(player.getId()));
            TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
            getBackMap.put(player.getId(), null);
            if (player.choose(Outcome.ReturnToHand, target, source, game)) {
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
            player.choose(Outcome.Sacrifice, target, source, game);
            perms.addAll(target.getTargets());
        }
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}
