package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

public class ForgehammerCenturion extends CardImpl {
    public ForgehammerCenturion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        //Whenever another creature or artifact you control is put into a graveyard from the battlefield, put an oil
        //counter on Forgehammer Centurion.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance()),
                false, StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT_OR_CREATURE, false
        ));

        //Whenever Forgehammer Centurion attacks, you may remove two oil counters from it. When you do, target creature
        //can’t block this turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new CantBlockTargetEffect(Duration.EndOfTurn), false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(ability,
                new RemoveCountersSourceCost(CounterType.OIL.createInstance(2)), "Remove 2 oil counters?"
        )));
    }

    private ForgehammerCenturion(final ForgehammerCenturion card) {
        super(card);
    }

    @Override
    public ForgehammerCenturion copy() {
        return new ForgehammerCenturion(this);
    }
}
