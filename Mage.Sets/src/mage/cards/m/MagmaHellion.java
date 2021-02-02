
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.AssistAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class MagmaHellion extends CardImpl {

    public MagmaHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}");

        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Assist
        this.addAbility(new AssistAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

    }

    private MagmaHellion(final MagmaHellion card) {
        super(card);
    }

    @Override
    public MagmaHellion copy() {
        return new MagmaHellion(this);
    }
}
