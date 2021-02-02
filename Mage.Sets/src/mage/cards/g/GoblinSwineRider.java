
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingOrBlockingCreature;

/**
 *
 * @author LoneFox
 */
public final class GoblinSwineRider extends CardImpl {

    public GoblinSwineRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Goblin Swine-Rider becomes blocked, it deals 2 damage to each attacking creature and each blocking creature.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new DamageAllEffect(2, "it", new FilterAttackingOrBlockingCreature("attacking creature and each blocking creature")), false));
    }

    private GoblinSwineRider(final GoblinSwineRider card) {
        super(card);
    }

    @Override
    public GoblinSwineRider copy() {
        return new GoblinSwineRider(this);
    }
}
