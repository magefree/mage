package mage.cards.s;

import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SearingBarrage extends CardImpl {

    public SearingBarrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Searing Barrage deals 5 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Adamant — If at least three red mana was spent to cast this spell, Searing Barrage deals 3 damage to that creature's controller.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetControllerEffect(3), AdamantCondition.RED,
                "<br><i>Adamant</i> &mdash; If at least three red mana was spent to cast this spell, " +
                        "{this} deals 3 damage to that creature's controller."
        ));
    }

    private SearingBarrage(final SearingBarrage card) {
        super(card);
    }

    @Override
    public SearingBarrage copy() {
        return new SearingBarrage(this);
    }
}
