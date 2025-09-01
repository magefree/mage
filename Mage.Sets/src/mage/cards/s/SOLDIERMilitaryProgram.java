package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.SoldierToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SOLDIERMilitaryProgram extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SOLDIER, "Soldiers you control");

    public SOLDIERMilitaryProgram(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // At the beginning of combat on your turn, choose one. If you control a commander, you may choose both instead.
        // * Create a 1/1 white Soldier creature token.
        Ability ability = new BeginningOfCombatTriggeredAbility(new CreateTokenEffect(new SoldierToken()));
        ability.getModes().setChooseText("choose one. If you control a commander, you may choose both instead.");
        ability.getModes().setMoreCondition(2, ControlACommanderCondition.instance);
        ability.getModes().setChooseText(
                "choose one. If you control a commander, you may choose both instead."
        );

        // * Put a +1/+1 counter on each of up to two Soldiers you control.
        ability.addMode(new Mode(new OneShotNonTargetEffect(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("Put a +1/+1 counter on each of up to two Soldiers you control"),
                new TargetPermanent(0, 2, filter))));
        this.addAbility(ability);
    }

    private SOLDIERMilitaryProgram(final SOLDIERMilitaryProgram card) {
        super(card);
    }

    @Override
    public SOLDIERMilitaryProgram copy() {
        return new SOLDIERMilitaryProgram(this);
    }
}
