package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.common.*;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.command.Commander;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801, Susucr
 */
public class AddCreatureSubTypeAllMultiZoneEffect extends ContinuousEffectImpl {
    private final FilterControlledCreaturePermanent filterPermanent;
    private final FilterControlledCreatureSpell filterSpell;
    private final FilterOwnedCreatureCard filterCard;

    public AddCreatureSubTypeAllMultiZoneEffect(
            FilterControlledCreaturePermanent filterPermanent,
            FilterControlledCreatureSpell filterSpell,
            FilterOwnedCreatureCard filterCard
    ) {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);

        this.filterPermanent = filterPermanent;
        this.filterSpell = filterSpell;
        this.filterCard = filterCard;

        staticText = filterPermanent.getMessage() + " are the chosen type in addition to their other types. "
                + "The same is true for " + filterSpell.getMessage()
                + " and " + filterCard.getMessage() + " that aren't on the battlefield";
    }

    private AddCreatureSubTypeAllMultiZoneEffect(final AddCreatureSubTypeAllMultiZoneEffect effect) {
        super(effect);
        this.filterPermanent = effect.filterPermanent;
        this.filterSpell = effect.filterSpell;
        this.filterCard = effect.filterCard;
    }

    @Override
    public AddCreatureSubTypeAllMultiZoneEffect copy() {
        return new AddCreatureSubTypeAllMultiZoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (controller == null || subType == null) {
            return false;
        }

        // creatures cards you own that aren't on the battlefield
        // in graveyard
        for (UUID cardId : controller.getGraveyard()) {
            Card card = game.getCard(cardId);
            if (filterCard.match(card, controllerId, source, game) && !card.hasSubtype(subType, game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }
        // on Hand
        for (UUID cardId : controller.getHand()) {
            Card card = game.getCard(cardId);
            if (filterCard.match(card, controllerId, source, game) && !card.hasSubtype(subType, game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }
        // in Exile
        for (Card card : game.getState().getExile().getAllCards(game, controllerId)) {
            if (filterCard.match(card, controllerId, source, game) && !card.hasSubtype(subType, game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }
        // in Library (e.g. for Mystical Teachings)
        for (Card card : controller.getLibrary().getCards(game)) {
            if (filterCard.match(card, controllerId, source, game) && !card.hasSubtype(subType, game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }
        // commander in command zone
        for (CommandObject commandObject : game.getState().getCommand()) {
            if (commandObject instanceof Commander) {
                Card card = game.getCard((commandObject).getId());
                if (filterCard.match(card, controllerId, source, game) && !card.hasSubtype(subType, game)) {
                    game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
                }
            }
        }

        // creature spells you control
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell
                    && filterSpell.match(stackObject, controllerId, source, game)
                    && !stackObject.hasSubtype(subType, game)) {
                Card card = ((Spell) stackObject).getCard();
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }
        // creatures you control
        List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(
                filterPermanent, controllerId, game);
        for (Permanent creature : creatures) {
            if (creature != null) {
                creature.addSubType(game, subType);
            }
        }
        return true;
    }
}
