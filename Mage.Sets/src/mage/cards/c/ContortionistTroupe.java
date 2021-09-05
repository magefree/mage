package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.CovenHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ContortionistTroupe extends CardImpl {

    public ContortionistTroupe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Contortionist Troupe enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // Coven — At the beginning of your end step, if you control three or more creatures with different powers, put a +1/+1 counter on target creature you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU, CovenCondition.instance, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.addHint(CovenHint.instance).setAbilityWord(AbilityWord.COVEN));
    }

    private ContortionistTroupe(final ContortionistTroupe card) {
        super(card);
    }

    @Override
    public ContortionistTroupe copy() {
        return new ContortionistTroupe(this);
    }
}
