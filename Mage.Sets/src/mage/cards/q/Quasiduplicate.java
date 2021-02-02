package mage.cards.q;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.JumpStartAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class Quasiduplicate extends CardImpl {

    public Quasiduplicate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}");

        // Create a token that's a copy of target creature you control.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Jump-start
        this.addAbility(new JumpStartAbility(this));
    }

    private Quasiduplicate(final Quasiduplicate card) {
        super(card);
    }

    @Override
    public Quasiduplicate copy() {
        return new Quasiduplicate(this);
    }
}
