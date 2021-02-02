
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class DragonStyleTwins extends CardImpl {

    public DragonStyleTwins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
        // Prowess <em>(Whenever you cast a noncreature spell, this creature gets +1/+1 until end of turn.)</em>
        this.addAbility(new ProwessAbility());
    }

    private DragonStyleTwins(final DragonStyleTwins card) {
        super(card);
    }

    @Override
    public DragonStyleTwins copy() {
        return new DragonStyleTwins(this);
    }
}
