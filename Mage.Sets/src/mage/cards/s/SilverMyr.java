

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SilverMyr extends CardImpl {

    public SilverMyr (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new BlueManaAbility());
    }

    private SilverMyr(final SilverMyr card) {
        super(card);
    }

    @Override
    public SilverMyr copy() {
        return new SilverMyr(this);
    }

}
