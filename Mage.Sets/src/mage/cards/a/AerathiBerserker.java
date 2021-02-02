
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.RampageAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class AerathiBerserker extends CardImpl {

    public AerathiBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Rampage 3
        this.addAbility(new RampageAbility(3));
    }

    private AerathiBerserker(final AerathiBerserker card) {
        super(card);
    }

    @Override
    public AerathiBerserker copy() {
        return new AerathiBerserker(this);
    }
}
