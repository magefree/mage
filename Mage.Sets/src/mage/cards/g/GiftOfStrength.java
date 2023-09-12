package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

public final class GiftOfStrength extends CardImpl {

    public GiftOfStrength(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +3/+3 and gains reach until end of turn.
        Effect effect = new BoostTargetEffect(3, 3, Duration.EndOfTurn);
        effect.setText("Target creature gets +3/+3");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(ReachAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains reach until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GiftOfStrength(final GiftOfStrength giftOfStrength) {
        super(giftOfStrength);
    }

    public GiftOfStrength copy() {
        return new GiftOfStrength(this);
    }
}
