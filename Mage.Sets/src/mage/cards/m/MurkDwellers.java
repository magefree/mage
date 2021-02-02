
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public final class MurkDwellers extends CardImpl {

    public MurkDwellers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Murk Dwellers attacks and isn't blocked, it gets +2/+0 until end of combat.
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfCombat)));
    }

    private MurkDwellers(final MurkDwellers card) {
        super(card);
    }

    @Override
    public MurkDwellers copy() {
        return new MurkDwellers(this);
    }
}
