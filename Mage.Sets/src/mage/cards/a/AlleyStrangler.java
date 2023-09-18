
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class AlleyStrangler extends CardImpl {

    public AlleyStrangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));
    }

    private AlleyStrangler(final AlleyStrangler card) {
        super(card);
    }

    @Override
    public AlleyStrangler copy() {
        return new AlleyStrangler(this);
    }
}
