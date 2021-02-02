
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class SageOfEpityr extends CardImpl {

    public SageOfEpityr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Sage of Epityr enters the battlefield, look at the top four cards of your library, then put them back in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryControllerEffect(4)));
    }

    private SageOfEpityr(final SageOfEpityr card) {
        super(card);
    }

    @Override
    public SageOfEpityr copy() {
        return new SageOfEpityr(this);
    }
}
