package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HollowhengeHuntmaster extends CardImpl {

    public HollowhengeHuntmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.color.setGreen(true);
        this.nightCard = true;

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Other permanents you control have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS, true
        )));

        // At the beginning of combat on your turn, put two +1/+1 counters on each creature you control.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(2),
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ), TargetController.YOU, false
        ));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private HollowhengeHuntmaster(final HollowhengeHuntmaster card) {
        super(card);
    }

    @Override
    public HollowhengeHuntmaster copy() {
        return new HollowhengeHuntmaster(this);
    }
}
