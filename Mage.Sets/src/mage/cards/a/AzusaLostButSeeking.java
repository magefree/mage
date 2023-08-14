

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author Loki
 */
public final class AzusaLostButSeeking extends CardImpl {

    public AzusaLostButSeeking (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayAdditionalLandsControllerEffect(2, Duration.WhileOnBattlefield)));
    }

    public AzusaLostButSeeking (final AzusaLostButSeeking card) {
        super(card);
    }

    @Override
    public AzusaLostButSeeking copy() {
        return new AzusaLostButSeeking(this);
    }

}
