

package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class WringFlesh extends CardImpl {

    public WringFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, -1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WringFlesh(final WringFlesh card) {
        super(card);
    }

    @Override
    public WringFlesh copy() {
        return new WringFlesh(this);
    }

}
