
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class BorosRecruit extends CardImpl {

    public BorosRecruit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R/W}");
        this.subtype.add(SubType.GOBLIN, SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private BorosRecruit(final BorosRecruit card) {
        super(card);
    }

    @Override
    public BorosRecruit copy() {
        return new BorosRecruit(this);
    }
}
