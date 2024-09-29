package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RiddleGateGargoyle extends CardImpl {

    public RiddleGateGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Riddle Gate Gargoyle enters the battlefield, you get {E}{E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(3)));

        // Whenever you attack, you may pay {E}{E}. When you do, target creature you control gains lifelink until end of turn.
        ReflexiveTriggeredAbility trigger = new ReflexiveTriggeredAbility(
                new GainAbilityTargetEffect(LifelinkAbility.getInstance()), false
        );
        trigger.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DoWhenCostPaid(trigger, new PayEnergyCost(2), "Pay {E}{E}?"), 1
        ));
    }

    private RiddleGateGargoyle(final RiddleGateGargoyle card) {
        super(card);
    }

    @Override
    public RiddleGateGargoyle copy() {
        return new RiddleGateGargoyle(this);
    }
}
