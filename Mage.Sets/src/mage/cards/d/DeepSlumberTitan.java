
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
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
 * @author jeffwadsworth

 */
public final class DeepSlumberTitan extends CardImpl {

    public DeepSlumberTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Deep-Slumber Titan enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // Deep-Slumber Titan doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));
        
        // Whenever Deep-Slumber Titan is dealt damage, untap it.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new UntapSourceEffect().setText("untap it"), false));
        
    }

    private DeepSlumberTitan(final DeepSlumberTitan card) {
        super(card);
    }

    @Override
    public DeepSlumberTitan copy() {
        return new DeepSlumberTitan(this);
    }
}
