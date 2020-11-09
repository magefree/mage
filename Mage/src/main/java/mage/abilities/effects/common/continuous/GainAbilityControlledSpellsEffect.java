package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Styxo
 */
public class GainAbilityControlledSpellsEffect extends ContinuousEffectImpl {

    private final Ability ability;
    private final FilterCard filter;

    public GainAbilityControlledSpellsEffect(Ability ability, FilterCard filter) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.filter = filter;
        staticText = filter.getMessage() + " you cast have " + ability.getRule() + '.';
    }

    public GainAbilityControlledSpellsEffect(final GainAbilityControlledSpellsEffect effect) {
        super(effect);
        this.ability = effect.ability;
        this.filter = effect.filter;
    }

    @Override
    public GainAbilityControlledSpellsEffect copy() {
        return new GainAbilityControlledSpellsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null
                && permanent != null) {
            for (Card card : game.getExile().getAllCards(game)) {
                if (card.isOwnedBy(source.getControllerId())
                        && filter.match(card, game)) {
                    game.getState().addOtherAbility(card, ability);
                }
            }
            for (Card card : player.getLibrary().getCards(game)) {
                if (filter.match(card, game)) {
                    game.getState().addOtherAbility(card, ability);
                }
            }
            for (Card card : player.getHand().getCards(game)) {
                if (filter.match(card, game)) {
                    game.getState().addOtherAbility(card, ability);
                }
            }
            for (Card card : player.getGraveyard().getCards(game)) {
                if (filter.match(card, game)) {
                    game.getState().addOtherAbility(card, ability);
                }
            }
            for (UUID commanderId : game.getCommandersIds(player)) {
                if (game.getState().getZone(commanderId) == Zone.COMMAND) {
                    Card commander = game.getCard(commanderId);
                    if (filter.match(commander, game)) {
                        game.getState().addOtherAbility(commander, ability);
                    }
                }
            }
            for (StackObject stackObject : game.getStack()) {
                // only spells cast, so no copies of spells
                if ((stackObject instanceof Spell)
                        && !stackObject.isCopy()
                        && stackObject.isControlledBy(source.getControllerId())) {
                    Card card = game.getCard(stackObject.getSourceId());
                    if (card != null
                            && filter.match(card, game)) {
                        if (!card.hasAbility(ability, game)) {
                            game.getState().addOtherAbility(card, ability);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
