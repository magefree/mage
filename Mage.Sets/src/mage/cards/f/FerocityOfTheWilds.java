package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FerocityOfTheWilds extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("attacking non-Human creatures");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public FerocityOfTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Attacking non-Human creatures you control get +1/+0 and have trample.
        Ability ability = new SimpleStaticAbility(
                new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filter)
        );
        ability.addEffect(new GainAbilityAllEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, "and have trample"
        ));
        this.addAbility(ability);
    }

    private FerocityOfTheWilds(final FerocityOfTheWilds card) {
        super(card);
    }

    @Override
    public FerocityOfTheWilds copy() {
        return new FerocityOfTheWilds(this);
    }
}
