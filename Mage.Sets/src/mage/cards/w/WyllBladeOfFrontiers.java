package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.OneOrMoreDiceRolledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.RollDiceEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WyllBladeOfFrontiers extends CardImpl {

    public WyllBladeOfFrontiers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // If you would roll one or more dice, instead roll that many dice plus one and ignore the lowest roll.
        this.addAbility(new SimpleStaticAbility(new WyllBladeOfFrontiersEffect()));

        // Whenever you roll one or more dice, put a +1/+1 counter on Wyll, Blade of Frontiers.
        this.addAbility(new OneOrMoreDiceRolledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private WyllBladeOfFrontiers(final WyllBladeOfFrontiers card) {
        super(card);
    }

    @Override
    public WyllBladeOfFrontiers copy() {
        return new WyllBladeOfFrontiers(this);
    }
}

class WyllBladeOfFrontiersEffect extends ReplacementEffectImpl {

    WyllBladeOfFrontiersEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if you would roll one or more dice, " +
                "instead roll that many dice plus one and ignore the lowest roll";
    }

    private WyllBladeOfFrontiersEffect(final WyllBladeOfFrontiersEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        RollDiceEvent rdEvent = (RollDiceEvent) event;
        rdEvent.incAmount(1);
        rdEvent.incIgnoreLowestAmount(1);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DICE
                && ((RollDiceEvent) event).getRollDieType() == RollDieType.NUMERICAL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public WyllBladeOfFrontiersEffect copy() {
        return new WyllBladeOfFrontiersEffect(this);
    }
}
