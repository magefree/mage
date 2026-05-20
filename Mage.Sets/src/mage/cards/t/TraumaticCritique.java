package mage.cards.t;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TraumaticCritique extends CardImpl {

    public TraumaticCritique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{R}");

        // Traumatic Critique deals X damage to any target. Draw two cards, then discard a card.
        this.getSpellAbility().addEffect(new DamageTargetEffect(GetXValue.instance));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 1));
    }

    private TraumaticCritique(final TraumaticCritique card) {
        super(card);
    }

    @Override
    public TraumaticCritique copy() {
        return new TraumaticCritique(this);
    }
}
