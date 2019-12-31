package mage.cards.o;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Outflank extends CardImpl {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE);

    public Outflank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Outflank deals damage to target attacking or blocking creature equal to the number of creatures you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals damage to target attacking or blocking creature " +
                        "equal to the number of creatures you control"));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private Outflank(final Outflank card) {
        super(card);
    }

    @Override
    public Outflank copy() {
        return new Outflank(this);
    }
}
