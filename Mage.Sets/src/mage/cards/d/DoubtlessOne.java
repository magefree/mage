
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageGainLifeSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author fireshoes
 */
public final class DoubtlessOne extends CardImpl {
    
    final static FilterPermanent filter = new FilterPermanent("Clerics on the battlefield");

        static {
            filter.add(new SubtypePredicate(SubType.CLERIC));
        }

    public DoubtlessOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Doubtless One's power and toughness are each equal to the number of Clerics on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
        
        // Whenever Doubtless One deals damage, you gain that much life.
        this.addAbility(new DealsDamageGainLifeSourceTriggeredAbility());
    }

    public DoubtlessOne(final DoubtlessOne card) {
        super(card);
    }

    @Override
    public DoubtlessOne copy() {
        return new DoubtlessOne(this);
    }
}