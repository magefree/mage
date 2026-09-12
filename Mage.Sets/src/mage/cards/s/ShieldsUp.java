package mage.cards.s;

import java.util.Optional;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author muz
 */
public final class ShieldsUp extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact or creature you control");

    static {
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.ARTIFACT.getPredicate()
        ));
    }

    public ShieldsUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");


        // Target artifact or creature you control gains hexproof and indestructible until end of turn. If it's a creature, put a +1/+1 counter on it.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
            .setText("target artifact or creature you control gains hexproof"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
            .setText("and indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledPermanent(filter));
        this.getSpellAbility().addEffect(new ShieldsUpEffect());
    }

    private ShieldsUp(final ShieldsUp card) {
        super(card);
    }

    @Override
    public ShieldsUp copy() {
        return new ShieldsUp(this);
    }
}

class ShieldsUpEffect extends OneShotEffect {

    ShieldsUpEffect() {
        super(Outcome.Benefit);
        staticText = "If it's a creature, put a +1/+1 counter on it";
    }

    private ShieldsUpEffect(final ShieldsUpEffect effect) {
        super(effect);
    }

    @Override
    public ShieldsUpEffect copy() {
        return new ShieldsUpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .filter(permanent -> permanent.isCreature())
                .filter(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(), source, game))
                .isPresent();
    }
}
