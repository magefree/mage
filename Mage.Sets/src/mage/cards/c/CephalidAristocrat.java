
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class CephalidAristocrat extends CardImpl {

    public CephalidAristocrat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.CEPHALID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Cephalid Aristocrat becomes the target of a spell or ability, put the top two cards of your library into your graveyard.
        this.addAbility(new BecomesTargetTriggeredAbility(new PutTopCardOfLibraryIntoGraveControllerEffect(2)));
    }

    public CephalidAristocrat(final CephalidAristocrat card) {
        super(card);
    }

    @Override
    public CephalidAristocrat copy() {
        return new CephalidAristocrat(this);
    }
}
