
package mage.cards.t;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
 * @author Plopman
 */
public final class TinderWall extends CardImpl {

    public TinderWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Sacrifice Tinder Wall: Add {R}{R}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(2), new SacrificeSourceCost()));
        // {R}, Sacrifice Tinder Wall: Tinder Wall deals 2 damage to target creature it's blocking.
        FilterAttackingCreature filter = new FilterAttackingCreature("creature it's blocking");
        filter.add(new BlockedByIdPredicate(this.getId()));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl("{R}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability, new BlockedByWatcher());
    }

    public TinderWall(final TinderWall card) {
        super(card);
    }

    @Override
    public TinderWall copy() {
        return new TinderWall(this);
    }
}

class BlockedByWatcher extends Watcher {

    public List<UUID> blockedByWatcher = new ArrayList<>();

    public BlockedByWatcher() {
        super("BlockedByWatcher", WatcherScope.CARD);
    }

    public BlockedByWatcher(final BlockedByWatcher watcher) {
        super(watcher);
        this.blockedByWatcher.addAll(watcher.blockedByWatcher);
    }

    @Override
    public BlockedByWatcher copy() {
        return new BlockedByWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            if (sourceId.equals(event.getSourceId()) && !blockedByWatcher.contains(event.getTargetId())) {
                blockedByWatcher.add(event.getTargetId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        blockedByWatcher.clear();
    }

}
