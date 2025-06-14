package mage.abilities.effects.common;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;

public class GainActivatedAbilitiesOfTopCardEffect extends ContinuousEffectImpl {

    private final FilterCard filter;

    public GainActivatedAbilitiesOfTopCardEffect(FilterCard filter) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "As long as the top card of your library is " + filter.getMessage() + ", {this} has all activated abilities of that card";
        this.filter = filter;
    }

    protected GainActivatedAbilitiesOfTopCardEffect(final GainActivatedAbilitiesOfTopCardEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public GainActivatedAbilitiesOfTopCardEffect copy() {
        return new GainActivatedAbilitiesOfTopCardEffect(this);
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null || !filter.match(card, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            affectedObjects.add(permanent);
        }
        return !affectedObjects.isEmpty();
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Player player = game.getPlayer(source.getControllerId());
            Card card = player.getLibrary().getFromTop(game);
            Permanent permanent = (Permanent) object;
            for (Ability ability : card.getAbilities(game)) {
                if (ability.isActivatedAbility()) {
                    permanent.addAbility(ability, source.getSourceId(), game, true);
                }
            }
        }
    }
}
