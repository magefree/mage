package mage.cards.p;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class Putrefy extends CardImpl {

    public Putrefy (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{G}");

        // Destroy target artifact or creature. It can't be regenerated.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
    }

    private Putrefy(final Putrefy card) {
        super(card);
    }

    @Override
    public Putrefy copy() {
        return new Putrefy(this);
    }

}
