package mage.cards.b;

import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeastieBeatdown extends CardImpl {

    public BeastieBeatdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{G}");

        // Choose target creature you control and target creature an opponent controls.
        this.getSpellAbility().addEffect(new InfoEffect(
                "Choose target creature you control and target creature an opponent controls."
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

        // Delirium -- If there are four or more card types among cards in your graveyard, put two +1/+1 counters on the creature you control.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)),
                DeliriumCondition.instance, AbilityWord.DELIRIUM.formatWord() + "If there are four or more " +
                "card types among cards in your graveyard, put two +1/+1 counters on the creature you control."
        ).concatBy("<br>"));
        this.getSpellAbility().addHint(CardTypesInGraveyardCount.YOU.getHint());

        // The creature you control deals damage equal to its power to the creature an opponent controls.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect()
                .setText("<br>The creature you control deals damage equal to its power to the creature an opponent controls."));
    }

    private BeastieBeatdown(final BeastieBeatdown card) {
        super(card);
    }

    @Override
    public BeastieBeatdown copy() {
        return new BeastieBeatdown(this);
    }
}
