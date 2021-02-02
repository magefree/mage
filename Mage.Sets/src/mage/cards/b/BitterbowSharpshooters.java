
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author ciaccona007
 */
public final class BitterbowSharpshooters extends CardImpl {

    public BitterbowSharpshooters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        
        this.subtype.add(SubType.JACKAL);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

    }

    private BitterbowSharpshooters(final BitterbowSharpshooters card) {
        super(card);
    }

    @Override
    public BitterbowSharpshooters copy() {
        return new BitterbowSharpshooters(this);
    }
}
