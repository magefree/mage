package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnderworldBreach extends CardImpl {

    public UnderworldBreach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Each nonland card in your graveyard has escape. The escape cost is equal to the card's mana cost plus exile three other cards from your graveyard.
        this.addAbility(new SimpleStaticAbility(new UnderworldBreachEffect()));

        // At the beginning of the end step, sacrifice Underworld Breach.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new SacrificeSourceEffect(), TargetController.NEXT, false
        ));
    }

    private UnderworldBreach(final UnderworldBreach card) {
        super(card);
    }

    @Override
    public UnderworldBreach copy() {
        return new UnderworldBreach(this);
    }
}

class UnderworldBreachEffect extends ContinuousEffectImpl {

    UnderworldBreachEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each nonland card in your graveyard has escape. " +
                "The escape cost is equal to the card's mana cost plus exile three other cards from your graveyard.";
    }

    private UnderworldBreachEffect(final UnderworldBreachEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> !card.getManaCost().getText().isEmpty()) // card must have a mana cost
                .filter(card -> !card.isLand(game))
                .forEach(card -> {
                    Ability ability = new EscapeAbility(card, card.getManaCost().getText(), 3);
                    ability.setSourceId(card.getId());
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                });
        return true;
    }

    @Override
    public UnderworldBreachEffect copy() {
        return new UnderworldBreachEffect(this);
    }
}
