package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TandemTakedown extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature, planeswalker, or battle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
        filter.add(new AnotherTargetPredicate(2));
    }

    public TandemTakedown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{G}");

        // Up to two target creatures you control each get +1/+0 until end of turn. They each deal damage equal to their power to another target creature, planeswalker, or battle.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0)
                .setText("up to two target creatures you control each get +1/+0 until end of turn"));
        this.getSpellAbility().addEffect(new TandemTakedownEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(filter).setTargetTag(2));
    }

    private TandemTakedown(final TandemTakedown card) {
        super(card);
    }

    @Override
    public TandemTakedown copy() {
        return new TandemTakedown(this);
    }
}

class TandemTakedownEffect extends OneShotEffect {

    TandemTakedownEffect() {
        super(Outcome.Benefit);
        staticText = "They each deal damage equal to their power to another target creature, planeswalker, or battle";
    }

    private TandemTakedownEffect(final TandemTakedownEffect effect) {
        super(effect);
    }

    @Override
    public TandemTakedownEffect copy() {
        return new TandemTakedownEffect(this);
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
        Permanent targetPermanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        if (permanents.isEmpty() || targetPermanent == null) {
            return false;
        }
        for (Permanent permanent : permanents) {
            targetPermanent.damage(permanent.getPower().getValue(), permanent.getId(), source, game);
        }
        return true;
    }
}
