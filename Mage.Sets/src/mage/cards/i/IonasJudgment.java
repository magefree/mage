package mage.cards.i;

import mage.abilities.effects.common.ExileTargetEffect;
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
public final class IonasJudgment extends CardImpl {

    public IonasJudgment (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}");

        // Exile target creature or enchantment.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT));
    }

    private IonasJudgment(final IonasJudgment card) {
        super(card);
    }

    @Override
    public IonasJudgment copy() {
        return new IonasJudgment(this);
    }

}
