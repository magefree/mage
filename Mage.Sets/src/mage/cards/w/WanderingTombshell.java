
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class WanderingTombshell extends CardImpl {

    public WanderingTombshell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(6);
    }

    private WanderingTombshell(final WanderingTombshell card) {
        super(card);
    }

    @Override
    public WanderingTombshell copy() {
        return new WanderingTombshell(this);
    }
}
