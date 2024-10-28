package mage.cards.a;

import mage.MageInt;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AtraxasSkitterfang extends CardImpl {

    public AtraxasSkitterfang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Atraxa's Skitterfang enters the battlefield with three oil counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance(3)),
                "with three oil counters on it"
        ));

        // At the beginning of combat on your turn, you may remove an oil counter from Atraxa's Skitterfang. When you do, target creature you control gains your choice of flying, vigilance, deathtouch, or lifelink until end of turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new GainsChoiceOfAbilitiesEffect(
                FlyingAbility.getInstance(), VigilanceAbility.getInstance(), DeathtouchAbility.getInstance(), LifelinkAbility.getInstance())
                .concatBy("and"), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DoWhenCostPaid(
                ability,
                new RemoveCountersSourceCost(CounterType.OIL.createInstance()),
                "Remove an oil counter?"
        ), TargetController.YOU, false));
    }

    private AtraxasSkitterfang(final AtraxasSkitterfang card) {
        super(card);
    }

    @Override
    public AtraxasSkitterfang copy() {
        return new AtraxasSkitterfang(this);
    }
}
