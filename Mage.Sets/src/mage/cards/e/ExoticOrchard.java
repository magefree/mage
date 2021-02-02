
package mage.cards.e;

import java.util.UUID;
import mage.abilities.mana.AnyColorLandsProduceManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class ExoticOrchard extends CardImpl {

    public ExoticOrchard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add one mana of any color that a land an opponent controls could produce.
        this.addAbility(new AnyColorLandsProduceManaAbility(TargetController.OPPONENT));
    }

    private ExoticOrchard(final ExoticOrchard card) {
        super(card);
    }

    @Override
    public ExoticOrchard copy() {
        return new ExoticOrchard(this);
    }
}
