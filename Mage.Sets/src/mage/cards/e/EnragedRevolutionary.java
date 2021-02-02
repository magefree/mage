
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DethroneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class EnragedRevolutionary extends CardImpl {

    public EnragedRevolutionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Dethrone
        this.addAbility(new DethroneAbility());
    }

    private EnragedRevolutionary(final EnragedRevolutionary card) {
        super(card);
    }

    @Override
    public EnragedRevolutionary copy() {
        return new EnragedRevolutionary(this);
    }
}
