package mage.cards.b;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BargeIn extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("Each attacking non-Human creature");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public BargeIn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target attacking creature gets +2/+2 until end of turn. Each attacking non-Human creature gains trample until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, filter
        ));
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private BargeIn(final BargeIn card) {
        super(card);
    }

    @Override
    public BargeIn copy() {
        return new BargeIn(this);
    }
}
