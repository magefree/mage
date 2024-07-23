package mage.cards.v;

import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.GiftType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValleyRally extends CardImpl {

    public ValleyRally(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Gift a Food
        this.addAbility(new GiftAbility(this, GiftType.FOOD));

        // Creatures you control get +2/+0 until end of turn. If the gift was promised, target creature you control gains first strike until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())),
                GiftWasPromisedCondition.TRUE, "If the gift was promised, target creature " +
                "you control gains first strike until end of turn"
        ));
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(GiftWasPromisedCondition.TRUE,
                new TargetControlledCreaturePermanent()));
    }

    private ValleyRally(final ValleyRally card) {
        super(card);
    }

    @Override
    public ValleyRally copy() {
        return new ValleyRally(this);
    }
}
