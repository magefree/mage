package mage.cards.b;

import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BecomeBrutes extends CardImpl {

    public BecomeBrutes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // One or two target creatures each gain haste until end of turn. For each of those creatures, create a Monster Role token attached to it.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText("one or two target creatures each gain haste until end of turn"));
        this.getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.MONSTER)
                .setText("for each of those creatures, create a Monster Role token attached to it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, 2));
    }

    private BecomeBrutes(final BecomeBrutes card) {
        super(card);
    }

    @Override
    public BecomeBrutes copy() {
        return new BecomeBrutes(this);
    }
}
