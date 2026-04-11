package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.WasCardExiledThisTurnCondition;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class EnnisDebateModerator extends CardImpl {

    public EnnisDebateModerator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Ennis enters, exile up to one other target creature you control. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileReturnBattlefieldNextEndStepTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);

        // At the beginning of your end step, if one or more cards were put into exile this turn, put a +1/+1 counter on Ennis.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            TargetController.YOU,
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            false,
            WasCardExiledThisTurnCondition.instance
        ).addHint(WasCardExiledThisTurnCondition.getHint()));
    }

    private EnnisDebateModerator(final EnnisDebateModerator card) {
        super(card);
    }

    @Override
    public EnnisDebateModerator copy() {
        return new EnnisDebateModerator(this);
    }
}
