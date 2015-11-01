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
package mage.sets.alarareborn;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CascadeAbility;
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
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public class MaelstromNexus extends CardImpl {

    public MaelstromNexus(UUID ownerId) {
        super(ownerId, 130, "Maelstrom Nexus", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}{B}{R}{G}");
        this.expansionSetCode = "ARB";

        // The first spell you cast each turn has cascade.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MaelstromNexusGainCascadeFirstSpellEffect()), new FirstSpellCastThisTurnWatcher());

    }

    public MaelstromNexus(final MaelstromNexus card) {
        super(card);
    }

    @Override
    public MaelstromNexus copy() {
        return new MaelstromNexus(this);
    }
}

class MaelstromNexusGainCascadeFirstSpellEffect extends ContinuousEffectImpl {

    private Ability cascadeAbility = new CascadeAbility();

    public MaelstromNexusGainCascadeFirstSpellEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "The first spell you cast each turn has cascade";
    }

    public MaelstromNexusGainCascadeFirstSpellEffect(final MaelstromNexusGainCascadeFirstSpellEffect effect) {
        super(effect);
    }

    @Override
    public MaelstromNexusGainCascadeFirstSpellEffect copy() {
        return new MaelstromNexusGainCascadeFirstSpellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (StackObject stackObject : game.getStack()) {
                // only spells cast, so no copies of spells
                if ((stackObject instanceof Spell) && !stackObject.isCopy() && stackObject.getControllerId().equals(source.getControllerId())) {
                    Spell spell = (Spell) stackObject;
                    FirstSpellCastThisTurnWatcher watcher = (FirstSpellCastThisTurnWatcher) game.getState().getWatchers().get("FirstSpellCastThisTurn");
                    if (watcher != null && spell.getId().equals(watcher.getIdOfFirstCastSpell(source.getControllerId()))) {
                        game.getState().addOtherAbility(spell.getCard(), cascadeAbility);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class FirstSpellCastThisTurnWatcher extends Watcher {

    Map<UUID, UUID> playerFirstSpellCast = new HashMap<>();
    Map<UUID, UUID> playerFirstCastSpell = new HashMap<>();

    public FirstSpellCastThisTurnWatcher() {
        super("FirstSpellCastThisTurn", WatcherScope.GAME);
    }

    public FirstSpellCastThisTurnWatcher(final FirstSpellCastThisTurnWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
            case CAST_SPELL:
                Spell spell = (Spell) game.getObject(event.getTargetId());
                if (spell != null && !playerFirstSpellCast.containsKey(spell.getControllerId())) {
                    if (event.getType().equals(EventType.SPELL_CAST)) {
                        playerFirstSpellCast.put(spell.getControllerId(), spell.getId());
                    } else if (event.getType().equals(EventType.CAST_SPELL)) {
                        playerFirstCastSpell.put(spell.getControllerId(), spell.getId());
                    }

                }
        }
    }

    @Override
    public FirstSpellCastThisTurnWatcher copy() {
        return new FirstSpellCastThisTurnWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        playerFirstSpellCast.clear();
        playerFirstCastSpell.clear();
    }

    public UUID getIdOfFirstCastSpell(UUID playerId) {
        if (playerFirstSpellCast.get(playerId) == null) {
            return playerFirstCastSpell.get(playerId);
        } else {
            return playerFirstSpellCast.get(playerId);
        }
    }
}
