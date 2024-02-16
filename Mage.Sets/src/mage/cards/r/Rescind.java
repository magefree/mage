
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author Backfir3
 */
public final class Rescind extends CardImpl {

    public Rescind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private Rescind(final Rescind card) {
        super(card);
    }

    @Override
    public Rescind copy() {
        return new Rescind(this);
    }
}
