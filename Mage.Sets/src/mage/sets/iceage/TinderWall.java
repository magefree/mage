/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.iceage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author Plopman
 */
public class TinderWall extends CardImpl<TinderWall> {

    public TinderWall(UUID ownerId) {
        super(ownerId, 158, "Tinder Wall", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "ICE";
        this.subtype.add("Plant");
        this.subtype.add("Wall");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Sacrifice Tinder Wall: Add {R}{R} to your mana pool.
        this.addAbility(new SimpleManaAbility(Constants.Zone.BATTLEFIELD, new BasicManaEffect(Mana.RedMana(2)), new SacrificeSourceCost()));
        // {R}, Sacrifice Tinder Wall: Tinder Wall deals 2 damage to target creature it's blocking.
        FilterAttackingCreature filter = new FilterAttackingCreature("creature it's blocking");
        filter.add(new BlockingByPredicate(this.getId()));
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl("{R}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
        
        
        this.addWatcher(new BlockedByWatcher());
    }

    public TinderWall(final TinderWall card) {
        super(card);
    }

    @Override
    public TinderWall copy() {
        return new TinderWall(this);
    }
}


class BlockedByWatcher extends WatcherImpl<BlockedByWatcher> {

    public List<UUID> blockedByWatcher = new ArrayList<UUID>();

    public BlockedByWatcher() {
        super("BlockedByWatcher", Constants.WatcherScope.CARD);
    }

    public BlockedByWatcher(final BlockedByWatcher watcher) {
        super(watcher);
        this.blockedByWatcher = watcher.blockedByWatcher;
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

class BlockingByPredicate implements Predicate<Permanent> {

    private UUID source;
    
    public BlockingByPredicate(UUID source) {
        this.source = source;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        BlockedByWatcher watcher = (BlockedByWatcher) game.getState().getWatchers().get("BlockedByWatcher", source);
        
        for(UUID uuid :watcher.blockedByWatcher){
            Permanent creature = game.getPermanent(uuid);
            if(creature != null && creature.getId().equals(input.getId())){
                return true;
            }
        }
        return false;
        
    }

    @Override
    public String toString() {
        return "creature it's blocking";
    }
}