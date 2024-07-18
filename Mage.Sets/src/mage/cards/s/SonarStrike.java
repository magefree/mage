package mage.cards.s;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SonarStrike extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("attacking, blocking, or tapped creature");

    static {
        filter.add(Predicates.or(
                AttackingPredicate.instance,
                BlockingPredicate.instance,
                TappedPredicate.TAPPED
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.BAT));
    private static final Hint hint = new ConditionHint(condition, "You control a Bat");

    public SonarStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Sonar Strike deals 4 damage to target attacking, blocking, or tapped creature. You gain 3 life if you control a Bat.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(3), condition,
                "you gain 3 life if you control a Bat"
        ));
        this.getSpellAbility().addHint(hint);
    }

    private SonarStrike(final SonarStrike card) {
        super(card);
    }

    @Override
    public SonarStrike copy() {
        return new SonarStrike(this);
    }
}
