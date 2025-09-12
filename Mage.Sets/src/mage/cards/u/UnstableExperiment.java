package mage.cards.u;

import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.keyword.ConniveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnstableExperiment extends CardImpl {

    public UnstableExperiment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Target player draws a card, then up to one target creature you control connives.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new ConniveTargetEffect().setTargetPointer(new SecondTargetPointer()).concatBy(", then"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 1));
    }

    private UnstableExperiment(final UnstableExperiment card) {
        super(card);
    }

    @Override
    public UnstableExperiment copy() {
        return new UnstableExperiment(this);
    }
}
