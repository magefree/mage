

package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class BluntTheAssault extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures on the battlefield");

    public BluntTheAssault (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");

        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter)));
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
    }

    public BluntTheAssault (final BluntTheAssault card) {
        super(card);
    }

    @Override
    public BluntTheAssault copy() {
        return new BluntTheAssault(this);
    }

}
