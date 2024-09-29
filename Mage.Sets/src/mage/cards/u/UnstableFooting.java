
package mage.cards.u;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class UnstableFooting extends CardImpl {

    public UnstableFooting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Kicker {3}{R} (You may pay an additional {3}{R} as you cast this spell.)
        this.addAbility(new KickerAbility("{3}{R}"));

        // Damage can't be prevented this turn. If Unstable Footing was kicked, it deals 5 damage to target player.
        this.getSpellAbility().addEffect(new DamageCantBePreventedEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(5),
                KickedCondition.ONCE,
                "If this spell was kicked, it deals 5 damage to target player or planeswalker")
        );
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(KickedCondition.ONCE,
                new TargetPlayerOrPlaneswalker()));
    }

    private UnstableFooting(final UnstableFooting card) {
        super(card);
    }

    @Override
    public UnstableFooting copy() {
        return new UnstableFooting(this);
    }
}
