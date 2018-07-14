package mage.abilities.effects;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

public class GainAbilitySpellsEffect extends ContinuousEffectImpl {

    private final Ability ability;
    private final FilterObject filter;

    public GainAbilitySpellsEffect(Ability ability, FilterObject filter) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.filter = filter;
        staticText = filter.getMessage() + " have " + ability.getRule();
    }

    public GainAbilitySpellsEffect(final GainAbilitySpellsEffect effect) {
        super(effect);
        this.ability = effect.ability;
        this.filter = effect.filter;
    }

    @Override
    public GainAbilitySpellsEffect copy() {
        return new GainAbilitySpellsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            for (Card card : game.getExile().getAllCards(game)) {
                if (card.isOwnedBy(source.getControllerId()) && filter.match(card, game)) {
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
            for (StackObject stackObject : game.getStack()) {
                if (stackObject.isControlledBy(source.getControllerId())) {
                    Card card = game.getCard(stackObject.getSourceId());
                    if (card != null && filter.match(card, game)) {
                        if (!card.getAbilities().contains(ability)) {
                            game.getState().addOtherAbility(card, ability);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
