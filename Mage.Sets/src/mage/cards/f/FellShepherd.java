
package mage.cards.f;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
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
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-2, -2, Duration.EndOfTurn), new ManaCostsImpl("{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, false)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public FellShepherd(final FellShepherd card) {
        super(card);
    }

    @Override
    public FellShepherd copy() {
        return new FellShepherd(this);
    }
}

class FellShepherdWatcher extends Watcher {

    private Set<UUID> creatureIds = new HashSet<>();

    public FellShepherdWatcher() {
        super(WatcherScope.PLAYER);
        condition = true;
    }

    public FellShepherdWatcher(final FellShepherdWatcher watcher) {
        super(watcher);
        this.creatureIds.addAll(watcher.creatureIds);
    }

    @Override
    public FellShepherdWatcher copy() {
        return new FellShepherdWatcher(this);
    }

    public Set<UUID> getCreaturesIds() {
        return creatureIds;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            MageObject card = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (card != null && ((Card) card).isOwnedBy(this.controllerId) && card.isCreature()) {
                creatureIds.add(card.getId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creatureIds.clear();
    }
}

class FellShepherdEffect extends OneShotEffect {

    public FellShepherdEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return to your hand all creature cards that were put into your graveyard from the battlefield this turn";
    }

    public FellShepherdEffect(final FellShepherdEffect effect) {
        super(effect);
    }

    @Override
    public FellShepherdEffect copy() {
        return new FellShepherdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FellShepherdWatcher watcher = game.getState().getWatcher(FellShepherdWatcher.class, source.getControllerId());
        if (watcher != null) {
            StringBuilder sb = new StringBuilder();
            for (UUID creatureId : watcher.getCreaturesIds()) {
                if (game.getState().getZone(creatureId) == Zone.GRAVEYARD) {
                    Card card = game.getCard(creatureId);
                    if (card != null) {
                        card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                        sb.append(' ').append(card.getName());
                    }
                }
            }
            if (sb.length() > 0) {
                sb.insert(0, "Fell Shepherd - returning to hand:");
                game.informPlayers(sb.toString());
            }
            return true;
        }
        return false;
    }
}
