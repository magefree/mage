package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.command.Commander;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ShapeshifterBlueToken;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaskwoodNexus extends CardImpl {

    public MaskwoodNexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Creatures you control are every creature type. The same is true for creature spells you control and creature cards you own that aren't on the battlefield.
        this.addAbility(new SimpleStaticAbility(new MaskwoodNexusEffect()));

        // {3}, {T}: Create a 2/2 blue Shapeshifter creature token with changeling.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new ShapeshifterBlueToken()), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MaskwoodNexus(final MaskwoodNexus card) {
        super(card);
    }

    @Override
    public MaskwoodNexus copy() {
        return new MaskwoodNexus(this);
    }
}

class MaskwoodNexusEffect extends ContinuousEffectImpl {

    MaskwoodNexusEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Creatures you control are every creature type. "
                + "The same is true for creature spells you control "
                + "and creature cards you own that aren't on the battlefield.";
        this.dependendToTypes.add(DependencyType.BecomeCreature);
    }

    private MaskwoodNexusEffect(final MaskwoodNexusEffect effect) {
        super(effect);
    }

    @Override
    public MaskwoodNexusEffect copy() {
        return new MaskwoodNexusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // Creature cards you own that aren't on the battlefield
        // in graveyard
        for (UUID cardId : controller.getGraveyard()) {
            Card card = game.getCard(cardId);
            if (card != null && card.isCreature(game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().setIsAllCreatureTypes(true);
            }
        }
        // on Hand
        for (UUID cardId : controller.getHand()) {
            Card card = game.getCard(cardId);
            if (card != null && card.isCreature(game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().setIsAllCreatureTypes(true);
            }
        }
        // in Exile
        for (Card card : game.getState().getExile().getAllCards(game)) {
            if (card.isCreature(game) && card.isOwnedBy(controller.getId())) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().setIsAllCreatureTypes(true);
            }
        }
        // in Library (e.g. for Mystical Teachings)
        for (Card card : controller.getLibrary().getCards(game)) {
            if (card.isOwnedBy(controller.getId()) && card.isCreature(game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().setIsAllCreatureTypes(true);
            }
        }
        // commander in command zone
        for (CommandObject commandObject : game.getState().getCommand()) {
            if (!(commandObject instanceof Commander)) {
                continue;
            }
            Card card = game.getCard(((Commander) commandObject).getId());
            if (card != null
                    && card.isOwnedBy(controller.getId())
                    && card.isCreature(game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().setIsAllCreatureTypes(true);
            }
        }
        // creature spells you control
        for (Iterator<StackObject> iterator = game.getStack().iterator(); iterator.hasNext();) {
            StackObject stackObject = iterator.next();
            if (stackObject instanceof Spell
                    && stackObject.isControlledBy(source.getControllerId())
                    && stackObject.isCreature(game)) {
                Card card = ((Spell) stackObject).getCard();
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().setIsAllCreatureTypes(true);
            }
        }
        // creatures you control
        List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(
                new FilterControlledCreaturePermanent(), source.getControllerId(), game);
        for (Permanent creature : creatures) {
            if (creature != null) {
                creature.setIsAllCreatureTypes(game, true);
            }
        }
        return true;

    }
}
