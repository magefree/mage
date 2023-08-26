package mage.cards.t;

import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.RoleType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwistedFealty extends CardImpl {

    public TwistedFealty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn."));

        // Create a Wicked Role token attached to up to one target creature.
        this.getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.WICKED)
                .setTargetPointer(new SecondTargetPointer()).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
    }

    private TwistedFealty(final TwistedFealty card) {
        super(card);
    }

    @Override
    public TwistedFealty copy() {
        return new TwistedFealty(this);
    }
}
