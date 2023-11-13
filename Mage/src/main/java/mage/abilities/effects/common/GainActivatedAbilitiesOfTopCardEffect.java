package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
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
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (filter.match(card, game)) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    for (Ability ability : card.getAbilities(game)) {
                        if (ability instanceof ActivatedAbility) {
                            permanent.addAbility(ability, source.getSourceId(), game, true);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

}
