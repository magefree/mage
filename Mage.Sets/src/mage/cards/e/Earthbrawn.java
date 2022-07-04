
package mage.cards.e;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ReinforceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class Earthbrawn extends CardImpl {

    public Earthbrawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.addAbility(new ReinforceAbility(1, new ManaCostsImpl<>("{1}{G}")));
    }

    private Earthbrawn(final Earthbrawn card) {
        super(card);
    }

    @Override
    public Earthbrawn copy() {
        return new Earthbrawn(this);
    }
}
