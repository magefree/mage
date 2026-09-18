package mage.cards.g;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.CadetToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
 * @author muz
 */
public final class GerminateRecruits extends CardImpl {

    public GerminateRecruits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Create X 2/2 colorless Wizard Soldier creature tokens named Cadet, where X is the amount of life you gained this turn.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
            new CadetToken(), ControllerGainedLifeCount.instance
        ));
        this.getSpellAbility().addHint(ControllerGainedLifeCount.getHint());
        this.getSpellAbility().addWatcher(new PlayerGainedLifeWatcher());
    }

    private GerminateRecruits(final GerminateRecruits card) {
        super(card);
    }

    @Override
    public GerminateRecruits copy() {
        return new GerminateRecruits(this);
    }
}
