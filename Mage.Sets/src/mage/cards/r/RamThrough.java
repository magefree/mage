package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static mage.filter.StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL;

/**
 * @author TheElk801
 */
public final class RamThrough extends CardImpl {

    public RamThrough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature you control deals damage equal to its power to target creature you don't control. If the creature you control has trample, excess damage is dealt to that creature's controller instead.
        this.getSpellAbility().addEffect(new RamThroughEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private RamThrough(final RamThrough card) {
        super(card);
    }

    @Override
    public RamThrough copy() {
        return new RamThrough(this);
    }
}

class RamThroughEffect extends OneShotEffect {

    RamThroughEffect() {
        super(Outcome.Benefit);
        this.setTargetPointer(new EachTargetPointer());
        staticText = "Target creature you control deals damage equal to its power to target creature you don't control. " +
                "If the creature you control has trample, excess damage is dealt to that creature's controller instead.";
    }

    private RamThroughEffect(final RamThroughEffect effect) {
        super(effect);
    }

    @Override
    public RamThroughEffect copy() {
        return new RamThroughEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.size() < 2) {
            return false;
        }
        Permanent permanent = permanents.get(0);
        int power = permanent.getPower().getValue();
        if (power < 1) {
            return false;
        }
        Permanent creature = permanents.get(1);
        if (!permanent.hasAbility(TrampleAbility.getInstance(), game)) {
            return creature.damage(power, permanent.getId(), source, game) > 0;
        }
        int excess = creature.damageWithExcess(power, permanent.getId(), source, game);
        if (excess > 0) {
            Optional.ofNullable(creature)
                    .map(Controllable::getControllerId)
                    .map(game::getPlayer)
                    .ifPresent(player -> player.damage(excess, permanent.getId(), source, game));
        }
        return true;
    }
}
