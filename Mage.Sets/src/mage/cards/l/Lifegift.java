
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class Lifegift extends CardImpl {

    public Lifegift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");


        // Whenever a land enters the battlefield, you may gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), StaticFilters.FILTER_LAND_A, true));
    }

    private Lifegift(final Lifegift card) {
        super(card);
    }

    @Override
    public Lifegift copy() {
        return new Lifegift(this);
    }
}
