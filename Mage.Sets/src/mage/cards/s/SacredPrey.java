
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class SacredPrey extends CardImpl {

    public SacredPrey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Sacred Prey becomes blocked, you gain 1 life.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new GainLifeEffect(1), false));
    }

    private SacredPrey(final SacredPrey card) {
        super(card);
    }

    @Override
    public SacredPrey copy() {
        return new SacredPrey(this);
    }
}
