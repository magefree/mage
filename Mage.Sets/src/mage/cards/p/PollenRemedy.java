package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageToTargetMultiAmountEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.target.common.TargetAnyTargetAmount;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class PollenRemedy extends CardImpl {

    public PollenRemedy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Kicker-Sacrifice a land.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(new TargetControlledPermanent(1, 1,
                new FilterControlledLandPermanent("a land"), true))));

        // Prevent the next 3 damage that would be dealt this turn to any number of target creatures and/or players, divided as you choose.
        // If Pollen Remedy was kicked, prevent the next 6 damage this way instead.
        Effect effect = new ConditionalReplacementEffect(new PreventDamageToTargetMultiAmountEffect(Duration.EndOfTurn, 6),
                KickedCondition.ONCE, new PreventDamageToTargetMultiAmountEffect(Duration.EndOfTurn, 3));
        effect.setText("Prevent the next 3 damage that would be dealt this turn to any number of targets, divided as you choose. If this spell was kicked, prevent the next 6 damage this way instead.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(PollenRemedyAdjuster.instance);
    }

    private PollenRemedy(final PollenRemedy card) {
        super(card);
    }

    @Override
    public PollenRemedy copy() {
        return new PollenRemedy(this);
    }
}

enum PollenRemedyAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.addTarget(new TargetAnyTargetAmount(KickedCondition.ONCE.apply(game, ability) ? 6 : 3));
    }
}
