package mage.cards.c;

import mage.MageObject;
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
import mage.util.SubTypeList;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author anonymous
 */
public final class Conspiracy extends CardImpl {

    public Conspiracy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // As Conspiracy enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Neutral)));
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
        staticText = "Creatures you control are the chosen type. The same is true for creature spells you control and creature cards you own that aren't on the battlefield.";
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
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (controller != null && subType != null) {
            // Creature cards you own that aren't on the battlefield
            // in graveyard
            for (UUID cardId : controller.getGraveyard()) {
                Card card = game.getCard(cardId);
                if (card != null && card.isCreature()) {
                    setCreatureSubtype(card, subType, game);
                }
            }
            // on Hand
            for (UUID cardId : controller.getHand()) {
                Card card = game.getCard(cardId);
                if (card != null && card.isCreature()) {
                    setCreatureSubtype(card, subType, game);
                }
            }
            // in Exile
            for (Card card : game.getState().getExile().getAllCards(game)) {
                if (card.isOwnedBy(controller.getId()) && card.isCreature()) {
                    setCreatureSubtype(card, subType, game);
                }
            }
            // in Library (e.g. for Mystical Teachings)
            for (Card card : controller.getLibrary().getCards(game)) {
                if (card.isOwnedBy(controller.getId()) && card.isCreature()) {
                    setCreatureSubtype(card, subType, game);
                }
            }
            // commander in command zone
            for (UUID commanderId : game.getCommandersIds(controller)) {
                if (game.getState().getZone(commanderId) == Zone.COMMAND) {
                    Card card = game.getCard(commanderId);
                    if (card != null && card.isCreature()) {
                        setCreatureSubtype(card, subType, game);
                    }
                }
            }
            // creature spells you control
            for (Iterator<StackObject> iterator = game.getStack().iterator(); iterator.hasNext(); ) {
                StackObject stackObject = iterator.next();
                if (stackObject instanceof Spell
                        && stackObject.isControlledBy(source.getControllerId())
                        && stackObject.isCreature()) {
                    Card card = ((Spell) stackObject).getCard();
                    setCreatureSubtype(card, subType, game);
                }
            }
            // creatures you control
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(
                    new FilterControlledCreaturePermanent(), source.getControllerId(), game);
            for (Permanent creature : creatures) {
                setCreatureSubtype(creature, subType, game);
            }
            return true;
        }
        return false;
    }

    private void setCreatureSubtype(MageObject object, SubType subtype, Game game) {
        if (object != null) {
            if (object instanceof Card) {
                Card card = (Card) object;
                setChosenSubtype(
                        game.getState().getCreateCardAttribute(card, game).getSubtype(),
                        subtype);
            } else {
                setChosenSubtype(object.getSubtype(game), subtype);
            }
        }
    }

    private void setChosenSubtype(SubTypeList subtype, SubType choice) {
        if (subtype.size() != 1 || !subtype.contains(choice)) {
            subtype.clear();
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
