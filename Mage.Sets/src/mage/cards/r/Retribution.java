package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetPermanentSameController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jeffwadsworth
 */
public final class Retribution extends CardImpl {

    public Retribution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Choose two target creatures an opponent controls. That player chooses and sacrifices one of those creatures. Put a -1/-1 counter on the other.
        this.getSpellAbility().addEffect(new RetributionEffect());
        this.getSpellAbility().addTarget(new TargetPermanentSameController(2, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES));
    }

    private Retribution(final Retribution card) {
        super(card);
    }

    @Override
    public Retribution copy() {
        return new Retribution(this);
    }
}

class RetributionEffect extends OneShotEffect {

    RetributionEffect() {
        super(Outcome.Detriment);
        this.staticText = "Choose two target creatures an opponent controls. That player chooses and sacrifices one of those creatures. Put a -1/-1 counter on the other";
    }

    private RetributionEffect(final RetributionEffect effect) {
        super(effect);
    }

    @Override
    public RetributionEffect copy() {
        return new RetributionEffect(this);
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
        List<Permanent> canSac = permanents
                .stream()
                .filter(Permanent::canBeSacrificed)
                .collect(Collectors.toList());
        Permanent toSacrifice;
        switch (canSac.size()) {
            case 0:
                toSacrifice = null;
                break;
            case 1:
                toSacrifice = canSac.get(0);
                break;
            default:
                toSacrifice = Optional
                        .ofNullable(canSac.get(0).getControllerId())
                        .map(game::getPlayer)
                        .map(player -> {
                            FilterPermanent filter = new FilterPermanent();
                            filter.add(new PermanentReferenceInCollectionPredicate(canSac, game));
                            TargetPermanent target = new TargetPermanent(filter);
                            target.withNotTarget(true);
                            player.choose(Outcome.Sacrifice, target, source, game);
                            return target;
                        })
                        .map(TargetImpl::getFirstTarget)
                        .map(game::getPermanent)
                        .orElse(null);
        }
        if (toSacrifice != null) {
            permanents.remove(toSacrifice);
            toSacrifice.sacrifice(source, game);
        }
        for (Permanent permanent : permanents) {
            permanent.addCounters(CounterType.M1M1.createInstance(), source, game);
        }
        return true;
    }
}
