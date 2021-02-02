
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class GoblinArsonist extends CardImpl {

    public GoblinArsonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Goblin Arsonist dies, you may have it deal 1 damage to any target.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(1).setText("it deal 1 damage to any target"), true);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private GoblinArsonist(final GoblinArsonist card) {
        super(card);
    }

    @Override
    public GoblinArsonist copy() {
        return new GoblinArsonist(this);
    }
}
