package mage.cards.y;

import mage.abilities.Mode;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YouSeeAGuardApproach extends CardImpl {

    public YouSeeAGuardApproach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Choose one —
        // • Distract the Guard — Tap target creature.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFirstModeFlavorWord("Distract the Guard");

        // • Hide — Target creature you control gains hexproof until end of turn.
        Mode mode = new Mode(new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn));
        mode.addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addMode(mode.withFlavorWord("Hide"));
    }

    private YouSeeAGuardApproach(final YouSeeAGuardApproach card) {
        super(card);
    }

    @Override
    public YouSeeAGuardApproach copy() {
        return new YouSeeAGuardApproach(this);
    }
}
