package mage.cards.a;

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
import mage.game.command.CommandObject;
import mage.game.command.Commander;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcaneAdaptation extends CardImpl {

    public ArcaneAdaptation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // As Arcane Adaptation enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Neutral)));

        // Creatures you control are the chosen type in addition to their other types. The same is true for creature spells you control and creature cards you own that aren't on the battlefield.
        this.addAbility(new SimpleStaticAbility(new ArcaneAdaptationEffect()));
    }

    private ArcaneAdaptation(final ArcaneAdaptation card) {
        super(card);
    }

    @Override
    public ArcaneAdaptation copy() {
        return new ArcaneAdaptation(this);
    }
}

class ArcaneAdaptationEffect extends ContinuousEffectImpl {

    ArcaneAdaptationEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Creatures you control are the chosen type in addition to their other types. " +
                "The same is true for creature spells you control and creature cards you own that aren't on the battlefield";
    }

    private ArcaneAdaptationEffect(final ArcaneAdaptationEffect effect) {
        super(effect);
    }

    @Override
    public ArcaneAdaptationEffect copy() {
        return new ArcaneAdaptationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (controller == null || subType == null) {
            return false;
        }
        // Creature cards you own that aren't on the battlefield
        // in graveyard
        for (UUID cardId : controller.getGraveyard()) {
            Card card = game.getCard(cardId);
            if (card != null && card.isCreature(game) && !card.hasSubtype(subType, game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }
        // on Hand
        for (UUID cardId : controller.getHand()) {
            Card card = game.getCard(cardId);
            if (card != null && card.isCreature(game) && !card.hasSubtype(subType, game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }
        // in Exile
        for (Card card : game.getState().getExile().getAllCards(game)) {
            if (card.isCreature(game) && !card.hasSubtype(subType, game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }
        // in Library (e.g. for Mystical Teachings)
        for (Card card : controller.getLibrary().getCards(game)) {
            if (card.isOwnedBy(controller.getId()) && card.isCreature(game) && !card.hasSubtype(subType, game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }
        // commander in command zone
        for (CommandObject commandObject : game.getState().getCommand()) {
            if (commandObject instanceof Commander) {
                Card card = game.getCard((commandObject).getId());
                if (card != null && card.isOwnedBy(controller.getId())
                        && card.isCreature(game) && !card.hasSubtype(subType, game)) {
                    game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
                }
            }
        }
        // TODO: Why is this not using the for-in loop like all the others?
        // creature spells you control
        for (Iterator<StackObject> iterator = game.getStack().iterator(); iterator.hasNext(); ) {
            StackObject stackObject = iterator.next();
            if (stackObject instanceof Spell
                    && stackObject.isControlledBy(source.getControllerId())
                    && stackObject.isCreature(game)
                    && !stackObject.hasSubtype(subType, game)) {
                Card card = ((Spell) stackObject).getCard();
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }
        // creatures you control
        List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(
                new FilterControlledCreaturePermanent(), source.getControllerId(), game);
        for (Permanent creature : creatures) {
            if (creature != null) {
                creature.addSubType(game, subType);
            }
        }
        return true;

    }

    private void setCreatureSubtype(MageObject object, SubType subtype, Game game) {
        if (object == null) {
            return;
        }
        game.getState().getCreateMageObjectAttribute(object, game).getSubtype().add(subtype);
    }
}
