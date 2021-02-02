
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ZodiacRabbit extends CardImpl {

    public ZodiacRabbit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.RABBIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());
    }

    private ZodiacRabbit(final ZodiacRabbit card) {
        super(card);
    }

    @Override
    public ZodiacRabbit copy() {
        return new ZodiacRabbit(this);
    }
}
