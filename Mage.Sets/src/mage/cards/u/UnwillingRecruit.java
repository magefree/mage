package mage.cards.u;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class UnwillingRecruit extends CardImpl {

    public UnwillingRecruit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}{R}");

        // Gain control of target creature until end of turn. Untap that creature. It gets +X/+0 and gains haste until end of turn.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(ManacostVariableValue.REGULAR, StaticValue.get(0), Duration.EndOfTurn)
                .setText("It gets +X/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains haste until end of turn."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private UnwillingRecruit(final UnwillingRecruit card) {
        super(card);
    }

    @Override
    public UnwillingRecruit copy() {
        return new UnwillingRecruit(this);
    }
}
