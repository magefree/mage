

package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class ConstrictingTendrils extends CardImpl {

    public ConstrictingTendrils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, 0, Duration.EndOfTurn));
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private ConstrictingTendrils(final ConstrictingTendrils card) {
        super(card);
    }

    @Override
    public ConstrictingTendrils copy() {
        return new ConstrictingTendrils(this);
    }

}
