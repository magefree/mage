
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.token.BeastToken;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 *
 * @author North
 */
public final class FreshMeat extends CardImpl {

    public FreshMeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");

        // Create a 3/3 green Beast creature token for each creature put into your graveyard from the battlefield this turn.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeastToken(), new FreshMeatDynamicValue()));
    }

    private FreshMeat(final FreshMeat card) {
        super(card);
    }

    @Override
    public FreshMeat copy() {
        return new FreshMeat(this);
    }
}

class FreshMeatDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CreaturesDiedWatcher watcher = game.getState().getWatcher(CreaturesDiedWatcher.class);
        if (watcher != null) {
            return watcher.getAmountOfCreaturesDiedThisTurnByController(sourceAbility.getControllerId());
        }
        return 0;
    }

    @Override
    public FreshMeatDynamicValue copy() {
        return new FreshMeatDynamicValue();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "creature put into your graveyard from the battlefield this turn";
    }
}
