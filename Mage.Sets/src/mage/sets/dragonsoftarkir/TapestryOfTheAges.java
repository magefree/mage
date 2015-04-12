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
package mage.sets.dragonsoftarkir;

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
import mage.constants.CardType;
import mage.constants.Rarity;
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
public class TapestryOfTheAges extends CardImpl {

    public TapestryOfTheAges(UUID ownerId) {
        super(ownerId, 246, "Tapestry of the Ages", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT},"{4}" );
        this.expansionSetCode = "DTK";

        // {2}, {T}: Draw a card. Activate this ability only if you've cast a noncreature spell this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, 
                new DrawCardSourceControllerEffect(1), 
                new ManaCostsImpl<>("{2}"), 
                PlayerCastNonCreatureSpellCondition.getInstance());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new PlayerCastNonCreatureSpellWatcher());                       

    }

    public TapestryOfTheAges(final TapestryOfTheAges card) {
        super(card);
    }

    @Override
    public TapestryOfTheAges copy() {
        return new TapestryOfTheAges(this);
    }
}

class PlayerCastNonCreatureSpellCondition implements Condition {
    private final static PlayerCastNonCreatureSpellCondition fInstance = new PlayerCastNonCreatureSpellCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerCastNonCreatureSpellWatcher watcher = (PlayerCastNonCreatureSpellWatcher) game.getState().getWatchers().get("PlayerCastNonCreatureSpell");
        return watcher != null && watcher.playerDidCastNonCreatureSpellThisTurn(source.getControllerId());
    }
    
    @Override
    public String toString() {
        return "you've cast a noncreature spell this turn";
    }
}

class PlayerCastNonCreatureSpellWatcher extends Watcher {

    Set<UUID> playerIds = new HashSet<>();

    public PlayerCastNonCreatureSpellWatcher() {
        super("PlayerCastNonCreatureSpell", WatcherScope.GAME);
    }

    public PlayerCastNonCreatureSpellWatcher(final PlayerCastNonCreatureSpellWatcher watcher) {
        super(watcher);
        this.playerIds.addAll(watcher.playerIds);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (!spell.getCardType().contains(CardType.CREATURE)) {
                playerIds.add(spell.getControllerId());
            }
        }
    }

    @Override
    public PlayerCastNonCreatureSpellWatcher copy() {
        return new PlayerCastNonCreatureSpellWatcher(this);
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
