package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class GracefulTakedown extends CardImpl {

    public GracefulTakedown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Any number of target enchanted creatures you control and up to one other target creature you control each deal damage equal to their power to target creature you don't control.
        this.getSpellAbility().addEffect(new GracefulTakedownEffect());
        this.getSpellAbility().addTarget(new GracefulTakedownTarget());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private GracefulTakedown(final GracefulTakedown card) {
        super(card);
    }

    @Override
    public GracefulTakedown copy() {
        return new GracefulTakedown(this);
    }
}

class GracefulTakedownTarget extends TargetPermanent {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("enchanted creatures you control and up to one other creature you control");

    GracefulTakedownTarget() {
        super(0, Integer.MAX_VALUE, filter);
    }

    private GracefulTakedownTarget(final GracefulTakedownTarget target) {
        super(target);
    }

    @Override
    public GracefulTakedownTarget copy() {
        return new GracefulTakedownTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(id);
        return permanent != null
                && (EnchantedPredicate.instance.apply(permanent, game)
                || this
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .allMatch(p -> p.getId().equals(id) || EnchantedPredicate.instance.apply(p, game)));
    }
}

class GracefulTakedownEffect extends OneShotEffect {

    GracefulTakedownEffect() {
        super(Outcome.Benefit);
        staticText = "any number of target enchanted creatures you control and up to one other target creature " +
                "you control each deal damage equal to their power to target creature you don't control";
    }

    private GracefulTakedownEffect(final GracefulTakedownEffect effect) {
        super(effect);
    }

    @Override
    public GracefulTakedownEffect copy() {
        return new GracefulTakedownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = source
                .getTargets()
                .get(0)
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature == null) {
            return false;
        }
        for (Permanent permanent : permanents) {
            creature.damage(permanent.getPower().getValue(), permanent.getId(), source, game);
        }
        return true;
    }
}
