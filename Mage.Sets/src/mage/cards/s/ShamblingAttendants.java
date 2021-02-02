
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ShamblingAttendants extends CardImpl {

    public ShamblingAttendants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Delve
        this.addAbility(new DelveAbility());
        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private ShamblingAttendants(final ShamblingAttendants card) {
        super(card);
    }

    @Override
    public ShamblingAttendants copy() {
        return new ShamblingAttendants(this);
    }
}
