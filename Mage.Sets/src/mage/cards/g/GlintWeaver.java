package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.GreatestToughnessAmongControlledCreaturesValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlintWeaver extends CardImpl {

    public GlintWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Glint Weaver enters the battlefield, distribute three +1/+1 counters among one, two, or three target creatures, then you gain life equal to the greatest toughness among creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DistributeCountersEffect(
                CounterType.P1P1, 3, "one, two, or three target creatures"
        ));
        ability.addEffect(new GainLifeEffect(GreatestToughnessAmongControlledCreaturesValue.instance).concatBy(", then"));
        ability.addTarget(new TargetCreaturePermanentAmount(3));
        this.addAbility(ability);
    }

    private GlintWeaver(final GlintWeaver card) {
        super(card);
    }

    @Override
    public GlintWeaver copy() {
        return new GlintWeaver(this);
    }
}
