
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class GaeasProtector extends CardImpl {

    public GaeasProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Gaea's Protector must be blocked if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));
    }

    public GaeasProtector(final GaeasProtector card) {
        super(card);
    }

    @Override
    public GaeasProtector copy() {
        return new GaeasProtector(this);
    }
}
