
package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingCreature;
import mage.game.permanent.token.BirdSoldierToken;

/**
 *
 * @author North
 */
public final class FlurryOfWings extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("the number of attacking creatures");

    public FlurryOfWings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{W}{U}");

        this.getSpellAbility().addEffect(new CreateTokenEffect(new BirdSoldierToken(), new PermanentsOnBattlefieldCount(filter)));
    }

    private FlurryOfWings(final FlurryOfWings card) {
        super(card);
    }

    @Override
    public FlurryOfWings copy() {
        return new FlurryOfWings(this);
    }
}
