
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Wehk
 */
public final class ExiledBoggart extends CardImpl {

    public ExiledBoggart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Exiled Boggart dies, discard a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DiscardControllerEffect(1), false));
    }

    private ExiledBoggart(final ExiledBoggart card) {
        super(card);
    }

    @Override
    public ExiledBoggart copy() {
        return new ExiledBoggart(this);
    }
}
