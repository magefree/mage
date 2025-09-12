package mage.cards.j;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ConditionalTargetAdjuster;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Jilt extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public Jilt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Kicker {1}{R}
        this.addAbility(new KickerAbility("{1}{R}"));

        // Return target creature to its owner's hand. If Jilt was kicked, it deals 2 damage to another target creature.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(2, "it"),
                KickedCondition.ONCE,
                "if this spell was kicked, it deals 2 damage to another target creature")
                .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(1).withChooseHint("to return to hand"));
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(KickedCondition.ONCE, true,
                new TargetPermanent(filter).setTargetTag(2).withChooseHint("to deal 2 damage")));
    }

    private Jilt(final Jilt card) {
        super(card);
    }

    @Override
    public Jilt copy() {
        return new Jilt(this);
    }
}
