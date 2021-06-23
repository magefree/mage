package mage.cards.s;

import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlayingFire extends CardImpl {

    public SlayingFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Slaying Fire deals 3 damage to any target.
        // Adamant — If at least three red mana was spent to cast this spell, it deals 4 damage instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(4), new DamageTargetEffect(3),
                AdamantCondition.RED, "{this} deals 3 damage to any target." +
                "<br><i>Adamant</i> &mdash; If at least three red mana was spent to cast this spell, " +
                "it deals 4 damage instead."
        ));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private SlayingFire(final SlayingFire card) {
        super(card);
    }

    @Override
    public SlayingFire copy() {
        return new SlayingFire(this);
    }
}
