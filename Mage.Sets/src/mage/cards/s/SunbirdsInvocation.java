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
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class SunbirdsInvocation extends CardImpl {

    public SunbirdsInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");

        // Whenever you cast a spell from your hand, reveal the top X cards of your library, where X is that spell's converted mana cost. You may cast a card revealed this way with converted mana cost X or less without paying its mana cost. Put the rest on the bottom of your library in a random order.
        this.addAbility(new SunbirdsInvocationTriggeredAbility());
    }

    public SunbirdsInvocation(final SunbirdsInvocation card) {
        super(card);
    }

    @Override
    public SunbirdsInvocation copy() {
        return new SunbirdsInvocation(this);
    }
}

class SunbirdsInvocationTriggeredAbility extends SpellCastControllerTriggeredAbility {

    public SunbirdsInvocationTriggeredAbility() {
        super(new SunbirdsInvocationEffect(), false);
    }

    public SunbirdsInvocationTriggeredAbility(SunbirdsInvocationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.getFromZone() == Zone.HAND) {
                if (spell.getCard() != null) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(spell.getId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public SunbirdsInvocationTriggeredAbility copy() {
        return new SunbirdsInvocationTriggeredAbility(this);
    }
}

class SunbirdsInvocationEffect extends OneShotEffect {

    public SunbirdsInvocationEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "reveal the top X cards of your library, where X is that spell's converted mana cost. You may cast a card revealed this way with converted mana cost X or less without paying its mana cost. Put the rest on the bottom of your library in a random order";
    }

    public SunbirdsInvocationEffect(final SunbirdsInvocationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
        int xValue = 0;
        if (spell == null) {
            spell = (Spell) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.STACK);
        }
        if (spell == null) {
            return false;
        }
        xValue = spell.getConvertedManaCost();
        Cards cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, xValue));
        if (!cards.isEmpty()) {
            controller.revealCards(sourceObject.getIdName(), cards, game);

            FilterCard filter = new FilterNonlandCard("card revealed this way with converted mana cost " + xValue + " or less");
            filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, xValue + 1));
            TargetCard target = new TargetCard(1, Zone.LIBRARY, filter);

            if (controller.chooseTarget(Outcome.PlayForFree, cards, target, source, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    if (controller.chooseUse(outcome, "Cast " + card.getLogName() + " without paying its mana cost?", source, game)) {
                        controller.cast(card.getSpellAbility(), game, true);
                        cards.remove(card);
                    }
                }
            }
        }

        while (!cards.isEmpty()) {
            Card card = cards.getRandom(game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
            }
        }

        return true;
    }

    @Override
    public SunbirdsInvocationEffect copy() {
        return new SunbirdsInvocationEffect(this);
    }
}
