package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.watchers.common.PlayerGainedLifeWatcher;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class DefilingDaemogoth extends CardImpl {

    public DefilingDaemogoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever a creature you control deals combat damage to a player, you gain 1 life.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
            new GainLifeEffect(1),
            StaticFilters.FILTER_CONTROLLED_A_CREATURE,
            false, SetTargetPointer.NONE, true
        ));

        // At the beginning of your end step, each opponent loses X life, where X is the amount of life you gained this turn.
        this.addAbility(
            new BeginningOfEndStepTriggeredAbility(
                new LoseLifeOpponentsEffect(ControllerGainedLifeCount.instance)
            ),
            new PlayerGainedLifeWatcher()
        );

    }

    private DefilingDaemogoth(final DefilingDaemogoth card) {
        super(card);
    }

    @Override
    public DefilingDaemogoth copy() {
        return new DefilingDaemogoth(this);
    }
}
