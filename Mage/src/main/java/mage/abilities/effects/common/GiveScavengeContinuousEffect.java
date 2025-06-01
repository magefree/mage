package mage.abilities.effects.common;

import mage.MageItem;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            discard();
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Card)) {
                continue;
            }
            Card card = (Card) object;
            ScavengeAbility ability = new ScavengeAbility(new ManaCostsImpl<>(card.getManaCost().getText()));
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
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
        for (Card card : controller.getGraveyard().getCards(filter, game)) {
            if (card.getManaCost().getText().isEmpty()) {
                continue;
            }
            objects.add(card);
        }
        return objects;
    }

    @Override
    public GiveScavengeContinuousEffect copy() {
        return new GiveScavengeContinuousEffect(this);
    }
}
