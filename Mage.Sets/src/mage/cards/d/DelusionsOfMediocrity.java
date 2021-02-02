
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author Backfir3
 */
public final class DelusionsOfMediocrity extends CardImpl {

    public DelusionsOfMediocrity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}");


        //When Delusions of Mediocrity enters the battlefield, you gain 10 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(10), false));
        //When Delusions of Mediocrity leaves the battlefield, you lose 10 life.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new LoseLifeSourceControllerEffect(10), false));
    }

    private DelusionsOfMediocrity(final DelusionsOfMediocrity card) {
        super(card);
    }

    @Override
    public DelusionsOfMediocrity copy() {
        return new DelusionsOfMediocrity(this);
    }
}
