package mage.cards.v;

import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VoltstormAngel extends CardImpl {

    public VoltstormAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Voltstorm Angel enters the battlefield, you get {E}{E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(3)));

        // At the beginning of combat on your turn, you may pay {E}{E}. When you do, choose one --
        // * Voltstorm Angel gains vigilance and lifelink until end of turn.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn)
                        .setText("{this} gains vigilance"), false
        );
        reflexive.addEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn)
                        .setText("and lifelink until end of turn")
        );
        // * Other creatures you control get +1/+1 until end of turn.
        reflexive.addMode(new Mode(new BoostControlledEffect(1, 1, Duration.EndOfTurn, true)));
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new DoWhenCostPaid(reflexive, new PayEnergyCost(2), "Pay {E}{E}?"),
                TargetController.YOU, false
        ));
    }

    private VoltstormAngel(final VoltstormAngel card) {
        super(card);
    }

    @Override
    public VoltstormAngel copy() {
        return new VoltstormAngel(this);
    }
}
