package mage.cards.d;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class DivineResilience extends CardImpl {

    public DivineResilience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Kicker {2}{W}
        this.addAbility(new KickerAbility("{2}{W}"));

        // Target creature you control gains indestructible until end of turn. If this spell was kicked, instead any number of target creatures you control gain indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("Target creature you control gains indestructible until end of turn. " +
                        "If this spell was kicked, instead any number of target creatures you control gain indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(
                KickedCondition.ONCE,
                new TargetControlledCreaturePermanent(),
                new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE)
        ));
    }

    private DivineResilience(final DivineResilience card) {
        super(card);
    }

    @Override
    public DivineResilience copy() {
        return new DivineResilience(this);
    }
}
