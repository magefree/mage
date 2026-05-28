package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ModifyObjectAllMultiZoneEffect extends ContinuousEffectImpl {
    @FunctionalInterface
    public interface ObjectModifier {
        void modify(MageObject obj, Ability source, Game game);
    }

    private final FilterPermanent filterPermanent;
    private final FilterSpell filterSpell;
    private final FilterCard filterCard;
    private final ObjectModifier function;

    public ModifyObjectAllMultiZoneEffect(
        FilterPermanent filterPermanent,
        FilterSpell filterSpell,
        FilterCard filterCard,
        ObjectModifier function,
        String modification
    ) {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);

        this.filterPermanent = filterPermanent;
        this.filterSpell = filterSpell;
        this.filterCard = filterCard;
        this.function = function;

        this.staticText = filterPermanent.getMessage() + " are " + modification + ". "
                + "The same is true for " + filterSpell.getMessage()
                + " and " + filterCard.getMessage() + " that aren't on the battlefield";
    }

    protected ModifyObjectAllMultiZoneEffect(final ModifyObjectAllMultiZoneEffect effect) {
        super(effect);
        this.filterPermanent = effect.filterPermanent;
        this.filterSpell = effect.filterSpell;
        this.filterCard = effect.filterCard;
        this.function = effect.function;
    }

    @Override
    public ModifyObjectAllMultiZoneEffect copy() {
        return new ModifyObjectAllMultiZoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        final UUID controllerId = source.getControllerId();
        if (controllerId == null) {
            return false;
        }
        final Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }

        // cards that aren't on the battlefield
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
                .filter(card -> this.filterCard.match(card, controllerId, source, game))
                .forEach(mageObject -> {
                    this.function.modify(mageObject, source, game);
                    CardUtil.getObjectParts(mageObject).stream()
                            .filter(Objects::nonNull)
                            .map(game::getCard)
                            .filter(Objects::nonNull)
                            .filter(part -> this.filterCard.match(part, controllerId, source, game))
                            .forEach(part -> { this.function.modify(part, source, game); });
                });

        // spells
        game.getStack().stream()
            .filter(Spell.class::isInstance)
            .filter(spell -> this.filterSpell.match(spell, controllerId, source, game))
            .map(Spell.class::cast)
            .filter(Objects::nonNull)
            .map(spell -> spell.getCard())
            .forEach(card -> { this.function.modify(card, source, game); });

        // permanents
        game.getBattlefield().getAllActivePermanents(this.filterPermanent, controllerId, game)
            .stream()
            .filter(Objects::nonNull)
            .forEach(permanent -> { this.function.modify(permanent, source, game); });

        return true;
    }
}
