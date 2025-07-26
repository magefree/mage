package mage.cards.c;

import mage.MageItem;
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
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.command.Commander;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.SubTypes;

import java.util.List;
import java.util.UUID;

/**
 * @author bunchOfDevs
 */
public final class Conspiracy extends CardImpl {

    public Conspiracy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // As Conspiracy enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Neutral)));

        // Creature cards you own that aren't on the battlefield, creature 
        // spells you control, and creatures you control are the chosen type.
        this.addAbility(new SimpleStaticAbility(new ConspiracyEffect()));

    }

    private Conspiracy(final Conspiracy card) {
        super(card);
    }

    @Override
    public Conspiracy copy() {
        return new Conspiracy(this);
    }

    static class ConspiracyEffect extends ContinuousEffectImpl {

        private ConspiracyEffect() {
            super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
            staticText = "Creatures you control are the chosen type. The same is "
                    + "true for creature spells you control and creature cards "
                    + "you own that aren't on the battlefield.";

            this.dependendToTypes.add(DependencyType.BecomeCreature);  // Opalescence and Starfield of Nyx
        }

        private ConspiracyEffect(final ConspiracyEffect effect) {
            super(effect);
        }

        @Override
        public ConspiracyEffect copy() {
            return new ConspiracyEffect(this);
        }

        @Override
        public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
            SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
            for (MageItem object : affectedObjects) {
                if (object instanceof Permanent) {
                    Permanent permanent = (Permanent) object;
                    permanent.removeAllSubTypes(game);
                    permanent.addSubType(game, subType);
                } else {
                    setCreatureSubtype((MageObject) object, subType, game);
                }
            }
        }

        @Override
        public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
            Player controller = game.getPlayer(source.getControllerId());
            SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
            if (controller == null || subType == null) {
                return true;
            }
            // Creature cards you own that aren't on the battlefield
            // in graveyard
            for (UUID cardId : controller.getGraveyard()) {
                Card card = game.getCard(cardId);
                if (card != null && card.isCreature(game)) {
                    affectedObjects.add(card);
                }
            }
            // on Hand
            for (UUID cardId : controller.getHand()) {
                Card card = game.getCard(cardId);
                if (card != null && card.isCreature(game)) {
                    affectedObjects.add(card);
                }
            }
            // in Exile
            for (Card card : game.getState().getExile().getAllCards(game, controller.getId())) {
                if (card.isOwnedBy(controller.getId()) && card.isCreature(game)) {
                    affectedObjects.add(card);
                }
            }
            // in Library (e.g. for Mystical Teachings)
            for (Card card : controller.getLibrary().getCards(game)) {
                if (card.isOwnedBy(controller.getId()) && card.isCreature(game)) {
                    affectedObjects.add(card);
                }
            }
            // in command zone
            for (CommandObject commandObject : game.getState().getCommand()) {
                if (commandObject instanceof Commander) {
                    Card card = game.getCard(commandObject.getId());
                    if (card != null && card.isCreature(game) && card.isOwnedBy(controller.getId())) {
                        affectedObjects.add(card);
                    }
                }
            }
            // creature spells you control
            for (StackObject stackObject : game.getStack()) {
                if (stackObject instanceof Spell
                        && stackObject.isControlledBy(controller.getId())
                        && stackObject.isCreature(game)) {
                    affectedObjects.add(stackObject);
                    affectedObjects.add(((Spell) stackObject).getCard());
                }
            }
            // creatures you control
            List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(controller.getId());
            for (Permanent permanent : permanents) {
                if (permanent.isCreature(game)) {
                    affectedObjects.add(permanent);
                }
            }
            return !affectedObjects.isEmpty();
        }

        private void setCreatureSubtype(MageObject object, SubType subtype, Game game) {
            if (object == null) {
                return;
            }
            SubTypes subTypes = game.getState().getCreateMageObjectAttribute(object, game).getSubtype();
            subTypes.setIsAllCreatureTypes(false);
            subTypes.removeAll(SubType.getCreatureTypes());
            subTypes.add(subtype);
        }
    }

}
