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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlayer;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author LevelX2
 */
public class JayaBallardTaskMage extends CardImpl<JayaBallardTaskMage> {

    private static final FilterPermanent filter = new FilterPermanent("blue permanent");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }
    
    public JayaBallardTaskMage(UUID ownerId) {
        super(ownerId, 166, "Jaya Ballard, Task Mage", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "TSP";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Spellshaper");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}, {tap}, Discard a card: Destroy target blue permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{R}"));
        ability.addTarget(new TargetPermanent(filter, true));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());        
        this.addAbility(ability);
        
        // {1}{R}, {tap}, Discard a card: Jaya Ballard, Task Mage deals 3 damage to target creature or player. A creature dealt damage this way can't be regenerated this turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new ManaCostsImpl("{1}{R}"));
        ability.addTarget(new TargetCreatureOrPlayer(true));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost()); 
        ability.addEffect(new CantRegenerateEffect());
        this.addAbility(ability);
        this.addWatcher(new DamagedByWatcher());                
        
        // {5}{R}{R}, {tap}, Discard a card: Jaya Ballard deals 6 damage to each creature and each player.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageEverythingEffect(6), new ManaCostsImpl("{5}{R}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());        
        this.addAbility(ability);
        
    }

    public JayaBallardTaskMage(final JayaBallardTaskMage card) {
        super(card);
    }

    @Override
    public JayaBallardTaskMage copy() {
        return new JayaBallardTaskMage(this);
    }
}

class CantRegenerateEffect extends ReplacementEffectImpl<CantRegenerateEffect> {

    public CantRegenerateEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "A creature dealt damage this way can't be regenerated this turn";
    }

    public CantRegenerateEffect(final CantRegenerateEffect effect) {
        super(effect);
    }

    @Override
    public CantRegenerateEffect copy() {
        return new CantRegenerateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.REGENERATE) {
            DamagedByWatcher watcher = (DamagedByWatcher) game.getState().getWatchers().get("DamagedByWatcher", source.getSourceId());
            if (watcher != null) {
                return watcher.damagedCreatures.contains(event.getTargetId());
            } 
        }
        return false;
    }

}
