
package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 *
 * @author Backfir3
 */
public final class Scrap extends CardImpl {

    public Scrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private Scrap(final Scrap card) {
        super(card);
    }

    @Override
    public Scrap copy() {
        return new Scrap(this);
    }
}
