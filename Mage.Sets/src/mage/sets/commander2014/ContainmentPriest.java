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
package mage.sets.commander2014;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class ContainmentPriest extends CardImpl {

    public ContainmentPriest(UUID ownerId) {
        super(ownerId, 5, "Containment Priest", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "C14";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // If a nontoken creature would enter the battlefield and it wasn't cast, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ContainmentPriestReplacementEffect()), new CreatureCastWatcher());
    }

    public ContainmentPriest(final ContainmentPriest card) {
        super(card);
    }

    @Override
    public ContainmentPriest copy() {
        return new ContainmentPriest(this);
    }
}

class ContainmentPriestReplacementEffect extends ReplacementEffectImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard();

    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public ContainmentPriestReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.GainControl);
        staticText = "If a nontoken creature would enter the battlefield and it wasn't cast, exile it instead";
    }

    public ContainmentPriestReplacementEffect(final ContainmentPriestReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ContainmentPriestReplacementEffect copy() {
        return new ContainmentPriestReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (controller != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, zEvent.getFromZone(), true);
            }
            return true;

        }
        return false;
    }
    
    @Override    
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }
    

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.BATTLEFIELD) {
            Card card = game.getCard(event.getTargetId());
            if (card.getCardType().contains(CardType.CREATURE)) { // TODO: Bestow Card cast as Enchantment probably not handled correctly
                CreatureCastWatcher watcher = (CreatureCastWatcher) game.getState().getWatchers().get("CreatureWasCast");
                if (watcher != null && !watcher.wasCreatureCastThisTurn(event.getTargetId())) {
                    return true;
                }
            }
        }
        return false;
    }
}

class CreatureCastWatcher extends Watcher {

    private final Set<UUID> creaturesCasted = new HashSet<>();

    public CreatureCastWatcher() {
        super("CreatureWasCast", WatcherScope.GAME);
    }

    public CreatureCastWatcher(final CreatureCastWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
         if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null) {
                Card card = game.getCard(spell.getSourceId());
                if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                    creaturesCasted.add(card.getId());
                }
            }
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
            && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                creaturesCasted.remove(card.getId());
            }
        }
    }

    public boolean wasCreatureCastThisTurn(UUID creatureSourceId) {
        return creaturesCasted.contains(creatureSourceId);
    }

    @Override
    public void reset() {
        super.reset();
        creaturesCasted.clear();
    }

    @Override
    public CreatureCastWatcher copy() {
        return new CreatureCastWatcher(this);
    }
}
