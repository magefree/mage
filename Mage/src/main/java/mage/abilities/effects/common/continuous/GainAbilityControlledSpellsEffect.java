package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.common.LinkedEffectIdStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.CardUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Styxo
 */
public class GainAbilityControlledSpellsEffect extends ContinuousEffectImpl {

    private final Ability ability;
    private final FilterNonlandCard filter;

    public GainAbilityControlledSpellsEffect(Ability ability, FilterNonlandCard filter) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.filter = filter.copy();
        staticText = filter.getMessage() + " have " + CardUtil.getTextWithFirstCharLowerCase(CardUtil.stripReminderText(ability.getRule()));
    }

    private GainAbilityControlledSpellsEffect(final GainAbilityControlledSpellsEffect effect) {
        super(effect);
        this.ability = effect.ability;
        this.filter = effect.filter.copy();
    }

    @Override
    public GainAbilityControlledSpellsEffect copy() {
        return new GainAbilityControlledSpellsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        for (Card card : game.getExile().getCardsInRange(game, source.getControllerId())) {
            if (filter.match(card, player.getId(), source, game)) {
                game.getState().addOtherAbility(card, copyAbilityForCard(card, source), false);
            }
        }
        for (Card card : player.getLibrary().getCards(game)) {
            if (filter.match(card, player.getId(), source, game)) {
                game.getState().addOtherAbility(card, copyAbilityForCard(card, source), false);
            }
        }
        for (Card card : player.getHand().getCards(game)) {
            if (filter.match(card, player.getId(), source, game)) {
                game.getState().addOtherAbility(card, copyAbilityForCard(card, source), false);
            }
        }
        for (Card card : player.getGraveyard().getCards(game)) {
            if (filter.match(card, player.getId(), source, game)) {
                game.getState().addOtherAbility(card, copyAbilityForCard(card, source), false);
            }
        }

        // workaround to gain cost reduction abilities to commanders before cast (make it playable)
        game.getCommanderCardsFromCommandZone(player, CommanderCardType.ANY)
                .stream()
                .filter(card -> filter.match(card, player.getId(), source, game))
                .forEach(card -> game.getState().addOtherAbility(card, copyAbilityForCard(card, source), false));

        for (StackObject stackObject : game.getStack()) {
            if (!(stackObject instanceof Spell) || !stackObject.isControlledBy(source.getControllerId())) {
                continue;
            }
            // TODO: Distinguish "you cast" to exclude copies
            Card card = game.getCard(stackObject.getSourceId());
            if (card != null && filter.match((Spell) stackObject, player.getId(), source, game)) {
                game.getState().addOtherAbility(card, copyAbilityForCard(card, source), false);
            }
        }
        return true;
    }

    private Ability copyAbilityForCard(Card attachedTo, Ability source) {
        Ability abilityToCopy = ability.copy();
        abilityToCopy.remapForSource(buildGrantSeed(attachedTo, source));
        if (abilityToCopy instanceof LinkedEffectIdStaticAbility) {
            ((LinkedEffectIdStaticAbility) abilityToCopy).setEffectIdManually();
        }
        return abilityToCopy;
    }

    private UUID buildGrantSeed(Card attachedTo, Ability source) {
        return UUID.nameUUIDFromBytes((
                "GainAbilityControlledSpellsEffect"
                        + '|'
                        + source.getSourceId()
                        + '|'
                        + source.getOriginalId()
                        + '|'
                        + attachedTo.getId()
        ).getBytes(StandardCharsets.UTF_8));
    }
}
