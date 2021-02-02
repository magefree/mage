package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StormFleetSwashbuckler extends CardImpl {

    public StormFleetSwashbuckler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ascend
        this.addAbility(new AscendAbility());

        // Storm Fleet Swashbuckler has double strike as long as you have the city's blessing.
        ContinuousEffect boostSource = new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield);
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(boostSource, CitysBlessingCondition.instance,
                "{this} has double strike as long as you have the city's blessing");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect).addHint(CitysBlessingHint.instance);
        this.addAbility(ability);

    }

    private StormFleetSwashbuckler(final StormFleetSwashbuckler card) {
        super(card);
    }

    @Override
    public StormFleetSwashbuckler copy() {
        return new StormFleetSwashbuckler(this);
    }
}
