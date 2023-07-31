package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlossomingBogbeast extends CardImpl {

    public BlossomingBogbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Blossoming Bogbeast attacks, you gain 2 life. Then creatures you control gain trample and get +X/+X until end of turn, where X is the amount of life you gained this turn.
        Ability ability = new AttacksTriggeredAbility(new GainLifeEffect(2), false);
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("Then creatures you control gain trample"));
        ability.addEffect(new BoostControlledEffect(
                ControllerGainedLifeCount.instance, ControllerGainedLifeCount.instance, Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES, false, true
        ).setText("and get +X/+X until end of turn, where X is the amount of life you gained this turn"));
        this.addAbility(ability.addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private BlossomingBogbeast(final BlossomingBogbeast card) {
        super(card);
    }

    @Override
    public BlossomingBogbeast copy() {
        return new BlossomingBogbeast(this);
    }
}
