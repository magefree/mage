package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.OrCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;
import mage.watchers.common.BlockedAttackerWatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author noahg
 */
public final class SeaTroll extends CardImpl {

    public SeaTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {U}: Regenerate Sea Troll. Activate this ability only if Sea Troll blocked or was blocked by a blue creature this turn.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{U}"), new SeaTrollCondition());
        ability.addWatcher(new SeaTrollWatcher());
        this.addAbility(ability);
    }

    public SeaTroll(final SeaTroll card) {
        super(card);
    }

    @Override
    public SeaTroll copy() {
        return new SeaTroll(this);
    }
}

class SeaTrollWatcher extends Watcher {

    private final Set<MageObjectReference> blockedOrBlockedByBlueThisTurnCreatures;

    public SeaTrollWatcher() {
        super(SeaTrollWatcher.class.getSimpleName(), WatcherScope.GAME);
        blockedOrBlockedByBlueThisTurnCreatures = new HashSet<>();
    }

    public SeaTrollWatcher(SeaTrollWatcher watcher) {
        super(watcher);
        this.blockedOrBlockedByBlueThisTurnCreatures = new HashSet<>(watcher.blockedOrBlockedByBlueThisTurnCreatures);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            MageObjectReference blocker = new MageObjectReference(event.getSourceId(), game);
            MageObjectReference attacker = new MageObjectReference(event.getTargetId(), game);
            if (blocker.getPermanentOrLKIBattlefield(game).getColor(game).isBlue()){
                blockedOrBlockedByBlueThisTurnCreatures.add(attacker);
            }
            if (attacker.getPermanentOrLKIBattlefield(game).getColor(game).isBlue()){
                blockedOrBlockedByBlueThisTurnCreatures.add(blocker);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        blockedOrBlockedByBlueThisTurnCreatures.clear();
    }

    public boolean blockedOrBlockedByBlueCreatureThisTurn(MageObjectReference creature){
        return blockedOrBlockedByBlueThisTurnCreatures.contains(creature);
    }

    @Override
    public SeaTrollWatcher copy() {
        return new SeaTrollWatcher(this);
    }
}

class SeaTrollCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null){
            SeaTrollWatcher watcher = (SeaTrollWatcher) game.getState().getWatchers().get(SeaTrollWatcher.class.getSimpleName());
            if (watcher != null) {
                return watcher.blockedOrBlockedByBlueCreatureThisTurn(new MageObjectReference(sourcePermanent, game));
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{this} blocked or was blocked by a blue creature this turn";
    }
}