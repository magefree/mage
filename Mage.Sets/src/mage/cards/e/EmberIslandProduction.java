package mage.cards.e;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmberIslandProduction extends CardImpl {

    public EmberIslandProduction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Choose one--
        // * Create a token that's a copy of target creature you control, except it's not legendary and it's a 4/4 Hero in addition to its other types.
        this.getSpellAbility().addEffect(
                new CreateTokenCopyTargetEffect()
                        .setIsntLegendary(true)
                        .setPower(4)
                        .setToughness(4)
                        .withAdditionalSubType(SubType.HERO)
                        .setText("create a token that's a copy of target creature you control, " +
                                "except it's not legendary and it's a 4/4 Hero in addition to its other types")
        );
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // * Create a token that's a copy of target creature an opponent controls, except it's not legendary and it's a 2/2 Coward in addition to its other types.
        this.getSpellAbility().addMode(new Mode(
                new CreateTokenCopyTargetEffect()
                        .setIsntLegendary(true)
                        .setPower(2)
                        .setToughness(2)
                        .withAdditionalSubType(SubType.COWARD)
                        .setText("create a token that's a copy of target creature an opponent controls, " +
                                "except it's not legendary and it's a 2/2 Coward in addition to its other types")
        ).addTarget(new TargetOpponentsCreaturePermanent()));
    }

    private EmberIslandProduction(final EmberIslandProduction card) {
        super(card);
    }

    @Override
    public EmberIslandProduction copy() {
        return new EmberIslandProduction(this);
    }
}
