package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CyclopsSuperconductor extends CardImpl {

    public CyclopsSuperconductor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());

        // When Cyclops Superconductor enters the battlefield, you get {E}{E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(3)));

        // When Cyclops Superconductor dies, you may pay {E}{E}{E}. When you do, Cyclops Superconductor deals damage equal to its power to any target.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(new SourcePermanentPowerCount())
                        .setText("{this} deals damage equal to its power to any target"),
                false
        );
        reflexive.addTarget(new TargetAnyTarget());
        this.addAbility(new DiesSourceTriggeredAbility(
                new DoWhenCostPaid(reflexive, new PayEnergyCost(3), "Pay {E}{E}{E}?", true)
        ));
    }

    private CyclopsSuperconductor(final CyclopsSuperconductor card) {
        super(card);
    }

    @Override
    public CyclopsSuperconductor copy() {
        return new CyclopsSuperconductor(this);
    }
}
