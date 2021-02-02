

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LevelX2
 */
public final class ArcboundWorker extends CardImpl {

    public ArcboundWorker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Modular 1 (This enters the battlefield with a +1/+1 counter on it. When it dies, you may put its +1/+1 counters on target artifact creature.)
        this.addAbility(new ModularAbility(this, 1));
    }

    private ArcboundWorker(final ArcboundWorker card) {
        super(card);
    }

    @Override
    public ArcboundWorker copy() {
        return new ArcboundWorker(this);
    }

}
