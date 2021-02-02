
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class IllTemperedCyclops extends CardImpl {

    public IllTemperedCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.CYCLOPS);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // {5}{R}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{5}{R}", 3));
    }

    private IllTemperedCyclops(final IllTemperedCyclops card) {
        super(card);
    }

    @Override
    public IllTemperedCyclops copy() {
        return new IllTemperedCyclops(this);
    }
}
