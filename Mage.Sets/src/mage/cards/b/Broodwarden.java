
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author North
 */
public final class Broodwarden extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Eldrazi Spawn creatures");

    static {
        filter.add(SubType.ELDRAZI.getPredicate());
        filter.add(SubType.SPAWN.getPredicate());
    }

    public Broodwarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELDRAZI, SubType.DRONE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        // Eldrazi Spawn creatures you control get +2/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 1, Duration.WhileOnBattlefield, filter, false)));
    }

    private Broodwarden(final Broodwarden card) {
        super(card);
    }

    @Override
    public Broodwarden copy() {
        return new Broodwarden(this);
    }
}
