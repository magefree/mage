package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonoistCircuitFeeder extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(ArtifactYouControlCount.instance);

    public MonoistCircuitFeeder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.NAUTILUS);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, until end of turn, target creature you control gets +X/+0 and target creature an opponent controls gets -0/-X, where X is the number of artifacts you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(
                ArtifactYouControlCount.instance, StaticValue.get(0)
        ).setText("until end of turn, target creature you control gets +X/+0"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addEffect(new BoostTargetEffect(StaticValue.get(0), xValue)
                .setText("and target creature an opponent controls gets -0/-X, where X is the number of artifacts you control")
                .setTargetPointer(new SecondTargetPointer()));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.addHint(ArtifactYouControlHint.instance));
    }

    private MonoistCircuitFeeder(final MonoistCircuitFeeder card) {
        super(card);
    }

    @Override
    public MonoistCircuitFeeder copy() {
        return new MonoistCircuitFeeder(this);
    }
}
