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
package mage.cards.i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class IndomitableCreativity extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and/or creatures");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.CREATURE)));
    }

    public IndomitableCreativity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}{R}");

        // Destroy X target artifacts and/or creatures. For each permanent destroyed this way, its controller reveals cards from the top of his or her library until an artifact or creature card is revealed and exiles that card. Those players put the exiled card onto the battlefield, then shuffle their libraries.
        getSpellAbility().addEffect(new IndomitableCreativityEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public IndomitableCreativity(final IndomitableCreativity card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            ability.addTarget(new TargetPermanent(xValue, xValue, filter, false));
        }
    }

    @Override
    public IndomitableCreativity copy() {
        return new IndomitableCreativity(this);
    }
}

class IndomitableCreativityEffect extends OneShotEffect {

    public IndomitableCreativityEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy X target artifacts and/or creatures. For each permanent destroyed this way, its controller reveals cards from the top of his or her library until an artifact or creature card is revealed and exiles that card. Those players put the exiled card onto the battlefield, then shuffle their libraries";
    }

    public IndomitableCreativityEffect(final IndomitableCreativityEffect effect) {
        super(effect);
    }

    @Override
    public IndomitableCreativityEffect copy() {
        return new IndomitableCreativityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            ArrayList<Permanent> destroyedPermanents = new ArrayList<>();
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent target = game.getPermanent(targetId);
                if (target != null) {
                    if (target.destroy(source.getSourceId(), game, false)) {
                        destroyedPermanents.add(target);
                    }
                }
            }
            HashMap<Player, Cards> cardsToReveal = new HashMap<>();

            for (Permanent permanent : destroyedPermanents) {
                Player controllerOfDestroyedCreature = game.getPlayer(permanent.getControllerId());
                if (controllerOfDestroyedCreature != null) {
                    Library library = controllerOfDestroyedCreature.getLibrary();
                    if (library.size() > 0) {
                        Cards cards = new CardsImpl();
                        Cards revealCards;
                        if (cardsToReveal.containsKey(controllerOfDestroyedCreature)) {
                            revealCards = cardsToReveal.get(controllerOfDestroyedCreature);
                        } else {
                            revealCards = new CardsImpl();
                            cardsToReveal.put(controllerOfDestroyedCreature, revealCards);
                        }
                        Card card = library.removeFromTop(game);
                        cards.add(card);
                        while (!card.getCardType().contains(CardType.CREATURE) && !card.getCardType().contains(CardType.ARTIFACT) && library.size() > 0) {
                            card = library.removeFromTop(game);
                            cards.add(card);
                        }

                        if (card.getCardType().contains(CardType.CREATURE) || card.getCardType().contains(CardType.ARTIFACT)) {
                            controllerOfDestroyedCreature.moveCards(card, Zone.BATTLEFIELD, source, game);
                        }

                        if (!cards.isEmpty()) {
                            revealCards.addAll(cards);
                            Set<Card> cardsToShuffle = cards.getCards(game);
                            cardsToShuffle.remove(card);
                            library.addAll(cardsToShuffle, game);

                        }
                        controllerOfDestroyedCreature.shuffleLibrary(source, game);
                    }
                }
            }
            // reveal cards at the end (because a player can have x permanents to be destroyed
            for (Player player : cardsToReveal.keySet()) {
                player.revealCards(sourceObject.getIdName(), cardsToReveal.get(player), game);
            }
            return true;
        }
        return false;
    }
}
