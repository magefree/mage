
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.BlocksAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class BattleStrain extends CardImpl {

    public BattleStrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");


        // Whenever a creature blocks, Battle Strain deals 1 damage to that creature's controller.
        this.addAbility(new BlocksAllTriggeredAbility(
                new DamageTargetEffect(1).withTargetDescription("that creature's controller"),
                false, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.PLAYER
        ));
    }

    private BattleStrain(final BattleStrain card) {
        super(card);
    }

    @Override
    public BattleStrain copy() {
        return new BattleStrain(this);
    }
}
