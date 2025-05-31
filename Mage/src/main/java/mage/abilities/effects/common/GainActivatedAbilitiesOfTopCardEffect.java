package mage.abilities.effects.common;

import mage.MageObject;
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

import java.util.ArrayList;
import java.util.Collections;
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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (!filter.match(card, game)) {
            return false;
        }
        List<Ability> activatedAbilities = new ArrayList<>();
        for (Ability ability : card.getAbilities()) {
            if (ability.isActivatedAbility()) {
                activatedAbilities.add(ability);
            }
        }
        for (MageObject mageObject : objects) {
            if (!(mageObject instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) mageObject;
            for (Ability ability : activatedAbilities) {
                permanent.addAbility(ability, source.getSourceId(), game, true);
            }
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null ? Collections.singletonList(permanent) : Collections.emptyList();
    }

    @Override
    public GainActivatedAbilitiesOfTopCardEffect copy() {
        return new GainActivatedAbilitiesOfTopCardEffect(this);
    }
}
