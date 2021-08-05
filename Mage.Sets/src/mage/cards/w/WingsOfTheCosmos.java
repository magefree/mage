package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author jeffwadsworth
 */
public final class WingsOfTheCosmos extends CardImpl {

    public WingsOfTheCosmos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target creature gets +1/+3 and gains flying until end of turn.  Untap it.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new BoostTargetEffect(1, 3, Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);
        effect.setText("Target creature gets +1/+3");
        effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying until end of turn.");
        this.getSpellAbility().addEffect(effect);
        effect = new UntapTargetEffect();
        effect.setText("Untap it.");
        this.getSpellAbility().addEffect(effect);

    }

    private WingsOfTheCosmos(final WingsOfTheCosmos card) {
        super(card);
    }

    @Override
    public WingsOfTheCosmos copy() {
        return new WingsOfTheCosmos(this);
    }
}
