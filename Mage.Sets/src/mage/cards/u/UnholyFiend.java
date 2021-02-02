
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class UnholyFiend extends CardImpl {

    public UnholyFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.HORROR);

        this.color.setBlack(true);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.nightCard = true;

        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new LoseLifeSourceControllerEffect(1), false));
    }

    private UnholyFiend(final UnholyFiend card) {
        super(card);
    }

    @Override
    public UnholyFiend copy() {
        return new UnholyFiend(this);
    }
}
