package mage.cards.c;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
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
import mage.util.SubTypeList;

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
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConspiracyEffect()));

    }

    public Conspiracy(final Conspiracy card) {
        super(card);
    }

    @Override
    public Conspiracy copy() {
        return new Conspiracy(this);
    }

    static class ConspiracyEffect extends ContinuousEffectImpl {

        public ConspiracyEffect() {
            super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
            staticText = "Creatures you control are the chosen type. The same is "
                    + "true for creature spells you control and creature cards "
                    + "you own that aren't on the battlefield.";
            
            this.dependendToTypes.add(DependencyType.BecomeCreature);  // Opalescence and Starfield of Nyx
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
            switch (layer) {
                case TypeChangingEffects_4:
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
                        // in command zone
                        for (CommandObject commandObject : game.getState().getCommand()) {
                            if (commandObject instanceof Commander) {
                                Card card = game.getCard(((Commander) commandObject).getId());
                                if (card != null && card.isCreature() && card.isOwnedBy(controller.getId())) {
                                    setCreatureSubtype(card, subType, game);
                                }
                            }
                        }                            
                        // creature spells you control
                        for (Iterator<StackObject> iterator = game.getStack().iterator(); iterator.hasNext();) {
                            StackObject stackObject = iterator.next();
                            if (stackObject instanceof Spell
                                    && stackObject.isControlledBy(controller.getId())
                                    && stackObject.isCreature()) {
                                setCreatureSubtype(stackObject, subType, game);
                                setCreatureSubtype(((Spell) stackObject).getCard(), subType, game);
                            }
                        }
                        // creatures you control
                        List<Permanent> permanents = game.getState().getBattlefield().getAllActivePermanents(controller.getId());
                        for (Permanent permanent : permanents) {
                            if (permanent.isCreature()) {
                                setCreatureSubtype(permanent, subType, game);
                            }
                        }
                    }
                    return true;
            }
            return false;
        }

        private void setCreatureSubtype(MageObject object, SubType subtype, Game game) {
            if (object != null) {
                setChosenSubtype(game.getState()
                    .getCreateMageObjectAttribute(object, game).getSubtype(), subtype);
            }
        }

        private void setChosenSubtype(SubTypeList subtype, SubType choice) {
            if (subtype.size() != 1 
                    || !subtype.contains(choice)) {
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
}
