
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class PulseTracker extends CardImpl {

    public PulseTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new AttacksTriggeredAbility(new LoseLifeOpponentsEffect(1), false));
    }

    private PulseTracker(final PulseTracker card) {
        super(card);
    }

    @Override
    public PulseTracker copy() {
        return new PulseTracker(this);
    }
}
