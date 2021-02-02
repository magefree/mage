
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
public final class ZodiacTiger extends CardImpl {

    public ZodiacTiger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());
    }

    private ZodiacTiger(final ZodiacTiger card) {
        super(card);
    }

    @Override
    public ZodiacTiger copy() {
        return new ZodiacTiger(this);
    }
}
