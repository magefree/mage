
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class NoxiousToad extends CardImpl {

    public NoxiousToad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.FROG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Noxious Toad dies, each opponent discards a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT), false));
    }

    private NoxiousToad(final NoxiousToad card) {
        super(card);
    }

    @Override
    public NoxiousToad copy() {
        return new NoxiousToad(this);
    }
}
