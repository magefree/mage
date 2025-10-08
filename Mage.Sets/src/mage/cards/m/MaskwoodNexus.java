package mage.cards.m;

import mage.MageItem;
import mage.MageObject;
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
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ShapeshifterBlueToken;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Set<Card> affectedCards = controller.getGraveyard().stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        // on Hand
        controller.getHand().stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .forEach(affectedCards::add);

        // in Exile
        game.getState().getExile().getCardsOwned(game, controller.getId()).stream()
                .filter(card -> card.isOwnedBy(controller.getId()))
                .forEach(affectedCards::add);

        // in Library (e.g. for Mystical Teachings)
        affectedCards.addAll(controller.getLibrary().getCards(game));

        // commander in command zone
        game.getState().getCommand().stream()
                .filter(Commander.class::isInstance)
                .map(MageItem::getId)
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(c -> c.isOwnedBy(controller.getId()))
                .forEach(affectedCards::add);

        // Apply to all affected cards by object parts
        affectedCards.stream()
                .map(card -> game.getObject(card.getId()))
                .filter(Objects::nonNull)
                .forEach(mageObject -> {
                    if (mageObject.isCreature(game)) {
                        game.getState().getCreateMageObjectAttribute(mageObject, game).getSubtype().setIsAllCreatureTypes(true);
                    }
                    CardUtil.getObjectParts(mageObject).stream()
                            .filter(Objects::nonNull)
                            .forEach(objectId -> {
                                MageObject partObject = game.getObject(objectId);
                                if (partObject != null && partObject.isCreature(game)) {
                                    game.getState().getCreateMageObjectAttribute(partObject, game).getSubtype().setIsAllCreatureTypes(true);
                                }
                            });
                });

        // creature spells you control
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell
                    && stackObject.isControlledBy(source.getControllerId())
                    && stackObject.isCreature(game)) {
                Card card = ((Spell) stackObject).getCard();
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().setIsAllCreatureTypes(true);
            }
        }

        // creatures you control
        List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), game);
        for (Permanent creature : creatures) {
            if (creature != null) {
                creature.setIsAllCreatureTypes(game, true);
            }
        }

        return true;

    }
}
