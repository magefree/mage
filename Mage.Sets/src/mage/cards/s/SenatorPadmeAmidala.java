
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author Styxo
 */
public final class SenatorPadmeAmidala extends CardImpl {

    public SenatorPadmeAmidala(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you draw a card, you gain 1 life.
        this.addAbility(new DrawCardControllerTriggeredAbility(new GainLifeEffect(1), false));

    }

    private SenatorPadmeAmidala(final SenatorPadmeAmidala card) {
        super(card);
    }

    @Override
    public SenatorPadmeAmidala copy() {
        return new SenatorPadmeAmidala(this);
    }
}
