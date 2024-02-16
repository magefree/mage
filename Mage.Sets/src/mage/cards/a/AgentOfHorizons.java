
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class AgentOfHorizons extends CardImpl {

    public AgentOfHorizons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {2}{U}: Agent of Horizons can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{2}{U}")));
    }

    private AgentOfHorizons(final AgentOfHorizons card) {
        super(card);
    }

    @Override
    public AgentOfHorizons copy() {
        return new AgentOfHorizons(this);
    }
}
