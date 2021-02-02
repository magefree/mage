
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleAllTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class LightningRift extends CardImpl {

    public LightningRift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");


        // Whenever a player cycles a card, you may pay {1}. If you do, Lightning Rift deals 2 damage to any target.
        Ability ability = new CycleAllTriggeredAbility(new DoIfCostPaid(new DamageTargetEffect(2), new GenericManaCost(1)), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private LightningRift(final LightningRift card) {
        super(card);
    }

    @Override
    public LightningRift copy() {
        return new LightningRift(this);
    }
}
