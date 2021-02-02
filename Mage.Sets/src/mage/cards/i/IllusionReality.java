
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.target.Target;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetSpellOrPermanent;

/**
 *
 * @author LevelX2
 */
public final class IllusionReality extends SplitCard {

    public IllusionReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}", "{2}{G}", SpellAbilityType.SPLIT);

        // Illusion
        // Target spell or permanent becomes the color of your choice until end of turn.
        getLeftHalfCard().getSpellAbility().addEffect(new BecomesColorTargetEffect(Duration.EndOfTurn));
        Target target = new TargetSpellOrPermanent();
        getLeftHalfCard().getSpellAbility().addTarget(target);

        // Reality
        // Destroy target artifact.
        getRightHalfCard().getSpellAbility().addTarget(new TargetArtifactPermanent());
        getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private IllusionReality(final IllusionReality card) {
        super(card);
    }

    @Override
    public IllusionReality copy() {
        return new IllusionReality(this);
    }
}
