
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class SaprazzanHeir extends CardImpl {

    public SaprazzanHeir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Saprazzan Heir becomes blocked, you may draw three cards.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new DrawCardSourceControllerEffect(3), true));
    }

    private SaprazzanHeir(final SaprazzanHeir card) {
        super(card);
    }

    @Override
    public SaprazzanHeir copy() {
        return new SaprazzanHeir(this);
    }
}
