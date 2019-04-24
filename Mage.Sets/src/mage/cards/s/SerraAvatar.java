
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class SerraAvatar extends CardImpl {

    public SerraAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}{W}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Serra Avatar's power and toughness are each equal to your life total.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new ControllerLifeCount(), Duration.EndOfGame)));
        
        // When Serra Avatar is put into a graveyard from anywhere, shuffle it into its owner's library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect()));
    }

    public SerraAvatar(final SerraAvatar card) {
        super(card);
    }

    @Override
    public SerraAvatar copy() {
        return new SerraAvatar(this);
    }
}
