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
package mage.cards.c;

import mage.MageInt;
import mage.constants.ComparisonType;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class ConduitOfRuin extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a colorless creature card with converted mana cost 7 or greater");
    private static final FilterCreatureCard filterCost = new FilterCreatureCard("The first creature spell");

    static {
        filter.add(new ColorlessPredicate());
        filter.add(new ConvertedManaCostPredicate(ComparisonType.MORE_THAN, 6));
        filterCost.add(new FirstCastCreatureSpellPredicate());
    }

    public ConduitOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}");
        this.subtype.add("Eldrazi");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When you cast Conduit of Ruin, you may search your library for a colorless creature card with converted mana cost 7 or greater, then shuffle your library and put that card on top of it.
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        this.addAbility(new CastSourceTriggeredAbility(new SearchLibraryPutOnLibraryEffect(target, true, true), true));

        // The first creature spell you cast each turn costs {2} less to cast.
        Effect effect = new SpellsCostReductionControllerEffect(filterCost, 2);
        effect.setText("The first creature spell you cast each turn costs {2} less to cast");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect), new ConduitOfRuinWatcher());
    }

    public ConduitOfRuin(final ConduitOfRuin card) {
        super(card);
    }

    @Override
    public ConduitOfRuin copy() {
        return new ConduitOfRuin(this);
    }
}

class ConduitOfRuinWatcher extends Watcher {

    Map<UUID, Integer> playerCreatureSpells;
    int spellCount = 0;

    public ConduitOfRuinWatcher() {
        super("FirstCreatureSpellCastThisTurn", WatcherScope.GAME);
        playerCreatureSpells = new HashMap<>();
    }

    public ConduitOfRuinWatcher(final ConduitOfRuinWatcher watcher) {
        super(watcher);
        this.playerCreatureSpells = new HashMap<>();
        playerCreatureSpells.putAll(watcher.playerCreatureSpells);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null && spell.isCreature()) {
                if (playerCreatureSpells.containsKey(event.getPlayerId())) {
                    playerCreatureSpells.put(event.getPlayerId(), playerCreatureSpells.get(event.getPlayerId()) + 1);
                } else {
                    playerCreatureSpells.put(event.getPlayerId(), 1);
                }
            }
        }
    }

    public int creatureSpellsCastThisTurn(UUID playerId) {
        if (playerCreatureSpells.containsKey(playerId)) {
            return playerCreatureSpells.get(playerId);
        }
        return 0;
    }

    @Override
    public ConduitOfRuinWatcher copy() {
        return new ConduitOfRuinWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        playerCreatureSpells.clear();
    }
}

class FirstCastCreatureSpellPredicate implements ObjectPlayerPredicate<ObjectPlayer<Controllable>> {

    @Override
    public boolean apply(ObjectPlayer<Controllable> input, Game game) {
        if (input.getObject() instanceof Spell
                && ((Spell) input.getObject()).isCreature()) {
            ConduitOfRuinWatcher watcher = (ConduitOfRuinWatcher) game.getState().getWatchers().get("FirstCreatureSpellCastThisTurn");
            return watcher != null && watcher.creatureSpellsCastThisTurn(input.getPlayerId()) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "The first creature spell you cast each turn";
    }
}
