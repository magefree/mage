package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTargets;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TemptedByTheOriq extends CardImpl {

    public TemptedByTheOriq(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}{U}");

        // For each opponent, gain control of up to one target creature or planeswalker that player controls with mana value 3 or less.
        this.getSpellAbility().addEffect(new TemptedByTheOriqEffect());
        this.getSpellAbility().setTargetAdjuster(TemptedByTheOriqAdjuster.instance);
    }

    private TemptedByTheOriq(final TemptedByTheOriq card) {
        super(card);
    }

    @Override
    public TemptedByTheOriq copy() {
        return new TemptedByTheOriq(this);
    }
}

enum TemptedByTheOriqAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent(
                    "creature or planeswalker " + opponent.getName() + " controls with mana value 3 or less"
            );
            filter.add(new ControllerIdPredicate(opponentId));
            filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
            ability.addTarget(new TargetPermanent(0, 1, filter, false));
        }
    }
}

class TemptedByTheOriqEffect extends OneShotEffect {

    TemptedByTheOriqEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, gain control of up to one target creature " +
                "or planeswalker that player controls with mana value 3 or less";
    }

    private TemptedByTheOriqEffect(final TemptedByTheOriqEffect effect) {
        super(effect);
    }

    @Override
    public TemptedByTheOriqEffect copy() {
        return new TemptedByTheOriqEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = source
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        game.addEffect(new GainControlTargetEffect(Duration.Custom, true)
                .setTargetPointer(new FixedTargets(permanents, game)), source);
        return true;
    }
}
