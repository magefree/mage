
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author Plopman
 */
public final class ViashinoSandscout extends CardImpl {

    public ViashinoSandscout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // At the beginning of the end step, return Viashino Sandscout to its owner's hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ReturnToHandSourceEffect(true), TargetController.NEXT, false));
    }

    private ViashinoSandscout(final ViashinoSandscout card) {
        super(card);
    }

    @Override
    public ViashinoSandscout copy() {
        return new ViashinoSandscout(this);
    }
}
