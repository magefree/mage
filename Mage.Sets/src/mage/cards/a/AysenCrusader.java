
package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AysenCrusader extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Soldiers and Warriors you control");

    static {
        filter.add(Predicates.or(
                SubType.SOLDIER.getPredicate(),
                SubType.WARRIOR.getPredicate()
        ));
    }

    private static final DynamicValue value = new IntPlusDynamicValue(2, new PermanentsOnBattlefieldCount(filter));

    public AysenCrusader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Aysen Crusader's power and toughness are each equal to 2 plus the number of Soldiers and Warriors you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(value)));
    }

    private AysenCrusader(final AysenCrusader card) {
        super(card);
    }

    @Override
    public AysenCrusader copy() {
        return new AysenCrusader(this);
    }
}
