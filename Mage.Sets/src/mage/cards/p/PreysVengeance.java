

package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class PreysVengeance extends CardImpl {

    public PreysVengeance (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.addAbility(new ReboundAbility());
    }

    private PreysVengeance(final PreysVengeance card) {
        super(card);
    }

    @Override
    public PreysVengeance copy() {
        return new PreysVengeance(this);
    }

}
