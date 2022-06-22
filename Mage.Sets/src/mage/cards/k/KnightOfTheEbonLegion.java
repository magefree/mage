package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightOfTheEbonLegion extends CardImpl {

    public KnightOfTheEbonLegion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {2}{B}: Knight of the Ebon Legion gets +3/+3 and gains deathtouch until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(
                3, 3, Duration.EndOfTurn
        ).setText("{this} gets +3/+3"), new ManaCostsImpl<>("{2}{B}"));
        ability.addEffect(new GainAbilitySourceEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains deathtouch until end of turn"));
        this.addAbility(ability);

        // At the beginning of your end step, if a player lost 4 or more life this turn, put a +1/+1 counter on Knight of the Ebon Legion.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        TargetController.YOU, false
                ), KnightOfTheEbonLegionCondition.instance, "At the beginning of your end step, " +
                "if a player lost 4 or more life this turn, put a +1/+1 counter on {this}."
        ));
    }

    private KnightOfTheEbonLegion(final KnightOfTheEbonLegion card) {
        super(card);
    }

    @Override
    public KnightOfTheEbonLegion copy() {
        return new KnightOfTheEbonLegion(this);
    }
}

enum KnightOfTheEbonLegionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (watcher == null) {
            return false;
        }
        return game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .anyMatch(uuid -> watcher.getLifeLost(uuid) > 3);
    }
}