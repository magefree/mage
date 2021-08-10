
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX
 */
public final class OrochiRanger extends CardImpl {

    public OrochiRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.RANGER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Orochi Ranger deals combat damage to a creature, tap that creature and it doesn't untap during its controller's next untap step.
                Ability ability;
        ability = new DealsDamageToACreatureTriggeredAbility(new TapTargetEffect("tap that creature"), true, false, true);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("and it"));
        this.addAbility(ability);
    }

    private OrochiRanger(final OrochiRanger card) {
        super(card);
    }

    @Override
    public OrochiRanger copy() {
        return new OrochiRanger(this);
    }
}