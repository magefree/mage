package mage.cards.p;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class PerilsOfThePast extends CardImpl {

    public PerilsOfThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one --
        // * Use the Relic -- Target creature gets +3/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFirstModeFlavorWord("Use the Relic");

        // * Destroy the Relic -- Destroy target artifact.
        this.getSpellAbility().addMode(
            new Mode(new DestroyTargetEffect()).withFlavorWord("Destroy the Relic")
            .addTarget(new TargetArtifactPermanent())
        );
    }

    private PerilsOfThePast(final PerilsOfThePast card) {
        super(card);
    }

    @Override
    public PerilsOfThePast copy() {
        return new PerilsOfThePast(this);
    }
}
