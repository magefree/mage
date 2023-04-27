package mage.cards.r;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoilEruption extends CardImpl {

    public RoilEruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Kicker {5}
        this.addAbility(new KickerAbility("{5}"));

        // Roil Eruption deals 3 damage to any target. If this spell was kicked, it deals 5 damage instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(5), new DamageTargetEffect(3), KickedCondition.ONCE,
                "{this} deals 3 damage to any target. If this spell was kicked, it deals 5 damage instead"
        ));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private RoilEruption(final RoilEruption card) {
        super(card);
    }

    @Override
    public RoilEruption copy() {
        return new RoilEruption(this);
    }
}
