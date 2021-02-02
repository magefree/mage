
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Quercitron
 */
public final class LeadGolem extends CardImpl {

    public LeadGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Whenever Lead Golem attacks, it doesn't untap during its controller's next untap step.
        Ability ability = new AttacksTriggeredAbility(new DontUntapInControllersNextUntapStepSourceEffect(), false);
        this.addAbility(ability);
    }

    private LeadGolem(final LeadGolem card) {
        super(card);
    }

    @Override
    public LeadGolem copy() {
        return new LeadGolem(this);
    }
}
