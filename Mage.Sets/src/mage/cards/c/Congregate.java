
package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class Congregate extends CardImpl {

    public Congregate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");


        // Target player gains 2 life for each creature on the battlefield.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(new PermanentsOnBattlefieldCount(new FilterCreaturePermanent("creature on the battlefield"), 2)));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Congregate(final Congregate card) {
        super(card);
    }

    @Override
    public Congregate copy() {
        return new Congregate(this);
    }
}
