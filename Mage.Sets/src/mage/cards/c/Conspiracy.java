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

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author anonymous
 */
public class Conspiracy extends CardImpl {

    public Conspiracy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{B}");

        // As Conspiracy enters the battlefield, choose a creature type.
        this.addAbility(new EntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Neutral)));
        // Creature cards you own that aren't on the battlefield, creature spells you control, and creatures you control are the chosen type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConspiracyEffect()));
    }

    public Conspiracy(final Conspiracy card) {
        super(card);
    }

    @Override
    public Conspiracy copy() {
        return new Conspiracy(this);
    }
}

class ConspiracyEffect extends ContinuousEffectImpl {

    public ConspiracyEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Creature cards you own that aren't on the battlefield, creature spells you control, and creatures you control are the chosen type";
    }

    public ConspiracyEffect(final ConspiracyEffect effect) {
        super(effect);
    }

    @Override
    public ConspiracyEffect copy() {
        return new ConspiracyEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        String choice = (String) game.getState().getValue(source.getSourceId().toString() + "_type");
        if (controller != null && choice != null) {
            // Creature cards you own that aren't on the battlefield
            // in graveyard
            for (UUID cardId : controller.getGraveyard()) {
                Card card = game.getCard(cardId);
                if (card.getCardType().contains(CardType.CREATURE)) {
                    setCreatureSubtype(card, choice, game);
                }
            }
            // on Hand
            for (UUID cardId : controller.getHand()) {
                Card card = game.getCard(cardId);
                if (card.getCardType().contains(CardType.CREATURE)) {
                    setCreatureSubtype(card, choice, game);
                }
            }
            // in Exile
            for (Card card : game.getState().getExile().getAllCards(game)) {
                if (card.getOwnerId().equals(controller.getId()) && card.getCardType().contains(CardType.CREATURE)) {
                    setCreatureSubtype(card, choice, game);
                }
            }
            // in Library (e.g. for Mystical Teachings)
            for (Card card : controller.getLibrary().getCards(game)) {
                if (card.getOwnerId().equals(controller.getId()) && card.getCardType().contains(CardType.CREATURE)) {
                    setCreatureSubtype(card, choice, game);
                }
            }
            // commander in command zone
            for (UUID commanderId : controller.getCommandersIds()) {
                if (game.getState().getZone(commanderId) == Zone.COMMAND) {
                    Card card = game.getCard(commanderId);
                    if (card.getCardType().contains(CardType.CREATURE)) {
                        setCreatureSubtype(card, choice, game);
                    }
                }
            }
            // creature spells you control
            for (Iterator<StackObject> iterator = game.getStack().iterator(); iterator.hasNext();) {
                StackObject stackObject = iterator.next();
                if (stackObject instanceof Spell &&
                        stackObject.getControllerId().equals(source.getControllerId()) &&
                        stackObject.getCardType().contains(CardType.CREATURE)) {
                    Card card = ((Spell) stackObject).getCard();
                    setCreatureSubtype(card, choice, game);
                }
            }
            // creatures you control
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(
                    new FilterControlledCreaturePermanent(), source.getControllerId(), game);
            for (Permanent creature : creatures) {
                setCreatureSubtype(creature, choice, game);
            }
            return true;
        }
        return false;
    }

    private void setCreatureSubtype(MageObject object, String subtype, Game game) {
        if (object != null) {
            if (object instanceof Card) {
                Card card = (Card) object;
                setChosenSubtype(
                        game.getState().getCreateCardAttribute(card).getSubtype(),
                        subtype);
            } else {
                setChosenSubtype(object.getSubtype(game), subtype);
            }
        }
    }

    private void setChosenSubtype(List<String> subtype, String choice) {
        if (subtype.size() != 1 ||  !subtype.contains(choice)) {
            subtype.removeAll(CardRepository.instance.getCreatureTypes());
            subtype.add(choice);
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }
}
