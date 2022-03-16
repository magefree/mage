
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTargetControllerSpellTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class DormantGomazoa extends CardImpl {

    public DormantGomazoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.JELLYFISH);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());

        // Dormant Gomazoa enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Dormant Gomazoa doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));

        // Whenever you become the target of a spell, you may untap Dormant Gomazoa.
        this.addAbility(new BecomesTargetControllerSpellTriggeredAbility(new UntapSourceEffect(), true).setTriggerPhrase("Whenever you become the target of a spell, "));
    }

    private DormantGomazoa(final DormantGomazoa card) {
        super(card);
    }

    @Override
    public DormantGomazoa copy() {
        return new DormantGomazoa(this);
    }
}
