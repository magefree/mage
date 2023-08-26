package mage.cards.m;

import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonstrousRage extends CardImpl {

    public MonstrousRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature gets +2/+0 until end of turn. Create a Monster Role attached to it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.MONSTER).setText("create a Monster Role token attached to it"));
    }

    private MonstrousRage(final MonstrousRage card) {
        super(card);
    }

    @Override
    public MonstrousRage copy() {
        return new MonstrousRage(this);
    }
}
