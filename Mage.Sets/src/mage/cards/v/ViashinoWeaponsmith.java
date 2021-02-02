
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author hanasu
 */
public final class ViashinoWeaponsmith extends CardImpl {

    public ViashinoWeaponsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Viashino Weaponsmith becomes blocked by a creature, Viashino Weaponsmith gets +2/+2 until end of turn.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), false));
    }

    private ViashinoWeaponsmith(final ViashinoWeaponsmith card) {
        super(card);
    }

    @Override
    public ViashinoWeaponsmith copy() {
        return new ViashinoWeaponsmith(this);
    }
}
