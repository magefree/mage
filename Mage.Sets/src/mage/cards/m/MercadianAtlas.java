
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.PlayLandWatcher;

/**
 *
 * @author TheElk801
 */
public final class MercadianAtlas extends CardImpl {

    public MercadianAtlas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // At the beginning of your end step, if you didn't play a land this turn, you may draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                TargetController.YOU,
                MercadianAtlasCondition.instance,
                true
        ), new PlayLandWatcher());
    }

    private MercadianAtlas(final MercadianAtlas card) {
        super(card);
    }

    @Override
    public MercadianAtlas copy() {
        return new MercadianAtlas(this);
    }
}

enum MercadianAtlasCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayLandWatcher watcher = game.getState().getWatcher(PlayLandWatcher.class);
        if (watcher != null) {
            return !watcher.landPlayed(source.getControllerId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "you didn't play a land this turn";
    }

}
