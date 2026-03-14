package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.AirbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AirbendersReversal extends CardImpl {

    public AirbendersReversal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        this.subtype.add(SubType.LESSON);

        // Choose one --
        // * Destroy target attacking creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature());

        // * Airbend target creature you control.
        this.getSpellAbility().addMode(new Mode(new AirbendTargetEffect())
                .addTarget(new TargetControlledCreaturePermanent()));
    }

    private AirbendersReversal(final AirbendersReversal card) {
        super(card);
    }

    @Override
    public AirbendersReversal copy() {
        return new AirbendersReversal(this);
    }
}
