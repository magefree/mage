
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class ApesOfRath extends CardImpl {

    public ApesOfRath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.APE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever Apes of Rath attacks, it doesn't untap during its controller's next untap step.
        this.addAbility(new AttacksTriggeredAbility(new DontUntapInControllersNextUntapStepSourceEffect(), false));
    }

    private ApesOfRath(final ApesOfRath card) {
        super(card);
    }

    @Override
    public ApesOfRath copy() {
        return new ApesOfRath(this);
    }
}
