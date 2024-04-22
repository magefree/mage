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
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801, xenohedron
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
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 2).setTargetTag(1));
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
        if (source.getTargets().size() < 2) {
            return false;
        }

        Target damageTarget = source.getTargets().get(0);
        Target destTarget = source.getTargets().get(1);
        if (damageTarget.getTargets().isEmpty() || destTarget.getTargets().isEmpty()) {
            return false;
        }

        Permanent permanentDamage1 = damageTarget.getTargets().isEmpty() ? null : game.getPermanent(damageTarget.getTargets().get(0));
        Permanent permanentDamage2 = damageTarget.getTargets().size() < 2 ? null : game.getPermanent(damageTarget.getTargets().get(1));
        Permanent permanentDest = game.getPermanent(destTarget.getTargets().get(0));
        if (permanentDest == null) {
            return false;
        }

        if (permanentDamage1 != null) {
            permanentDest.damage(permanentDamage1.getPower().getValue(), permanentDamage1.getId(), source, game, false, true);
        }
        if (permanentDamage2 != null) {
            permanentDest.damage(permanentDamage2.getPower().getValue(), permanentDamage2.getId(), source, game, false, true);
        }
        return true;
    }
}
