package mage.cards.r;

import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
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
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoadRage extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Mounts and Vehicles you control");

    static {
        filter.add(Predicates.or(
                SubType.MOUNT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            new PermanentsOnBattlefieldCount(filter), StaticValue.get(2)
    );
    private static final Hint hint = new ValueHint(
            "Mounts and Vehicles you control", new PermanentsOnBattlefieldCount(filter)
    );

    public RoadRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Road Rage deals X damage to target creature or planeswalker, where X is 2 plus the number of Mounts and Vehicles you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals X damage to target creature or planeswalker, " +
                        "where X is 2 plus the number of Mounts and Vehicles you control"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addHint(hint);
    }

    private RoadRage(final RoadRage card) {
        super(card);
    }

    @Override
    public RoadRage copy() {
        return new RoadRage(this);
    }
}
