
package mage.cards.f;

import java.util.UUID;
import mage.abilities.mana.AnyColorLandsProduceManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author Plopman
 */
public final class FellwarStone extends CardImpl {

    public FellwarStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {T}: Add one mana of any color that a land an opponent controls could produce.
        this.addAbility(new AnyColorLandsProduceManaAbility(TargetController.OPPONENT));
    }

    private FellwarStone(final FellwarStone card) {
        super(card);
    }

    @Override
    public FellwarStone copy() {
        return new FellwarStone(this);
    }
}
