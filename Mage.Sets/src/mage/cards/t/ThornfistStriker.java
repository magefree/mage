package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThornfistStriker extends CardImpl {

    public ThornfistStriker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}")));

        // Infusion -- Creatures you control get +1/+0 and have trample as long as you gained life this turn.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield),
                YouGainedLifeCondition.getZero(), "creatures you control get +1/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityAllEffect(
                        TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ), YouGainedLifeCondition.getZero(), "and have trample as long as you gained life this turn"
        ));
        this.addAbility(ability
                .setAbilityWord(AbilityWord.INFUSION)
                .addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private ThornfistStriker(final ThornfistStriker card) {
        super(card);
    }

    @Override
    public ThornfistStriker copy() {
        return new ThornfistStriker(this);
    }
}
