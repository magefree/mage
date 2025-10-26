package mage.cards.t;

import mage.abilities.effects.common.TargetsDamageTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801, xenohedron
 */
public final class TandemTakedown extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target creature, planeswalker, or battle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
        filter.add(new AnotherTargetPredicate(3));
    }

    public TandemTakedown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{G}");

        // Up to two target creatures you control each get +1/+0 until end of turn. They each deal damage equal to their power to another target creature, planeswalker, or battle.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0)
                .setText("up to two target creatures you control each get +1/+0 until end of turn"));
        this.getSpellAbility().addEffect(new TargetsDamageTargetsEffect(false));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 2).setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(filter).setTargetTag(3));
    }

    private TandemTakedown(final TandemTakedown card) {
        super(card);
    }

    @Override
    public TandemTakedown copy() {
        return new TandemTakedown(this);
    }
}
