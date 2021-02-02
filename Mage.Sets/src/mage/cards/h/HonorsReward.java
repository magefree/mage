
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class HonorsReward extends CardImpl {

    public HonorsReward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // You gain 4 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
        // Bolster 2.
        this.getSpellAbility().addEffect(new BolsterEffect(2));
    }

    private HonorsReward(final HonorsReward card) {
        super(card);
    }

    @Override
    public HonorsReward copy() {
        return new HonorsReward(this);
    }
}
