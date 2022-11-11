package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.PowerstoneToken;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VisionsOfPhyrexia extends CardImpl {

    public VisionsOfPhyrexia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // At the beginning of your upkeep, exile the top card of your library. You may play that card this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1), TargetController.YOU, false
        ));

        // At the beginning of your end step, if you didn't play a card from exile this turn, create a tapped Powerstone token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new PowerstoneToken(), 1, true),
                TargetController.YOU, VisionsOfPhyrexiaCondition.instance, false
        ), new VisionsOfPhyrexiaConditionWatcher());
    }

    private VisionsOfPhyrexia(final VisionsOfPhyrexia card) {
        super(card);
    }

    @Override
    public VisionsOfPhyrexia copy() {
        return new VisionsOfPhyrexia(this);
    }
}

enum VisionsOfPhyrexiaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return !VisionsOfPhyrexiaConditionWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "you didn't play a card from exile this turn";
    }
}

class VisionsOfPhyrexiaConditionWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    VisionsOfPhyrexiaConditionWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case LAND_PLAYED:
                if (Zone.EXILED.match(event.getZone())) {
                    playerSet.add(event.getPlayerId());
                }
                return;
            case SPELL_CAST:
                Spell spell = game.getSpell(event.getTargetId());
                if (spell != null && Zone.EXILED.match(spell.getFromZone())) {
                    playerSet.add(spell.getControllerId());
                }
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(VisionsOfPhyrexiaConditionWatcher.class)
                .playerSet
                .add(source.getControllerId());
    }
}
