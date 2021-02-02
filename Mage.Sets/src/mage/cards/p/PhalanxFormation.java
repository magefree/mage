package mage.cards.p;

import mage.abilities.abilityword.StriveAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PhalanxFormation extends CardImpl {

    public PhalanxFormation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Strive — Phalanx Formation costs {1}{W} more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{1}{W}"));

        // Any number of target creatures each gain double strike until end of turn.
        Effect effect = new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Any number of target creatures each gain double strike until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
    }

    private PhalanxFormation(final PhalanxFormation card) {
        super(card);
    }

    @Override
    public PhalanxFormation copy() {
        return new PhalanxFormation(this);
    }
}
