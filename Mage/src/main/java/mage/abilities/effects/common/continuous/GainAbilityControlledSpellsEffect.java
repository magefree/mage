package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Styxo
 */
public class GainAbilityControlledSpellsEffect extends ContinuousEffectImpl {

    private final Ability ability;
    private final FilterNonlandCard filter;

    public GainAbilityControlledSpellsEffect(Ability ability, FilterNonlandCard filter) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.filter = filter;
        staticText = filter.getMessage() + " have " + CardUtil.getTextWithFirstCharLowerCase(CardUtil.stripReminderText(ability.getRule()));
    }

    private GainAbilityControlledSpellsEffect(final GainAbilityControlledSpellsEffect effect) {
        super(effect);
        this.ability = effect.ability;
        this.filter = effect.filter;
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Card)) {
                continue;
            }
            game.getState().addOtherAbility((Card) object, ability);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return Collections.emptyList();
        }
        List<MageItem> objects = new ArrayList<>();
        // in graveyard
        objects.addAll(controller.getGraveyard().getCards(filter, game));
        // in Hand
        objects.addAll(controller.getHand().getCards(filter, game));
        // in Exile
        objects.addAll(game.getExile().getAllCards(game, controller.getId())
                .stream()
                .filter(card -> filter.match(card, controller.getId(), source, game))
                .collect(Collectors.toList())
        );
        // in Library
        objects.addAll(controller.getLibrary().getCards(game)
                .stream()
                .filter(card -> filter.match(card, controller.getId(), source, game))
                .collect(Collectors.toList())
        );
        // in Command Zone
        objects.addAll(game.getCommanderCardsFromCommandZone(controller, CommanderCardType.ANY)
                .stream()
                .filter(card -> filter.match(card, controller.getId(), source, game))
                .collect(Collectors.toList())
        );
        // Spells on Stack
        // TODO: Distinguish "you cast" to exclude copies
        objects.addAll(game.getStack().stream()
                .filter(stackObject -> (stackObject instanceof Spell && stackObject.isControlledBy(controller.getId()))
                        && filter.match((Spell) stackObject, controller.getId(), source, game))
                .map(stackObject -> game.getCard(stackObject.getSourceId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        return objects;
    }

    @Override
    public GainAbilityControlledSpellsEffect copy() {
        return new GainAbilityControlledSpellsEffect(this);
    }
}
