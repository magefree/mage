

package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class IronMyr extends CardImpl {

    public IronMyr (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new RedManaAbility());
    }

    private IronMyr(final IronMyr card) {
        super(card);
    }

    @Override
    public IronMyr copy() {
        return new IronMyr(this);
    }
}
