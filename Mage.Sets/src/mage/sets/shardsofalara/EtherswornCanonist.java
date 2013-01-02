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
package mage.sets.shardsofalara;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author Plopman
 */
public class EtherswornCanonist extends CardImpl<EtherswornCanonist> {

    public EtherswornCanonist(UUID ownerId) {
        super(ownerId, 10, "Ethersworn Canonist", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each player who has cast a nonartifact spell this turn can't cast additional nonartifact spells.
        this.addWatcher(new EtherswornCanonistWatcher());
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new EtherswornCanonistReplacementEffect()));
    }

    public EtherswornCanonist(final EtherswornCanonist card) {
        super(card);
    }

    @Override
    public EtherswornCanonist copy() {
        return new EtherswornCanonist(this);
    }
}

class EtherswornCanonistWatcher extends WatcherImpl<EtherswornCanonistWatcher> {

    private Map<UUID, Boolean> castNonartifactSpell = new HashMap<UUID, Boolean>();
    
    public EtherswornCanonistWatcher() {
        super("EtherswornCanonistWatcher", Constants.WatcherScope.GAME);
    }

    public EtherswornCanonistWatcher(final EtherswornCanonistWatcher watcher) {
        super(watcher);
        for (Map.Entry<UUID, Boolean> entry: watcher.castNonartifactSpell.entrySet()) {
            castNonartifactSpell.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Card card = game.getCard(event.getSourceId());
            if(card != null && !card.getCardType().contains(CardType.ARTIFACT)){
                UUID playerId = event.getPlayerId();
                if (playerId != null) {
                    castNonartifactSpell.put(playerId, true);
                }
            }
        }
    }

    @Override
    public void reset() {
        castNonartifactSpell.clear();
    }
    
    public boolean castNonArtifactSpell(UUID player){
        Boolean value = castNonartifactSpell.get(player);
        return value != null && value;
    }


    @Override
    public EtherswornCanonistWatcher copy() {
        return new EtherswornCanonistWatcher(this);
    }

}

class EtherswornCanonistReplacementEffect extends ReplacementEffectImpl<EtherswornCanonistReplacementEffect> {

    public EtherswornCanonistReplacementEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Detriment);
        staticText = "Each player who has cast a nonartifact spell this turn can't cast additional nonartifact spells";
    }

    public EtherswornCanonistReplacementEffect(final EtherswornCanonistReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public EtherswornCanonistReplacementEffect copy() {
        return new EtherswornCanonistReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            EtherswornCanonistWatcher watcher = (EtherswornCanonistWatcher)game.getState().getWatchers().get("EtherswornCanonistWatcher");
            Card card = game.getCard(event.getSourceId());
            if (card != null && !card.getCardType().contains(CardType.ARTIFACT) && watcher.castNonArtifactSpell(event.getPlayerId())) {  
                return true;
            }
        }
        return false;
    }

}