
package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;


/**
 *
 * @author LevelX2
 */
public final class TapestryOfTheAges extends CardImpl {

    public TapestryOfTheAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {2}, {T}: Draw a card. Activate this ability only if you've cast a noncreature spell this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, 
                new DrawCardSourceControllerEffect(1), 
                new ManaCostsImpl<>("{2}"), 
                PlayerCastNonCreatureSpellCondition.instance);
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new PlayerCastNonCreatureSpellWatcher());                       

    }

    private TapestryOfTheAges(final TapestryOfTheAges card) {
        super(card);
    }

    @Override
    public TapestryOfTheAges copy() {
        return new TapestryOfTheAges(this);
    }
}

enum PlayerCastNonCreatureSpellCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerCastNonCreatureSpellWatcher watcher = game.getState().getWatcher(PlayerCastNonCreatureSpellWatcher.class);
        return watcher != null && watcher.playerDidCastNonCreatureSpellThisTurn(source.getControllerId());
    }
    
    @Override
    public String toString() {
        return "you've cast a noncreature spell this turn";
    }
}

class PlayerCastNonCreatureSpellWatcher extends Watcher {

    private Set<UUID> playerIds = new HashSet<>();

    public PlayerCastNonCreatureSpellWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (!spell.isCreature(game)) {
                playerIds.add(spell.getControllerId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerIds.clear();
    }

    public boolean playerDidCastNonCreatureSpellThisTurn(UUID playerId) {
        return playerIds.contains(playerId);
    }
}
