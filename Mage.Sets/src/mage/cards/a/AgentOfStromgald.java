
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class AgentOfStromgald extends CardImpl {

    public AgentOfStromgald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}: Add {B}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new ManaCostsImpl<>("{R}")));
    }

    private AgentOfStromgald(final AgentOfStromgald card) {
        super(card);
    }

    @Override
    public AgentOfStromgald copy() {
        return new AgentOfStromgald(this);
    }
}
