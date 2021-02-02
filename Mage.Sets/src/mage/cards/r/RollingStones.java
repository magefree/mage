
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class RollingStones extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wall creatures");
    
    static {
        filter.add(SubType.WALL.getPredicate());
    }
    
    public RollingStones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // Wall creatures can attack as though they didn't have defender.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanAttackAsThoughItDidntHaveDefenderAllEffect(Duration.WhileOnBattlefield, filter)));
    }

    private RollingStones(final RollingStones card) {
        super(card);
    }

    @Override
    public RollingStones copy() {
        return new RollingStones(this);
    }
}
