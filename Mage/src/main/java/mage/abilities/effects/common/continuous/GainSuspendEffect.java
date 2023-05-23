
package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */

public class GainSuspendEffect extends ContinuousEffectImpl {

    MageObjectReference mor;

    public GainSuspendEffect(MageObjectReference mor) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.mor = mor;
        staticText = "{this} gains suspend";
    }

    public GainSuspendEffect(final GainSuspendEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public GainSuspendEffect copy() {
        return new GainSuspendEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(mor.getSourceId());
        if (card != null && mor.refersTo(card, game) && game.getState().getZone(card.getId()) == Zone.EXILED) {
            SuspendAbility.addSuspendTemporaryToCard(card, source, game);
        } else {
            discard();
        }
        return true;
    }
}
