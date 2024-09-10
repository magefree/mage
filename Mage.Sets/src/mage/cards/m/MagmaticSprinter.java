package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagmaticSprinter extends CardImpl {

    public MagmaticSprinter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Magmatic Sprinter enters the battlefield, put two oil counters on target artifact or creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.OIL.createInstance(2))
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);

        // At the beginning of your end step, return Magmatic Sprinter to its owner's hand unless you remove two oil counters from it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DoUnlessControllerPaysEffect(
                        new ReturnToHandSourceEffect(true),
                        new RemoveCountersSourceCost(CounterType.OIL.createInstance(2))
                ).setText("return {this} to its owner's hand unless you remove two oil counters from it"),
                TargetController.YOU, false
        ));
    }

    private MagmaticSprinter(final MagmaticSprinter card) {
        super(card);
    }

    @Override
    public MagmaticSprinter copy() {
        return new MagmaticSprinter(this);
    }
}
