
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class DeathsShadow extends CardImpl {

    public DeathsShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(13);
        this.toughness = new MageInt(13);

        // Death's Shadow gets -X/-X, where X is your life total.
        SignInversionDynamicValue x = new SignInversionDynamicValue(ControllerLifeCount.instance, false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(x, x, Duration.WhileOnBattlefield)));
    }

    private DeathsShadow(final DeathsShadow card) {
        super(card);
    }

    @Override
    public DeathsShadow copy() {
        return new DeathsShadow(this);
    }
}
