package mage.cards.r;

import mage.abilities.condition.common.ControllerDiscardedThisTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.hint.common.ControllerDiscardedHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.DiscardedCardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Recalibrate extends CardImpl {

    public Recalibrate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target creature to its owner's hand. If you've discarded a card this turn, draw a card.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                ControllerDiscardedThisTurnCondition.instance,
                "If you've discarded a card this turn, draw a card"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(ControllerDiscardedHint.instance);
        this.getSpellAbility().addWatcher(new DiscardedCardWatcher());
    }

    private Recalibrate(final Recalibrate card) {
        super(card);
    }

    @Override
    public Recalibrate copy() {
        return new Recalibrate(this);
    }
}
