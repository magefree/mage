package mage.cards.f;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirebendingLesson extends CardImpl {

    public FirebendingLesson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        this.subtype.add(SubType.LESSON);

        // Kicker {4}
        this.addAbility(new KickerAbility("{4}"));

        // Firebending Lesson deals 2 damage to target creature. If this spell was kicked, it deals 5 damage to that creature instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(5), new DamageTargetEffect(2),
                KickedCondition.ONCE, "{this} deals 2 damage to target creature. " +
                "If this spell was kicked, it deals 5 damage to that creature instead"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FirebendingLesson(final FirebendingLesson card) {
        super(card);
    }

    @Override
    public FirebendingLesson copy() {
        return new FirebendingLesson(this);
    }
}
