package mage.cards.f;

import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FistsOfFlame extends CardImpl {

    public FistsOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Draw a card. Until end of turn, target creature gains trample and gets +1/+0 for each card you've drawn this turn.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("Until end of turn, target creature gains trample"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                CardsDrawnThisTurnDynamicValue.instance,
                StaticValue.get(0), Duration.EndOfTurn
        ).setText("and gets +1/+0 for each card you've drawn this turn."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(CardsDrawnThisTurnDynamicValue.getHint());
    }

    private FistsOfFlame(final FistsOfFlame card) {
        super(card);
    }

    @Override
    public FistsOfFlame copy() {
        return new FistsOfFlame(this);
    }
}
