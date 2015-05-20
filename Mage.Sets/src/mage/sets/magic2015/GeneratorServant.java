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
package mage.sets.magic2015;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author Quercitron
 */
public class GeneratorServant extends CardImpl {

    public GeneratorServant(UUID ownerId) {
        super(ownerId, 143, "Generator Servant", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "M15";
        this.subtype.add("Elemental");

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}, Sacrifice Generator Servant: Add {2} to your mana pool.  If that mana is spent on a creature spell, it gains haste until end of turn.
        Mana mana = Mana.ColorlessMana(2);
        mana.setFlag(true); // used to indicate this mana ability
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, mana, new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.getEffects().get(0).setText("Add {2} to your mana pool. If that mana is spent on a creature spell, it gains haste until end of turn.");
        this.addAbility(ability);
        
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new GeneratorServantHasteEffect()), new GeneratorServantWatcher());
    }

    public GeneratorServant(final GeneratorServant card) {
        super(card);
    }

    @Override
    public GeneratorServant copy() {
        return new GeneratorServant(this);
    }
}

class GeneratorServantWatcher extends Watcher {

    public List<UUID> creatures = new ArrayList<>();
    
    public GeneratorServantWatcher() {
        super("GeneratorServantWatcher", WatcherScope.CARD);
    }
    
    public GeneratorServantWatcher(final GeneratorServantWatcher watcher) {
        super(watcher);
        this.creatures.addAll(watcher.creatures);
    }

    @Override
    public GeneratorServantWatcher copy() {
        return new GeneratorServantWatcher(this);
    }
    
    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAYED) {
            MageObject target = game.getObject(event.getTargetId());
            MageObject source = game.getObject(this.getSourceId());
            if (event.getSourceId() == this.getSourceId() && target != null && target.getCardType().contains(CardType.CREATURE) && event.getFlag()) {
                if (target instanceof Spell) {
                    this.creatures.add(((Spell) target).getCard().getId());
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creatures.clear();
    }
    
}

class GeneratorServantHasteEffect extends ContinuousEffectImpl {

    public GeneratorServantHasteEffect() {
        super(Duration.EndOfGame, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }
    
    public GeneratorServantHasteEffect(final GeneratorServantHasteEffect effect) {
        super(effect);
    }

    @Override
    public ContinuousEffect copy() {
        return new GeneratorServantHasteEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        GeneratorServantWatcher watcher = (GeneratorServantWatcher) game.getState().getWatchers().get("GeneratorServantWatcher", source.getSourceId());
        if (watcher != null) {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents()) {
                if (watcher.creatures.contains(perm.getId())) {
                    perm.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
                }
            }
            return true;
        }
        return false;
    }
    
}
