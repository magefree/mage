
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class Petrahydrox extends CardImpl {

    public Petrahydrox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U/R}");
        this.subtype.add(SubType.WEIRD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Petrahydrox becomes the target of a spell or ability, return Petrahydrox to its owner's hand.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(new ReturnToHandSourceEffect(true)));
    }

    private Petrahydrox(final Petrahydrox card) {
        super(card);
    }

    @Override
    public Petrahydrox copy() {
        return new Petrahydrox(this);
    }
}
