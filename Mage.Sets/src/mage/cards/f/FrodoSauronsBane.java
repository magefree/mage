package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasSubtypeCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.watchers.common.TemptedByTheRingWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrodoSauronsBane extends CardImpl {

    private static final Condition condition1 = new SourceHasSubtypeCondition(SubType.CITIZEN);
    private static final Condition condition2 = new SourceHasSubtypeCondition(SubType.SCOUT);

    public FrodoSauronsBane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {W/B}{W/B}: If Frodo, Sauron's Bane is a Citizen, it becomes a Halfling Scout with base power and toughness 2/3 and lifelink.
        this.addAbility(new SimpleActivatedAbility(
                new ConditionalOneShotEffect(new AddContinuousEffectToGame(
                        new AddCardSubTypeSourceEffect(Duration.Custom, SubType.HALFLING, SubType.SCOUT),
                        new SetBasePowerToughnessSourceEffect(2, 3, Duration.Custom, SubLayer.SetPT_7b),
                        new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.Custom)
                ), condition1, "if {this} is a Citizen, it becomes a Halfling Scout with base power and toughness 2/3 and lifelink"),
                new ManaCostsImpl<>("{W/B}{W/B}")
        ));

        // {B}{B}{B}: If Frodo is a Scout, it becomes a Halfling Rogue with "Whenever this creature deals combat damage to a player, that player loses the game if the Ring has tempted you four or more times this game. Otherwise, the Ring tempts you."
        this.addAbility(new SimpleActivatedAbility(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(
                        new AddCardSubTypeSourceEffect(Duration.Custom, SubType.HALFLING, SubType.ROGUE),
                        new GainAbilitySourceEffect(new DealsCombatDamageToAPlayerTriggeredAbility(
                                new ConditionalOneShotEffect(
                                        new LoseGameTargetPlayerEffect(), new TheRingTemptsYouEffect(),
                                        FrodoSauronsBaneCondition.instance, "that player loses the game " +
                                        "if the Ring has tempted you four or more times this game. Otherwise, the Ring tempts you"
                                ), false, true), Duration.Custom)
                ), condition2, "if {this} is a Scout, it becomes a Halfling Rogue with " +
                "\"Whenever this creature deals combat damage to a player, that player loses the game " +
                "if the Ring has tempted you four or more times this game. Otherwise, the Ring tempts you.\""
        ), new ManaCostsImpl<>("{B}{B}{B}")));
    }

    private FrodoSauronsBane(final FrodoSauronsBane card) {
        super(card);
    }

    @Override
    public FrodoSauronsBane copy() {
        return new FrodoSauronsBane(this);
    }
}

enum FrodoSauronsBaneCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return TemptedByTheRingWatcher.getCount(source.getControllerId(), game) >= 4;
    }
}
