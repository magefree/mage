package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DestinedConfrontation extends CardImpl {

    public DestinedConfrontation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Each player chooses any number of creatures they control with total power 4 or less, then sacrifices all other creatures they control.
        this.getSpellAbility().addEffect(new DestinedConfrontationEffect());
    }

    private DestinedConfrontation(final DestinedConfrontation card) {
        super(card);
    }

    @Override
    public DestinedConfrontation copy() {
        return new DestinedConfrontation(this);
    }
}

class DestinedConfrontationEffect extends OneShotEffect {

    DestinedConfrontationEffect() {
        super(Outcome.Benefit);
        staticText = "each player chooses any number of creatures they control " +
                "with total power 4 or less, then sacrifices all other creatures they control";
    }

    private DestinedConfrontationEffect(final DestinedConfrontationEffect effect) {
        super(effect);
    }

    @Override
    public DestinedConfrontationEffect copy() {
        return new DestinedConfrontationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> permanents = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetPermanent target = new DestinedConfrontationTarget();
            player.choose(outcome, target, source, game);
            permanents.addAll(target.getTargets());
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getSourceId(), source, game
        )) {
            if (!permanents.contains(permanent.getId())) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}

class DestinedConfrontationTarget extends TargetPermanent {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creatures you control with total power 4 or less");

    DestinedConfrontationTarget() {
        super(0, Integer.MAX_VALUE, filter, true);
    }

    private DestinedConfrontationTarget(final DestinedConfrontationTarget target) {
        super(target);
    }

    @Override
    public DestinedConfrontationTarget copy() {
        return new DestinedConfrontationTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(id);
        if (permanent == null) {
            return false;
        }
        return this
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum() + permanent.getPower().getValue() <= 4;
    }
}
