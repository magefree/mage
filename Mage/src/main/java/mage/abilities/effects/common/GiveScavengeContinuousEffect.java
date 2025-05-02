package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ScavengeAbility;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * Give Scavenge to all cards matching the filter in your graveyard.
 * The scavenge cost is the card's manacost.
 *
 * @author Susucr
 */
public class GiveScavengeContinuousEffect extends ContinuousEffectImpl {

    private final FilterCard filter;

    public GiveScavengeContinuousEffect(Duration duration, FilterCard filter) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each " + filter.getMessage() + " in your graveyard has scavenge. The scavenge cost is equal to its mana cost";
        this.filter = filter;
    }

    private GiveScavengeContinuousEffect(final GiveScavengeContinuousEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }


    @Override
    public GiveScavengeContinuousEffect copy() {
        return new GiveScavengeContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            discard();
            return false;
        }
        for (UUID cardId : controller.getGraveyard()) {
            Card card = game.getCard(cardId);
            if (!filter.match(card, source.getControllerId(), source, game)) {
                continue;
            }
            if (card.getManaCost().getText().isEmpty()) { // Checks that the card has a mana cost.
                continue;
            }
            ScavengeAbility ability = new ScavengeAbility(new ManaCostsImpl<>(card.getManaCost().getText()));
            ability.setSourceId(cardId);
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }
}
