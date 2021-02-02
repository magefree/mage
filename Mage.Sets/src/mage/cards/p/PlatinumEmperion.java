
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.LifeTotalCantChangeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author nantuko
 */
public final class PlatinumEmperion extends CardImpl {

    public PlatinumEmperion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{8}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Your life total can't change.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LifeTotalCantChangeControllerEffect(Duration.WhileOnBattlefield)));
    }

    private PlatinumEmperion(final PlatinumEmperion card) {
        super(card);
    }

    @Override
    public PlatinumEmperion copy() {
        return new PlatinumEmperion(this);
    }
}
