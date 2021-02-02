
package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class Exploration extends CardImpl {

    public Exploration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");


        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
				new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)));
    }

    private Exploration(final Exploration card) {
        super(card);
    }

    @Override
    public Exploration copy() {
        return new Exploration(this);
    }
}
