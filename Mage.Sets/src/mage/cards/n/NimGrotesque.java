
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;

/**
 *
 * @author Plopman
 */
public final class NimGrotesque extends CardImpl {

    public NimGrotesque(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Nim Grotesque gets +1/+0 for each artifact you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(new PermanentsOnBattlefieldCount(new FilterControlledArtifactPermanent()), new StaticValue(0), Duration.WhileOnBattlefield)));
    }

    public NimGrotesque(final NimGrotesque card) {
        super(card);
    }

    @Override
    public NimGrotesque copy() {
        return new NimGrotesque(this);
    }
}
