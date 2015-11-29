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
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public class MishraArtificerProdigy extends CardImpl {

    public MishraArtificerProdigy(UUID ownerId) {
        super(ownerId, 243, "Mishra, Artificer Prodigy", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");
        this.expansionSetCode = "TSP";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Artificer");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast an artifact spell, you may search your graveyard, hand, and/or library for a card with the same name as that spell and put it onto the battlefield. If you search your library this way, shuffle it.
        this.addAbility(new MishraArtificerProdigyTriggeredAbility());
    }

    public MishraArtificerProdigy(final MishraArtificerProdigy card) {
        super(card);
    }

    @Override
    public MishraArtificerProdigy copy() {
        return new MishraArtificerProdigy(this);
    }
}

class MishraArtificerProdigyTriggeredAbility extends TriggeredAbilityImpl {

    MishraArtificerProdigyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MishraArtificerProdigyEffect(), true);
    }

    MishraArtificerProdigyTriggeredAbility(final MishraArtificerProdigyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MishraArtificerProdigyTriggeredAbility copy() {
        return new MishraArtificerProdigyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.getCardType().contains(CardType.ARTIFACT)) {
                ((MishraArtificerProdigyEffect) this.getEffects().get(0)).setName(spell.getName());
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an artifact spell, you may search your graveyard, hand, and/or library for a card with the same name as that spell and put it onto the battlefield. If you search your library this way, shuffle it.";
    }
}

class MishraArtificerProdigyEffect extends OneShotEffect {

    private String cardName;

    MishraArtificerProdigyEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Search your graveyard, hand, and/or library for a card named <i>" + cardName + "</i> and put it onto the battlefield. If you search your library this way, shuffle it.";
    }

    MishraArtificerProdigyEffect(final MishraArtificerProdigyEffect effect) {
        super(effect);
        this.cardName = effect.cardName;
    }

    @Override
    public MishraArtificerProdigyEffect copy() {
        return new MishraArtificerProdigyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterCard filter = new FilterCard("card named " + this.cardName);
            filter.add(new NamePredicate(cardName));
            Card card = null;
            // Graveyard
            if (controller.chooseUse(Outcome.Neutral, "Search your graveyard?", source, game)) {
                // You can't fail to find the card in your graveyard because it's not hidden
                TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(1, 1, filter);
                if (controller.choose(Outcome.PutCardInPlay, controller.getGraveyard(), target, game)) {
                    card = game.getCard(target.getFirstTarget());
                }
            }
            // Hand
            if (card == null && controller.chooseUse(Outcome.Neutral, "Search your hand?", source, game)) {
                TargetCardInHand target = new TargetCardInHand(0, 1, filter);
                if (controller.choose(Outcome.PutCardInPlay, controller.getHand(), target, game)) {
                    card = game.getCard(target.getFirstTarget());
                }
            }
            // Library
            if (card == null && controller.chooseUse(Outcome.Neutral, "Search your library?", source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
                if (controller.searchLibrary(target, game)) {
                    card = game.getCard(target.getFirstTarget());
                }
                controller.shuffleLibrary(game);
            }
            // Put on battlefield
            if (card != null) {
                controller.moveCards(card, null, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }

    public void setName(String cardName) {
        this.cardName = cardName;
    }
}
