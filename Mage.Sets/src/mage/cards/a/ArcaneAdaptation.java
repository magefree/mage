
package mage.cards.a;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
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

/**
 *
 * @author TheElk801
 */
public final class ArcaneAdaptation extends CardImpl {

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
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (controller != null && subType != null) {
            // Creature cards you own that aren't on the battlefield
            // in graveyard
            for (UUID cardId : controller.getGraveyard()) {
                Card card = game.getCard(cardId);
                if (card.isCreature() && !card.hasSubtype(subType, game)) {
                    game.getState().getCreateCardAttribute(card, game).getSubtype().add(subType);
                }
            }
            // on Hand
            for (UUID cardId : controller.getHand()) {
                Card card = game.getCard(cardId);
                if (card.isCreature() && !card.hasSubtype(subType, game)) {
                    game.getState().getCreateCardAttribute(card, game).getSubtype().add(subType);
                }
            }
            // in Exile
            for (Card card : game.getState().getExile().getAllCards(game)) {
                if (card.isCreature() && !card.hasSubtype(subType, game)) {
                    game.getState().getCreateCardAttribute(card, game).getSubtype().add(subType);
                }
            }
            // in Library (e.g. for Mystical Teachings)
            for (Card card : controller.getLibrary().getCards(game)) {
                if (card.isOwnedBy(controller.getId()) && card.isCreature() && !card.hasSubtype(subType, game)) {
                    game.getState().getCreateCardAttribute(card, game).getSubtype().add(subType);
                }
            }
            // commander in command zone
            for (UUID commanderId : controller.getCommandersIds()) {
                if (game.getState().getZone(commanderId) == Zone.COMMAND) {
                    Card card = game.getCard(commanderId);
                    if (card.isCreature() && !card.hasSubtype(subType, game)) {
                        game.getState().getCreateCardAttribute(card, game).getSubtype().add(subType);
                    }
                }
            }
            // creature spells you control
            for (Iterator<StackObject> iterator = game.getStack().iterator(); iterator.hasNext();) {
                StackObject stackObject = iterator.next();
                if (stackObject instanceof Spell
                        && stackObject.isControlledBy(source.getControllerId())
                        && stackObject.isCreature()
                        && !stackObject.hasSubtype(subType, game)) {
                    Card card = ((Spell) stackObject).getCard();
                    game.getState().getCreateCardAttribute(card, game).getSubtype().add(subType);
                }
            }
            // creatures you control
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(
                    new FilterControlledCreaturePermanent(), source.getControllerId(), game);
            for (Permanent creature : creatures) {
                if (creature != null && !creature.hasSubtype(subType, game)) {
                    creature.getSubtype(game).add(subType);
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
