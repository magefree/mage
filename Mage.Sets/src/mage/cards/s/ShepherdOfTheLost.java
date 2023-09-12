

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class ShepherdOfTheLost extends CardImpl {

    public ShepherdOfTheLost (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
    }

    private ShepherdOfTheLost(final ShepherdOfTheLost card) {
        super(card);
    }

    @Override
    public ShepherdOfTheLost copy() {
        return new ShepherdOfTheLost(this);
    }
}
