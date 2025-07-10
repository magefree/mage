package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class PlanetaryAnnihilation extends CardImpl {

    public PlanetaryAnnihilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Each player chooses six lands they control, then sacrifices the rest. Planetary Annihilation deals 6 damage to each creature.
        this.getSpellAbility().addEffect(new PlanetaryAnnihilationEffect());
        this.getSpellAbility().addEffect(new DamageAllEffect(6, StaticFilters.FILTER_PERMANENT_CREATURE));
    }

    private PlanetaryAnnihilation(final PlanetaryAnnihilation card) {
        super(card);
    }

    @Override
    public PlanetaryAnnihilation copy() {
        return new PlanetaryAnnihilation(this);
    }
}

class PlanetaryAnnihilationEffect extends OneShotEffect {

    PlanetaryAnnihilationEffect() {
        super(Outcome.Benefit);
        staticText = "each player chooses six lands they control, then sacrifices the rest";
    }

    private PlanetaryAnnihilationEffect(final PlanetaryAnnihilationEffect effect) {
        super(effect);
    }

    @Override
    public PlanetaryAnnihilationEffect copy() {
        return new PlanetaryAnnihilationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> toSave = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null || game.getBattlefield().count(
                    StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS, playerId, source, game
            ) <= 6) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(
                    6, 6, StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS, true
            );
            target.withChooseHint("you will sacrifice the rest");
            player.choose(outcome, target, source, game);
            toSave.addAll(target
                    .getTargets()
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()));
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_LAND, source.getControllerId(), source, game
        )) {
            if (!toSave.contains(permanent)) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}
