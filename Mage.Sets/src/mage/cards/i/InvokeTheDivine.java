package mage.cards.i;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

public final class InvokeTheDivine extends CardImpl {

    public InvokeTheDivine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Destroy target artifact or enchantment. You gain 4 life.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
    }

    private InvokeTheDivine(final InvokeTheDivine invokeTheDivine) {
        super(invokeTheDivine);
    }

    @Override
    public InvokeTheDivine copy() {
        return new InvokeTheDivine(this);
    }
}