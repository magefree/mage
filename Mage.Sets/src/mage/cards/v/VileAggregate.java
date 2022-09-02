
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.IngestAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;

/**
 *
 * @author LevelX2
 */
public final class VileAggregate extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("colorless creatures you control");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public VileAggregate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Vile Aggregate's power is equal to the number of colorless creatures you control.
        Effect effect = new SetBasePowerSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Ingest
        this.addAbility(new IngestAbility());
    }

    private VileAggregate(final VileAggregate card) {
        super(card);
    }

    @Override
    public VileAggregate copy() {
        return new VileAggregate(this);
    }
}
