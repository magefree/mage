
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author TheElk801
 */
public final class DriftOfTheDead extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("snow lands you control");

    static {
        filter.add(SuperType.SNOW.getPredicate());
        filter.add(CardType.LAND.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DriftOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Drift of the Dead's power and toughness are each equal to the number of snow lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter))));
    }

    private DriftOfTheDead(final DriftOfTheDead card) {
        super(card);
    }

    @Override
    public DriftOfTheDead copy() {
        return new DriftOfTheDead(this);
    }
}
