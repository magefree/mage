
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author daagar
 */
public final class DemonsJester extends CardImpl {

    public DemonsJester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.IMP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Hellbent - Demon's Jester gets +2/+1 as long as you have no cards in hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(2,1,Duration.WhileOnBattlefield), HellbentCondition.instance,
            "<i>Hellbent</i> &mdash; {this} gets +2/+1 as long as you have no cards in hand")));
    }

    private DemonsJester(final DemonsJester card) {
        super(card);
    }

    @Override
    public DemonsJester copy() {
        return new DemonsJester(this);
    }
}
