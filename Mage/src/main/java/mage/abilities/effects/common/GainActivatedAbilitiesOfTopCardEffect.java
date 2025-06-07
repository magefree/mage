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

import java.util.Map;
import java.util.UUID;

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
    public Map<UUID, MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        if (!affectedObjectMap.isEmpty()) {
            return affectedObjectMap;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return affectedObjectMap;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null || !filter.match(card, game)) {
            return affectedObjectMap;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            affectedObjectMap.put(permanent.getId(), permanent);
        }
        return affectedObjectMap;
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, Map<UUID, MageItem> objects) {
        for (MageItem object : affectedObjectMap.values()) {
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

    @Override
    public boolean apply(Game game, Ability source) {
        if (queryAffectedObjects(layer, source, game).isEmpty()) {
            return false;
        }
        applyToObjects(layer, sublayer, source, game, affectedObjectMap);
        return true;
    }

}
