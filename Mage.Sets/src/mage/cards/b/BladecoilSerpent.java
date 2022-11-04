package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.EachTwoManaSpentToCastValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BladecoilSerpent extends CardImpl {

    public BladecoilSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{X}{6}");

        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Bladecoil Serpent enters the battlefield, for each {U}{U} spent to cast it, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(EachTwoManaSpentToCastValue.BLUE)
                        .setText("for each {U}{U} spent to cast it, draw a card")
        ));

        // When Bladecoil Serpent enters the battlefield, for each {B}{B} spent to cast it, each opponent discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(
                EachTwoManaSpentToCastValue.BLACK, false, TargetController.OPPONENT
        ).setText("for each {B}{B} spent to cast it, each opponent discards a card")));

        // When Bladecoil Serpent enters the battlefield, for each {R}{R} spent to cast it, it gets +1/+0 and gains trample and haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostSourceEffect(
                EachTwoManaSpentToCastValue.RED, StaticValue.get(0), Duration.EndOfTurn
        ).setText("for each {R}{R} spent to cast it, it gets +1/+0"));
        ability.addEffect(new ConditionalOneShotEffect(new AddContinuousEffectToGame(
                new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
        ), BladecoilSerpentCondition.instance, "and gains trample"));
        ability.addEffect(new ConditionalOneShotEffect(new AddContinuousEffectToGame(
                new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
        ), BladecoilSerpentCondition.instance, "and haste until end of turn"));
    }

    private BladecoilSerpent(final BladecoilSerpent card) {
        super(card);
    }

    @Override
    public BladecoilSerpent copy() {
        return new BladecoilSerpent(this);
    }
}

enum BladecoilSerpentCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return EachTwoManaSpentToCastValue.RED.calculate(game, source, null) > 0;
    }
}
