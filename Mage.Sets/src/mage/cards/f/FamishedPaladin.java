
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author L_J
 */
public final class FamishedPaladin extends CardImpl {

    public FamishedPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Famished Paladin doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));
        // Whenever you gain life, untap Famished Paladin.
        this.addAbility(new GainLifeControllerTriggeredAbility(new UntapSourceEffect(), false));
    }

    private FamishedPaladin(final FamishedPaladin card) {
        super(card);
    }

    @Override
    public FamishedPaladin copy() {
        return new FamishedPaladin(this);
    }
}
