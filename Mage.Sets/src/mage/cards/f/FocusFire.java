package mage.cards.f;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FocusFire extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.SPACECRAFT.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 2);
    private static final Hint hint = new ValueHint(
            "Creatures and/or Spacecraft you control", new PermanentsOnBattlefieldCount(filter)
    );

    public FocusFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Focus Fire deals X damage to target attacking or blocking creature, where X is 2 plus the number of creatures and/or Spacecraft you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals X damage to target attacking or blocking creature, " +
                        "where X is 2 plus the number of creatures and/or Spacecraft you control"));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
        this.getSpellAbility().addHint(hint);
    }

    private FocusFire(final FocusFire card) {
        super(card);
    }

    @Override
    public FocusFire copy() {
        return new FocusFire(this);
    }
}
