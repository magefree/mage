
package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;

import java.util.Collections;
import java.util.List;

/**
 * @author LevelX2
 */

public class GainSuspendEffect extends ContinuousEffectImpl {

    MageObjectReference mor;

    public GainSuspendEffect(MageObjectReference mor) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.mor = mor;
        staticText = "{this} gains suspend";
    }

    protected GainSuspendEffect(final GainSuspendEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            this.discard();
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Card)) {
                continue;
            }
            SuspendAbility.addSuspendTemporaryToCard((Card) object, source, game);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Card card = mor.getCard(game);
        if (card != null && game.getState().getZone(card.getId()) == Zone.EXILED) {
            return Collections.singletonList(card);
        }
        return Collections.emptyList();
    }

    @Override
    public GainSuspendEffect copy() {
        return new GainSuspendEffect(this);
    }
}
