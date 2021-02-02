package mage.cards.f;

import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ForceAway extends CardImpl {

    public ForceAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // <i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, you may draw a card. If you do, discard a card.
        Effect effect = new ConditionalOneShotEffect(new DrawDiscardControllerEffect(1, 1, true),
                FerociousCondition.instance, "<br><i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, you may draw a card. If you do, discard a card");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private ForceAway(final ForceAway card) {
        super(card);
    }

    @Override
    public ForceAway copy() {
        return new ForceAway(this);
    }
}
