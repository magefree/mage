
package mage.cards.f;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author LevelX2
 */
public final class FellShepherd extends CardImpl {

    public FellShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(8);
        this.toughness = new MageInt(6);

        // Whenever Fell Shepherd deals combat damage to a player, you may return to your hand all creature cards that were put into your graveyard from the battlefield this turn.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new FellShepherdEffect(), true), new FellShepherdWatcher());

        // {B}, Sacrifice another creature: Target creature gets -2/-2 until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
                new BoostTargetEffect(-2, -2, Duration.EndOfTurn), new ManaCostsImpl("{B}")
        );
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
        ));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private FellShepherd(final FellShepherd card) {
        super(card);
    }

    @Override
    public FellShepherd copy() {
        return new FellShepherd(this);
    }
}

class FellShepherdWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> playerMap = new HashMap();

    FellShepherdWatcher() {
        super(FellShepherdWatcher.class, WatcherScope.GAME);
    }

    private FellShepherdWatcher(final FellShepherdWatcher watcher) {
        super(watcher);
        this.playerMap.putAll(watcher.playerMap);
    }

    @Override
    public FellShepherdWatcher copy() {
        return new FellShepherdWatcher(this);
    }

    Set<MageObjectReference> getRefSet(UUID playerId) {
        return playerMap.get(playerId);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.isCreature()) {
                playerMap.putIfAbsent(card.getOwnerId(), new HashSet());
                playerMap.get(card.getOwnerId()).add(new MageObjectReference(card, game));
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }
}

class FellShepherdEffect extends OneShotEffect {

    FellShepherdEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return to your hand all creature cards that were put into your graveyard from the battlefield this turn";
    }

    private FellShepherdEffect(final FellShepherdEffect effect) {
        super(effect);
    }

    @Override
    public FellShepherdEffect copy() {
        return new FellShepherdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FellShepherdWatcher watcher = game.getState().getWatcher(FellShepherdWatcher.class);
        Player player = game.getPlayer(source.getControllerId());
        if (watcher == null || player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (MageObjectReference mor : watcher.getRefSet(source.getControllerId())) {
            Card card = mor.getCard(game);
            if (card != null) {
                cards.add(card);
            }
        }
        player.moveCards(cards, Zone.HAND, source, game);
        return true;
    }
}
