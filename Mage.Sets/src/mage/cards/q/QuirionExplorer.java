
package mage.cards.q;

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
 * @author LoneFox
 */
public final class QuirionExplorer extends CardImpl {

    public QuirionExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add one mana of any color that a land an opponent controls could produce.
        this.addAbility(new AnyColorLandsProduceManaAbility(TargetController.OPPONENT));
    }

    private QuirionExplorer(final QuirionExplorer card) {
        super(card);
    }

    @Override
    public QuirionExplorer copy() {
        return new QuirionExplorer(this);
    }
}
