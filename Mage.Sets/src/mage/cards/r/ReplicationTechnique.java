package mage.cards.r;

import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.DemonstrateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReplicationTechnique extends CardImpl {

    public ReplicationTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Demonstrate
        this.addAbility(new DemonstrateAbility());

        // Create a token that's a copy of target permanent you control.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
    }

    private ReplicationTechnique(final ReplicationTechnique card) {
        super(card);
    }

    @Override
    public ReplicationTechnique copy() {
        return new ReplicationTechnique(this);
    }
}
