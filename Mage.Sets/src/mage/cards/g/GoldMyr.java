

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class GoldMyr extends CardImpl {

    public GoldMyr (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new WhiteManaAbility());
    }

    private GoldMyr(final GoldMyr card) {
        super(card);
    }

    @Override
    public GoldMyr copy() {
        return new GoldMyr(this);
    }
}
