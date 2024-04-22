package mage.cards.q;

import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class QuantumMisalignment extends CardImpl {

    public QuantumMisalignment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Create a token that's a copy of target creature you control, except it isn't legendary.
        this.getSpellAbility().addEffect(
                new CreateTokenCopyTargetEffect()
                        .setIsntLegendary(true)
                        .setText("create a token that's a copy of target creature you control, except it isn't legendary")
        );
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private QuantumMisalignment(final QuantumMisalignment card) {
        super(card);
    }

    @Override
    public QuantumMisalignment copy() {
        return new QuantumMisalignment(this);
    }
}
