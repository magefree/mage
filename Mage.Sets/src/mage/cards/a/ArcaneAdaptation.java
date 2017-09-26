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
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public class ArcaneAdaptation extends CardImpl {

    public ArcaneAdaptation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // As Arcane Adaptation enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Neutral)));
        // Creatures you control are the chosen type in addition to their other types. The same is true for creature spells you control and creature cards you own that aren't on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConspyEffect()));
    }

    public ArcaneAdaptation(final ArcaneAdaptation card) {
        super(card);
    }

    @Override
    public ArcaneAdaptation copy() {
        return new ArcaneAdaptation(this);
    }
}

class ConspyEffect extends ContinuousEffectImpl {

    public ConspyEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures you control are the chosen type in addition to their other types. The same is true for creature spells you control and creature cards you own that aren't on the battlefield";
    }

    public ConspyEffect(final ConspyEffect effect) {
        super(effect);
    }

    @Override
    public ConspyEffect copy() {
        return new ConspyEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        SubType choice = (SubType) game.getState().getValue(source.getSourceId().toString() + "_type");
        if (controller != null && choice != null) {
            // Creature cards you own that aren't on the battlefield
            // in graveyard
            for (UUID cardId : controller.getGraveyard()) {
                Card card = game.getCard(cardId);
                if (card.isCreature() && !card.hasSubtype(choice, game)) {
                    for (SubType s : card.getSubtype(game)) {
                        game.getState().getCreateCardAttribute(card).getSubtype().add(s);
                    }
                    game.getState().getCreateCardAttribute(card).getSubtype().add(choice);
                }
            }
            // on Hand
            for (UUID cardId : controller.getHand()) {
                Card card = game.getCard(cardId);
                if (card.isCreature() && !card.hasSubtype(choice, game)) {
                    for (SubType s : card.getSubtype(game)) {
                        game.getState().getCreateCardAttribute(card).getSubtype().add(s);
                    }
                    game.getState().getCreateCardAttribute(card).getSubtype().add(choice);
                }
            }
            // in Exile
            for (Card card : game.getState().getExile().getAllCards(game)) {
                if (card.isCreature() && !card.hasSubtype(choice, game)) {
                    for (SubType s : card.getSubtype(game)) {
                        game.getState().getCreateCardAttribute(card).getSubtype().add(s);
                    }
                    game.getState().getCreateCardAttribute(card).getSubtype().add(choice);
                }
            }
            // in Library (e.g. for Mystical Teachings)
            for (Card card : controller.getLibrary().getCards(game)) {
                if (card.getOwnerId().equals(controller.getId()) && card.isCreature() && !card.hasSubtype(choice, game)) {
                    for (SubType s : card.getSubtype(game)) {
                        game.getState().getCreateCardAttribute(card).getSubtype().add(s);
                    }
                    game.getState().getCreateCardAttribute(card).getSubtype().add(choice);
                }
            }
            // commander in command zone
            for (UUID commanderId : controller.getCommandersIds()) {
                if (game.getState().getZone(commanderId) == Zone.COMMAND) {
                    Card card = game.getCard(commanderId);
                    if (card.isCreature() && !card.hasSubtype(choice, game)) {
                        for (SubType s : card.getSubtype(game)) {
                            game.getState().getCreateCardAttribute(card).getSubtype().add(s);
                        }
                        game.getState().getCreateCardAttribute(card).getSubtype().add(choice);
                    }
                }
            }
            // creature spells you control
            for (Iterator<StackObject> iterator = game.getStack().iterator(); iterator.hasNext();) {
                StackObject stackObject = iterator.next();
                if (stackObject instanceof Spell
                        && stackObject.getControllerId().equals(source.getControllerId())
                        && stackObject.isCreature()
                        && !stackObject.hasSubtype(choice, game)) {
                    Card card = ((Spell) stackObject).getCard();
                    for (SubType s : card.getSubtype(game)) {
                        game.getState().getCreateCardAttribute(card).getSubtype().add(s);
                    }
                    game.getState().getCreateCardAttribute(card).getSubtype().add(choice);
                }
            }
            // creatures you control
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(
                    new FilterControlledCreaturePermanent(), source.getControllerId(), game);
            for (Permanent creature : creatures) {
                if (creature != null && !creature.hasSubtype(choice, game)) {
                    creature.getSubtype(game).add(choice);
                }
            }
            return true;
        }

        return false;
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
