
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.combat.MustBeBlockedByAllTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.target.common.TargetCreaturePermanent;

public final class ArmedDangerous extends SplitCard {

    public ArmedDangerous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}", "{3}{G}", SpellAbilityType.SPLIT_FUSED);

        // Armed
        // Target creature gets +1/+1 and gains double strike until end of turn.
        getLeftHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn));
        getLeftHalfCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("+1/+1 and double strike"));

        // Dangerous
        // All creatures able to block target creature this turn do so.
        getRightHalfCard().getSpellAbility().addEffect(new MustBeBlockedByAllTargetEffect(Duration.EndOfTurn));
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("all creatures block"));
    }

    private ArmedDangerous(final ArmedDangerous card) {
        super(card);
    }

    @Override
    public ArmedDangerous copy() {
        return new ArmedDangerous(this);
    }
}
