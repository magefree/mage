package mage.cards.r;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class Repeal extends CardImpl {

    public Repeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Return target nonland permanent with converted mana cost X to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterNonlandPermanent("nonland permanent with mana value X")));
        this.getSpellAbility().setTargetAdjuster(new XManaValueTargetAdjuster());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Repeal(final Repeal card) {
        super(card);
    }

    @Override
    public Repeal copy() {
        return new Repeal(this);
    }
}
