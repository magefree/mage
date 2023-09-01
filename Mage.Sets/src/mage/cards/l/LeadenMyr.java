

package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class LeadenMyr extends CardImpl {

    public LeadenMyr (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new BlackManaAbility());
    }

    private LeadenMyr(final LeadenMyr card) {
        super(card);
    }

    @Override
    public LeadenMyr copy() {
        return new LeadenMyr(this);
    }

}
