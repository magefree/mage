
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.AuraAttachedCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class UrilTheMiststalker extends CardImpl {

    public UrilTheMiststalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Uril, the Miststalker gets +2/+2 for each Aura attached to it.
        AuraAttachedCount count = new AuraAttachedCount(2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(count, count, Duration.WhileOnBattlefield)));

    }

    private UrilTheMiststalker(final UrilTheMiststalker card) {
        super(card);
    }

    @Override
    public UrilTheMiststalker copy() {
        return new UrilTheMiststalker(this);
    }
}
