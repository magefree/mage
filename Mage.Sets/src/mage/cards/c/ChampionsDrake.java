
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ChampionsDrake extends CardImpl {

    private static final String rule = "{this} gets +3/+3 as long as you control a creature with three or more level counters on it.";

    public ChampionsDrake(UUID ownerId, CardSetInfo setInfo) {

        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.DRAKE);

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());

        // Champion's Drake gets +3/+3 as long as you control a creature with three or more level counters on it.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostSourceEffect(3, 3, Duration.WhileOnBattlefield), new PermanentHasCounterCondition(CounterType.LEVEL, 2, new FilterControlledCreaturePermanent(), ComparisonType.MORE_THAN), rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private ChampionsDrake(final ChampionsDrake card) {
        super(card);
    }

    @Override
    public ChampionsDrake copy() {
        return new ChampionsDrake(this);
    }
}
