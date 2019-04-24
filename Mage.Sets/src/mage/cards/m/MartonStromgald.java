
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author Eirkei
 */
public final class MartonStromgald extends CardImpl {

    private static final FilterAttackingCreature attackingFilter = new FilterAttackingCreature("attacking creature other than {this}");
    private static final FilterBlockingCreature blockingFilter = new FilterBlockingCreature("blocking creature other than {this}");
    
    static {
        attackingFilter.add(new AnotherPredicate());
        blockingFilter.add(new AnotherPredicate());
    }
        
    public MartonStromgald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Marton Stromgald attacks, other attacking creatures get +1/+1 until end of turn for each attacking creature other than Marton Stromgald.
        PermanentsOnBattlefieldCount attackingValue = new PermanentsOnBattlefieldCount(attackingFilter);
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(attackingValue, attackingValue, Duration.EndOfTurn, new FilterAttackingCreature(), true, null, true), false));

        // Whenever Marton Stromgald blocks, other blocking creatures get +1/+1 until end of turn for each blocking creature other than Marton Stromgald.
        PermanentsOnBattlefieldCount blockingValue = new PermanentsOnBattlefieldCount(blockingFilter);
        this.addAbility(new BlocksTriggeredAbility(new BoostAllEffect(blockingValue, blockingValue, Duration.EndOfTurn, new FilterBlockingCreature(), true, null, true), false));
        
    }

    public MartonStromgald(final MartonStromgald card) {
        super(card);
    }

    @Override
    public MartonStromgald copy() {
        return new MartonStromgald(this);
    }
}
