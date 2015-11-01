/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.PermanentToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class KnowledgePool extends CardImpl {

    public KnowledgePool(UUID ownerId) {
        super(ownerId, 111, "Knowledge Pool", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{6}");
        this.expansionSetCode = "MBS";

        // Imprint - When Knowledge Pool enters the battlefield, each player exiles the top three cards of his or her library
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KnowledgePoolEffect1(), false));

        // Whenever a player casts a spell from his or her hand, that player exiles it. If the player does, he or she may cast another nonland card exiled with Knowledge Pool without paying that card's mana cost.
        this.addAbility(new KnowledgePoolAbility());
    }

    public KnowledgePool(final KnowledgePool card) {
        super(card);
    }

    @Override
    public KnowledgePool copy() {
        return new KnowledgePool(this);
    }

}

class KnowledgePoolEffect1 extends OneShotEffect {

    public KnowledgePoolEffect1() {
        super(Outcome.Neutral);
        staticText = "each player exiles the top three cards of his or her library";
    }

    public KnowledgePoolEffect1(final KnowledgePoolEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        for (UUID playerId : controller.getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.moveCardsToExile(player.getLibrary().getTopCards(game, 3), source, game, true,
                        CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()),
                        sourceObject.getIdName() + " (" + sourceObject.getZoneChangeCounter(game) + ")");
            }
        }
        return true;
    }

    @Override
    public KnowledgePoolEffect1 copy() {
        return new KnowledgePoolEffect1(this);
    }

}

class KnowledgePoolAbility extends TriggeredAbilityImpl {

    public KnowledgePoolAbility() {
        super(Zone.BATTLEFIELD, new KnowledgePoolEffect2(), false);
    }

    public KnowledgePoolAbility(final KnowledgePoolAbility ability) {
        super(ability);
    }

    @Override
    public KnowledgePoolAbility copy() {
        return new KnowledgePoolAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getZone() == Zone.HAND) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

}

class KnowledgePoolEffect2 extends OneShotEffect {

    private static FilterNonlandCard filter = new FilterNonlandCard("nonland card exiled with Knowledge Pool");

    public KnowledgePoolEffect2() {
        super(Outcome.Neutral);
        staticText = "Whenever a player casts a spell from his or her hand, that player exiles it. If the player does, he or she may cast another nonland card exiled with {this} without paying that card's mana cost";
    }

    public KnowledgePoolEffect2(final KnowledgePoolEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && spell != null && sourceObject != null) {
            int zoneChangeCounter = (sourceObject instanceof PermanentToken) ? source.getSourceObjectZoneChangeCounter() : source.getSourceObjectZoneChangeCounter() - 1;
            UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), zoneChangeCounter);
            if (controller.moveCardsToExile(spell, source, game, true, exileZoneId, sourceObject.getIdName())) {
                Player player = game.getPlayer(spell.getControllerId());
                if (player != null && player.chooseUse(Outcome.PlayForFree, "Cast another nonland card exiled with " + sourceObject.getLogName() + " without paying that card's mana cost?", source, game)) {
                    TargetCardInExile target = new TargetCardInExile(filter, source.getSourceId());
                    while (player.choose(Outcome.PlayForFree, game.getExile().getExileZone(exileZoneId), target, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null && !card.getId().equals(spell.getSourceId())) {
                            game.getExile().removeCard(card, game);
                            return player.cast(card.getSpellAbility(), game, true);
                        }
                        target.clearChosen();
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public KnowledgePoolEffect2 copy() {
        return new KnowledgePoolEffect2(this);
    }

}
