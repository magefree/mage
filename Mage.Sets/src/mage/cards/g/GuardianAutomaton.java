
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox

 */
public final class GuardianAutomaton extends CardImpl {

    public GuardianAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Guardian Automaton dies, you gain 3 life.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(3)));
    }

    private GuardianAutomaton(final GuardianAutomaton card) {
        super(card);
    }

    @Override
    public GuardianAutomaton copy() {
        return new GuardianAutomaton(this);
    }
}
