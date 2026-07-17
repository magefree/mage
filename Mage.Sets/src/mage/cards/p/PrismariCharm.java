package mage.cards.p;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrismariCharm extends CardImpl {

    public PrismariCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{R}");

        // Choose one --
        // * Surveil 2, then draw a card.
        this.getSpellAbility().addEffect(new SurveilEffect(2, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));

        // * Prismari Charm deals 1 damage to each of one or two targets.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(1)).addTarget(new TargetAnyTarget(1, 2)));

        // * Return target nonland permanent to its owner's hand.
        this.getSpellAbility().addMode(new Mode(new ReturnToHandTargetEffect()).addTarget(new TargetNonlandPermanent()));
    }

    private PrismariCharm(final PrismariCharm card) {
        super(card);
    }

    @Override
    public PrismariCharm copy() {
        return new PrismariCharm(this);
    }
}
