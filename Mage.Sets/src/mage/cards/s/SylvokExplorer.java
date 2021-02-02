
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.AnyColorLandsProduceManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author Plopman
 */
public final class SylvokExplorer extends CardImpl {

    public SylvokExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add one mana of any color that a land an opponent controls could produce.
        this.addAbility(new AnyColorLandsProduceManaAbility(TargetController.OPPONENT));
    }

    private SylvokExplorer(final SylvokExplorer card) {
        super(card);
    }

    @Override
    public SylvokExplorer copy() {
        return new SylvokExplorer(this);
    }
}
