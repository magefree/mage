package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class CelestialJudgment extends CardImpl {

    public CelestialJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // For each different power among creatures on the battlefield, choose a creature with that power. Destroy each creature not chosen this way.
        this.getSpellAbility().addEffect(new CelestialJudgmentEffect());
    }

    private CelestialJudgment(final CelestialJudgment card) {
        super(card);
    }

    @Override
    public CelestialJudgment copy() {
        return new CelestialJudgment(this);
    }
}

class CelestialJudgmentEffect extends OneShotEffect {

    CelestialJudgmentEffect() {
        super(Outcome.Benefit);
        staticText = "for each different power among creatures on the battlefield, " +
                "choose a creature with that power. Destroy each creature not chosen this way";
    }

    private CelestialJudgmentEffect(final CelestialJudgmentEffect effect) {
        super(effect);
    }

    @Override
    public CelestialJudgmentEffect copy() {
        return new CelestialJudgmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Permanent> permanents = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE,
                        source.getControllerId(), source.getSourceId(), game
                );
        Map<Integer, List<Permanent>> powerMap = permanents
                .stream()
                .collect(Collectors.toMap(
                        permanent -> permanent.getPower().getValue(),
                        permanent -> Arrays.asList(permanent),
                        (a1, a2) -> {
                            a1.addAll(a2);
                            return a1;
                        }));
        Set<UUID> toKeep = new HashSet<>();
        for (Map.Entry<Integer, List<Permanent>> entry : powerMap.entrySet()) {
            if (entry.getValue().size() == 1) {
                toKeep.add(entry.getValue().get(0).getId());
                continue;
            }
            FilterPermanent filter = new FilterCreaturePermanent("creature with power " + entry.getKey() + " to save");
            filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, entry.getKey()));
            TargetPermanent target = new TargetPermanent(filter);
            target.setNotTarget(true);
            player.choose(outcome, target, source.getSourceId(), game);
            toKeep.add(target.getFirstTarget());
        }
        for (Permanent permanent : permanents) {
            if (!toKeep.contains(permanent.getId())) {
                permanent.destroy(source, game);
            }
        }
        return true;
    }
}
