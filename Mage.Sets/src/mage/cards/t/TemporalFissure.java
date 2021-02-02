
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author Plopman
 */
public final class TemporalFissure extends CardImpl {

    public TemporalFissure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");


        // Return target permanent to its owner's hand.
        this.getSpellAbility().addTarget(new TargetPermanent());
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        // Storm
        this.addAbility(new StormAbility());
    }

    private TemporalFissure(final TemporalFissure card) {
        super(card);
    }

    @Override
    public TemporalFissure copy() {
        return new TemporalFissure(this);
    }
}
