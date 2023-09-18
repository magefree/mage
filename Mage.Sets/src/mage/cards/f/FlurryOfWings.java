package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BirdSoldierToken;

/**
 *
 * @author North
 */
public final class FlurryOfWings extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_ATTACKING_CREATURES, null);

    public FlurryOfWings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{W}{U}");

        this.getSpellAbility().addEffect(new CreateTokenEffect(new BirdSoldierToken(), xValue));
    }

    private FlurryOfWings(final FlurryOfWings card) {
        super(card);
    }

    @Override
    public FlurryOfWings copy() {
        return new FlurryOfWings(this);
    }
}
