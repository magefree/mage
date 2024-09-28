package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author spjspj
 */
public final class EyeOfSingularity extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a permanent other than a basic land");

    static {
        filter.add(Predicates.not(Predicates.or(
                SuperType.BASIC.getPredicate(),
                CardType.LAND.getPredicate()
        )));
    }

    public EyeOfSingularity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.supertype.add(SuperType.WORLD);

        // When Eye of Singularity enters the battlefield, destroy each permanent with the same name as another permanent, except for basic lands. They can't be regenerated.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EyeOfSingularityETBEffect()));

        // Whenever a permanent other than a basic land enters the battlefield, destroy all other permanents with that name. They can't be regenerated.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new EyeOfSingularityTriggeredEffect(), filter));
    }

    private EyeOfSingularity(final EyeOfSingularity card) {
        super(card);
    }

    @Override
    public EyeOfSingularity copy() {
        return new EyeOfSingularity(this);
    }
}

class EyeOfSingularityETBEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.not(Predicates.or(
                SuperType.BASIC.getPredicate(),
                CardType.LAND.getPredicate()
        )));
    }

    EyeOfSingularityETBEffect() {
        super(Outcome.Benefit);
        this.staticText = "destroy each permanent with the same name as another permanent, " +
                "except for basic lands. They can't be regenerated";
    }

    private EyeOfSingularityETBEffect(final EyeOfSingularityETBEffect effect) {
        super(effect);
    }

    @Override
    public EyeOfSingularityETBEffect copy() {
        return new EyeOfSingularityETBEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> permanents = CardUtil.streamAllPairwiseMatches(
                game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game),
                (p1, p2) -> p1.sharesName(p2, game)
        ).collect(Collectors.toSet());

        for (Permanent permanent : permanents) {
            permanent.destroy(source, game, true);
        }
        return true;
    }
}

class EyeOfSingularityTriggeredEffect extends OneShotEffect {

    EyeOfSingularityTriggeredEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy all other permanents with that name. They can’t be regenerated";
    }

    private EyeOfSingularityTriggeredEffect(final EyeOfSingularityTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanent == null) {
            return false;
        }
        Set<Permanent> permanents = game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_PERMANENT, source.getControllerId(), source, game)
                .stream()
                .filter(p -> !p.equals(permanent))
                .filter(p -> p.sharesName(permanent, game))
                .collect(Collectors.toSet());
        for (Permanent p : permanents) {
            p.destroy(source, game, true);
        }
        return true;
    }

    @Override
    public EyeOfSingularityTriggeredEffect copy() {
        return new EyeOfSingularityTriggeredEffect(this);
    }
}
