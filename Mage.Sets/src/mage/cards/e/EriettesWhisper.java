package mage.cards.e;

import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EriettesWhisper extends CardImpl {

    public EriettesWhisper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Target opponent discards two cards. Create a Wicked Role token attached to up to one target creature you control.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.WICKED).setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 1));
    }

    private EriettesWhisper(final EriettesWhisper card) {
        super(card);
    }

    @Override
    public EriettesWhisper copy() {
        return new EriettesWhisper(this);
    }
}
