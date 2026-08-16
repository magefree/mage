package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CollectiveDrone extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("artifact creatures you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public CollectiveDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Collective Drone's power is equal to the number of artifact creatures you control.
        Effect effect = new SetBasePowerSourceEffect(new PermanentsOnBattlefieldCount(filter));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));
    }

    private CollectiveDrone(final CollectiveDrone card) {
        super(card);
    }

    @Override
    public CollectiveDrone copy() {
        return new CollectiveDrone(this);
    }
}
