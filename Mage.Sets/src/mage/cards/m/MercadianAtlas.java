
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.watchers.common.PlayLandWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MercadianAtlas extends CardImpl {

    public MercadianAtlas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.subtype.add(SubType.BOOK);

        // At the beginning of your end step, if you didn't play a land this turn, you may draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new DrawCardSourceControllerEffect(1),
                true, MercadianAtlasCondition.instance
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
