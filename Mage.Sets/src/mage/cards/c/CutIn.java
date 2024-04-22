package mage.cards.c;

import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CutIn extends CardImpl {

    public CutIn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Cut In deals 4 damage to target creature.
        this.getSpellAbility().addEffect(
                new DamageTargetEffect(4)
                        .setUseOnlyTargetPointer(true)
                        .setTargetPointer(new FirstTargetPointer())
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Create a Young Hero Role token attached to up to one target creature you control.
        this.getSpellAbility().addEffect(
                new CreateRoleAttachedTargetEffect(RoleType.YOUNG_HERO)
                        .setTargetPointer(new SecondTargetPointer())
                        .concatBy("<br>")
        );
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 1));
    }

    private CutIn(final CutIn card) {
        super(card);
    }

    @Override
    public CutIn copy() {
        return new CutIn(this);
    }
}
