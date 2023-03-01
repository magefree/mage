package mage.cards.a;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public final class ArchfiendOfTheDross extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.OIL, 0, 0);

    public ArchfiendOfTheDross(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Archfiend of the Dross enters the battlefield with four oil counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance(4)),
                "with four oil counters on it"
        ));

        // At the beginning of your upkeep, remove an oil counter from Archfiend of the Dross. Then if it has no oil counters on it, you lose the game.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.OIL.createInstance()),
                TargetController.YOU, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new LoseGameSourceControllerEffect(), condition,
                "Then if it has no oil counters on it, you lose the game"
        ));
        this.addAbility(ability);

        // Whenever a creature an opponent controls dies, its controller loses 2 life.
        this.addAbility(new DiesCreatureTriggeredAbility(new ArchfiendOfTheDrossEffect(), false));
    }

    private ArchfiendOfTheDross(final ArchfiendOfTheDross card) {
        super(card);
    }

    @Override
    public ArchfiendOfTheDross copy() {
        return new ArchfiendOfTheDross(this);
    }
}

class ArchfiendOfTheDrossEffect extends OneShotEffect {

    ArchfiendOfTheDrossEffect() {
        super(Outcome.Benefit);
        staticText = "its controller loses 2 life";
    }

    private ArchfiendOfTheDrossEffect(final ArchfiendOfTheDrossEffect effect) {
        super(effect);
    }

    @Override
    public ArchfiendOfTheDrossEffect copy() {
        return new ArchfiendOfTheDrossEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(getValue("creatureDied"))
                .filter(Objects::nonNull)
                .map(Permanent.class::cast)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .ifPresent(player -> player.loseLife(2, game, source, false));
        return true;
    }
}
