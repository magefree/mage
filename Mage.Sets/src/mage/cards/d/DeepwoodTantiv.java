
package mage.cards.d;

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
public final class DeepwoodTantiv extends CardImpl {

    public DeepwoodTantiv(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Deepwood Tantiv becomes blocked, you gain 2 life.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new GainLifeEffect(2), false));
    }

    private DeepwoodTantiv(final DeepwoodTantiv card) {
        super(card);
    }

    @Override
    public DeepwoodTantiv copy() {
        return new DeepwoodTantiv(this);
    }
}
