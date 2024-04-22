
package mage.cards.v;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author nantuko
 */
public final class VensersJournal extends CardImpl {

    public VensersJournal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // You have no maximum hand size.
        Effect effect = new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield, HandSizeModification.SET);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // At the beginning of your upkeep, you gain 1 life for each card in your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(CardsInControllerHandCount.instance)
                .setText("you gain 1 life for each card in your hand"), TargetController.YOU, false));
    }

    private VensersJournal(final VensersJournal card) {
        super(card);
    }

    @Override
    public VensersJournal copy() {
        return new VensersJournal(this);
    }
}
