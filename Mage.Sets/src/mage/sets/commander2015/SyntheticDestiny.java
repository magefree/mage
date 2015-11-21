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
package mage.sets.commander2015;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SyntheticDestiny extends CardImpl {

    public SyntheticDestiny(UUID ownerId) {
        super(ownerId, 15, "Synthetic Destiny", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");
        this.expansionSetCode = "C15";

        // Exile all creatures you control. At the beginning of the next end step, reveal cards from the top of your library until you reveal that many creature cards, put all creature cards revealed this way onto the battlefield, then shuffle the rest of the revealed cards into your library.
        getSpellAbility().addEffect(new SyntheticDestinyEffect());
    }

    public SyntheticDestiny(final SyntheticDestiny card) {
        super(card);
    }

    @Override
    public SyntheticDestiny copy() {
        return new SyntheticDestiny(this);
    }
}

class SyntheticDestinyEffect extends OneShotEffect {

    public SyntheticDestinyEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile all creatures you control. At the beginning of the next end step, reveal cards from the top of your library until you reveal that many creature cards, put all creature cards revealed this way onto the battlefield, then shuffle the rest of the revealed cards into your library";
    }

    public SyntheticDestinyEffect(final SyntheticDestinyEffect effect) {
        super(effect);
    }

    @Override
    public SyntheticDestinyEffect copy() {
        return new SyntheticDestinyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cardsToExile = new HashSet<>();
            cardsToExile.addAll(game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), controller.getId(), game));
            controller.moveCards(cardsToExile, Zone.EXILED, source, game);
            //Delayed ability
            Effect effect = new SyntheticDestinyDelayedEffect(cardsToExile.size());
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            delayedAbility.setSourceObject(source.getSourceObject(game), game);
            game.addDelayedTriggeredAbility(delayedAbility);

            return true;
        }
        return false;
    }
}

class SyntheticDestinyDelayedEffect extends OneShotEffect {

    protected int numberOfCards;

    public SyntheticDestinyDelayedEffect(int numberOfCards) {
        super(Outcome.PutCreatureInPlay);
        this.numberOfCards = numberOfCards;
        this.staticText = "reveal cards from the top of your library until you reveal that many creature cards, put all creature cards revealed this way onto the battlefield, then shuffle the rest of the revealed cards into your library";
    }

    public SyntheticDestinyDelayedEffect(final SyntheticDestinyDelayedEffect effect) {
        super(effect);
        this.numberOfCards = effect.numberOfCards;
    }

    @Override
    public SyntheticDestinyDelayedEffect copy() {
        return new SyntheticDestinyDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Cards revealed = new CardsImpl();
            Set<Card> creatureCards = new LinkedHashSet<>();
            Cards nonCreatureCards = new CardsImpl();
            while (creatureCards.size() < numberOfCards && controller.getLibrary().size() > 0) {
                Card card = controller.getLibrary().removeFromTop(game);
                revealed.add(card);
                if (card.getCardType().contains(CardType.CREATURE)) {
                    creatureCards.add(card);
                } else {
                    nonCreatureCards.add(card);
                }
            }
            controller.revealCards(sourceObject.getIdName(), revealed, game);
            controller.moveCards(creatureCards, Zone.BATTLEFIELD, source, game, false, false, true, null);
            controller.putCardsOnTopOfLibrary(nonCreatureCards, game, source, false);
            controller.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}
