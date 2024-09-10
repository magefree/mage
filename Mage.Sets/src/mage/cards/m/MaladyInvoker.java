package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaladyInvoker extends CardImpl {

    private static final DynamicValue xValue
            = new SignInversionDynamicValue(new SourcePermanentPowerCount(false));

    public MaladyInvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // When this creature transforms into Malady Invoker, target creature an opponent controls gets -0/-X until end of turn, where X is Malady Invoker's power.
        Ability ability = new TransformIntoSourceTriggeredAbility(new BoostTargetEffect(
                StaticValue.get(0), xValue, Duration.EndOfTurn
        ).setText("target creature an opponent controls gets -0/-X until end of turn, where X is {this}'s power"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private MaladyInvoker(final MaladyInvoker card) {
        super(card);
    }

    @Override
    public MaladyInvoker copy() {
        return new MaladyInvoker(this);
    }
}
