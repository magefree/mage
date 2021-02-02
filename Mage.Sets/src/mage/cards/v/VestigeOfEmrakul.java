
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class VestigeOfEmrakul extends CardImpl {

    public VestigeOfEmrakul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private VestigeOfEmrakul(final VestigeOfEmrakul card) {
        super(card);
    }

    @Override
    public VestigeOfEmrakul copy() {
        return new VestigeOfEmrakul(this);
    }
}
