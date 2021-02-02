
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class HuatlisSnubhorn extends CardImpl {

    public HuatlisSnubhorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

    }

    private HuatlisSnubhorn(final HuatlisSnubhorn card) {
        super(card);
    }

    @Override
    public HuatlisSnubhorn copy() {
        return new HuatlisSnubhorn(this);
    }
}
