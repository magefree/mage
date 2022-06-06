package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.SubTypeAssignment;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StickTogether extends CardImpl {

    public StickTogether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Each player choose a party from among creatures they control, then sacrifices the rest.
        this.getSpellAbility().addEffect(new StickTogetherEffect());
    }

    private StickTogether(final StickTogether card) {
        super(card);
    }

    @Override
    public StickTogether copy() {
        return new StickTogether(this);
    }
}

class StickTogetherEffect extends OneShotEffect {

    StickTogetherEffect() {
        super(Outcome.Benefit);
        staticText = "each player choose a party from among creatures they control, then sacrifices the rest";
    }

    private StickTogetherEffect(final StickTogetherEffect effect) {
        super(effect);
    }

    @Override
    public StickTogetherEffect copy() {
        return new StickTogetherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> toKeep = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null) {
                continue;
            }
            TargetPermanent target = new StickTogetherTarget();
            player.choose(outcome, target, source, game);
            toKeep.addAll(target.getTargets());
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (!toKeep.contains(creature.getId())) {
                creature.sacrifice(source, game);
            }
        }
        return true;
    }
}

class StickTogetherTarget extends TargetPermanent {

    private static final SubTypeAssignment subTypeAssigner = new SubTypeAssignment(
            SubType.CLERIC,
            SubType.ROGUE,
            SubType.WARRIOR,
            SubType.WIZARD
    );
    private static final FilterPermanent filter = new FilterControlledPermanent("a party");

    static {
        filter.add(Predicates.or(
                SubType.CLERIC.getPredicate(),
                SubType.ROGUE.getPredicate(),
                SubType.WARRIOR.getPredicate(),
                SubType.WIZARD.getPredicate()
        ));
    }

    StickTogetherTarget() {
        super(0, 4, filter, true);
    }

    private StickTogetherTarget(final StickTogetherTarget target) {
        super(target);
    }

    @Override
    public StickTogetherTarget copy() {
        return new StickTogetherTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability ability, Game game) {
        if (!super.canTarget(playerId, id, ability, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(id);
        if (permanent == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(permanent);
        return subTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }


    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        possibleTargets.removeIf(uuid -> !this.canTarget(sourceControllerId, uuid, null, game));
        return possibleTargets;
    }

    static int checkTargetCount(Ability source, Game game) {
        List<Permanent> permanents = game
                .getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game);
        return subTypeAssigner.getRoleCount(new CardsImpl(permanents), game);
    }
}
