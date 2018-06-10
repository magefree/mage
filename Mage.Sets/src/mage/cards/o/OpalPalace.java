
package mage.cards.o;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.CommanderColorIdentityManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public final class OpalPalace extends CardImpl {

    public OpalPalace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {1}, {tap}: Add one mana of any color in your commander's color identity. If you spend this mana to cast your commander, it enters the battlefield with a number of +1/+1 counters on it equal to the number of times it's been cast from the command zone this game.
        Ability ability = new CommanderColorIdentityManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new OpalPalaceWatcher(ability.getOriginalId().toString()));

        ability = new SimpleStaticAbility(Zone.ALL, new OpalPalaceEntersBattlefieldEffect());
        ability.setRuleVisible(false);
        this.addAbility(ability);

    }

    public OpalPalace(final OpalPalace card) {
        super(card);
    }

    @Override
    public OpalPalace copy() {
        return new OpalPalace(this);
    }
}

class OpalPalaceWatcher extends Watcher {

    public List<UUID> commanderId = new ArrayList<>();
    private final String originalId;

    public OpalPalaceWatcher(String originalId) {
        super(OpalPalaceWatcher.class.getSimpleName(), WatcherScope.CARD);
        this.originalId = originalId;
    }

    public OpalPalaceWatcher(final OpalPalaceWatcher watcher) {
        super(watcher);
        this.commanderId.addAll(watcher.commanderId);
        this.originalId = watcher.originalId;
    }

    @Override
    public OpalPalaceWatcher copy() {
        return new OpalPalaceWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            if (event.getData() != null && event.getData().equals(originalId)) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null) {
                    Card card = spell.getCard();
                    if (card != null) {
                        for (UUID playerId : game.getPlayerList()) {
                            Player player = game.getPlayer(playerId);
                            if (player != null) {
                                if (player.getCommandersIds().contains(card.getId())) {
                                    commanderId.add(card.getId());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        commanderId.clear();
    }
}

class OpalPalaceEntersBattlefieldEffect extends ReplacementEffectImpl {

    public OpalPalaceEntersBattlefieldEffect() {
        super(Duration.EndOfGame, Outcome.BoostCreature, false);
        staticText = "If you spend this mana to cast your commander, it enters the battlefield with a number of +1/+1 counters on it equal to the number of times it's been cast from the command zone this game";
    }

    public OpalPalaceEntersBattlefieldEffect(OpalPalaceEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        OpalPalaceWatcher watcher = (OpalPalaceWatcher) game.getState().getWatchers().get(OpalPalaceWatcher.class.getSimpleName(), source.getSourceId());
        return watcher != null
                && watcher.commanderId.contains(event.getTargetId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null) {
            Integer castCount = (Integer) game.getState().getValue(permanent.getId() + "_castCount");
            if (castCount != null && castCount > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(castCount), source, game);
            }
        }
        return false;
    }

    @Override
    public OpalPalaceEntersBattlefieldEffect copy() {
        return new OpalPalaceEntersBattlefieldEffect(this);
    }

}
