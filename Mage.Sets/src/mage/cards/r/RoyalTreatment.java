package mage.cards.r;

import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoyalTreatment extends CardImpl {

    public RoyalTreatment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature you control gains hexproof until end of turn. Create a Royal Role token attached to that creature.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.ROYAL).setText("create a Royal Role token attached to that creature"));
    }

    private RoyalTreatment(final RoyalTreatment card) {
        super(card);
    }

    @Override
    public RoyalTreatment copy() {
        return new RoyalTreatment(this);
    }
}
