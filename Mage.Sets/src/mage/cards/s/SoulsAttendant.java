
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author North
 */
public final class SoulsAttendant extends CardImpl {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SoulsAttendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another creature enters the battlefield, you may gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), filter, true));
    }

    private SoulsAttendant(final SoulsAttendant card) {
        super(card);
    }

    @Override
    public SoulsAttendant copy() {
        return new SoulsAttendant(this);
    }
}
