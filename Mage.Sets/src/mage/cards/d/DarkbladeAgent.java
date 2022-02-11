package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DarkbladeAgent extends CardImpl {

    public DarkbladeAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // As long as you've surveilled this turn, Darkblade Agent has deathtouch and "Whenever this creature deals combat damage to a player, draw a card."
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                DeathtouchAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ), DarkbladeAgentCondition.instance,
                        "As long as you've surveilled this turn, "
                                + "{this} has deathtouch"
                )
        );
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        new DealsCombatDamageToAPlayerTriggeredAbility(
                                new DrawCardSourceControllerEffect(1), false
                        ), Duration.WhileOnBattlefield
                ), DarkbladeAgentCondition.instance,
                "and \"Whenever this creature deals "
                        + "combat damage to a player, you draw a card.\""
        ));
        this.addAbility(ability, new DarkbladeAgentWatcher());
    }

    private DarkbladeAgent(final DarkbladeAgent card) {
        super(card);
    }

    @Override
    public DarkbladeAgent copy() {
        return new DarkbladeAgent(this);
    }
}

enum DarkbladeAgentCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        DarkbladeAgentWatcher watcher
                = game.getState().getWatcher(
                DarkbladeAgentWatcher.class);
        return watcher != null
                && watcher.getSurveiledThisTurn(source.getControllerId());
    }
}

class DarkbladeAgentWatcher extends Watcher {

    private final Set<UUID> surveiledThisTurn = new HashSet<>();

    public DarkbladeAgentWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SURVEILED) {
            this.surveiledThisTurn.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.surveiledThisTurn.clear();
    }

    public boolean getSurveiledThisTurn(UUID playerId) {
        return this.surveiledThisTurn.contains(playerId);
    }
}
