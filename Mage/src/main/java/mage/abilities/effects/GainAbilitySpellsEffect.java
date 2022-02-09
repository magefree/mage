package mage.abilities.effects;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

public class GainAbilitySpellsEffect extends ContinuousEffectImpl {

    private final Ability ability;
    private final FilterObject filter;

    public GainAbilitySpellsEffect(Ability ability, FilterObject filter) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.filter = filter;
        staticText = filter.getMessage() + " have " + ability.getRule();
    }

    private GainAbilitySpellsEffect(final GainAbilitySpellsEffect effect) {
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
        if (player == null || permanent == null) {
            return false;
        }
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

        // workaround to gain cost reduction abilities to commanders before cast (make it playable)
        game.getCommanderCardsFromCommandZone(player, CommanderCardType.ANY)
                .stream()
                .filter(card -> filter.match(card, game))
                .forEach(card -> {
                    game.getState().addOtherAbility(card, ability);
                });

        for (StackObject stackObject : game.getStack()) {
            if (!stackObject.isControlledBy(source.getControllerId())) {
                continue;
            }
            Card card = game.getCard(stackObject.getSourceId());
            if (card == null || !filter.match(stackObject, game)) {
                continue;
            }
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }
}
