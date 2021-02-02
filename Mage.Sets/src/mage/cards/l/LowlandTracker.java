
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ProvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Temba21
 */
public final class LowlandTracker extends CardImpl {

    public LowlandTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Provoke
        this.addAbility(new ProvokeAbility());
    }

    private LowlandTracker(final LowlandTracker card) {
        super(card);
    }

    @Override
    public LowlandTracker copy() {
        return new LowlandTracker(this);
    }
}
