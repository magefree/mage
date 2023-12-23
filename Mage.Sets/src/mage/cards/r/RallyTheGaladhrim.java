package mage.cards.r;

import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class RallyTheGaladhrim extends CardImpl {

    public RallyTheGaladhrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{U}");

        // Create a token that's a copy of target creature you control.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        // Conspire
        this.addAbility(new ConspireAbility(ConspireAbility.ConspireTargets.ONE));

    }

    private RallyTheGaladhrim(final RallyTheGaladhrim card) {
        super(card);
    }

    @Override
    public RallyTheGaladhrim copy() {
        return new RallyTheGaladhrim(this);
    }
}
