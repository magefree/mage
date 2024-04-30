
package mage.cards.l;

import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.DontLoseByZeroOrLessLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class LichsTomb extends CardImpl {

    public LichsTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // You don't lose the game for having 0 or less life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontLoseByZeroOrLessLifeEffect(Duration.WhileOnBattlefield)));

        // Whenever you lose life, sacrifice a permanent for each 1 life you lost.
        this.addAbility(new LoseLifeTriggeredAbility(
                new SacrificeControllerEffect(new FilterPermanent(), SavedLifeLossValue.MUCH, ""),
                TargetController.YOU
        ));
    }

    private LichsTomb(final LichsTomb card) {
        super(card);
    }

    @Override
    public LichsTomb copy() {
        return new LichsTomb(this);
    }
}