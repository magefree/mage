
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author TheElk801
 */
public final class AysenCrusader extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Soldiers and Warriors you control");

    static {
        filter.add(Predicates.or(
                new SubtypePredicate(SubType.SOLDIER),
                new SubtypePredicate(SubType.WARRIOR)
        ));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public AysenCrusader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Aysen Crusader's power and toughness are each equal to 2 plus the number of Soldiers and Warriors you control.
        DynamicValue value = new IntPlusDynamicValue(2, new PermanentsOnBattlefieldCount(filter));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(value, Duration.EndOfGame)));
    }

    public AysenCrusader(final AysenCrusader card) {
        super(card);
    }

    @Override
    public AysenCrusader copy() {
        return new AysenCrusader(this);
    }
}
