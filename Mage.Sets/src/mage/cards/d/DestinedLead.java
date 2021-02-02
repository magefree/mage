
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.target.common.TargetCreaturePermanent;

public final class DestinedLead extends SplitCard {

    public DestinedLead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{1}{B}", "{3}{G}", SpellAbilityType.SPLIT_AFTERMATH);

        // Destined
        // Target creature gets +1/+0 and gains indestructible until end of turn.
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new BoostTargetEffect(1, 0, Duration.EndOfTurn);
        effect.setText("Target creature gets +1/+0");
        getLeftHalfCard().getSpellAbility().addEffect(effect);

        effect = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains indestructible until end of turn");
        getLeftHalfCard().getSpellAbility().addEffect(effect);

        // to
        // Lead
        // All creatures able to block target creature this turn must do so.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        getRightHalfCard().getSpellAbility().addEffect(new MustBeBlockedByAllTargetEffect(Duration.EndOfTurn));
    }

    private DestinedLead(final DestinedLead card) {
        super(card);
    }

    @Override
    public DestinedLead copy() {
        return new DestinedLead(this);
    }
}
