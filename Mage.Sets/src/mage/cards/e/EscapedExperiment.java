package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
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
public final class EscapedExperiment extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(ArtifactYouControlCount.instance);

    public EscapedExperiment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Escaped Experiment attacks, target creature an opponent controls gets -X/-0 until end of turn, where X is the number of artifacts you control.
        Ability ability = new AttacksTriggeredAbility(
                new BoostTargetEffect(xValue, StaticValue.get(0), Duration.EndOfTurn)
                        .setText("target creature an opponent controls gets -X/-0 " +
                                "until end of turn, where X is the number of artifacts you control")
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.addHint(ArtifactYouControlHint.instance));
    }

    private EscapedExperiment(final EscapedExperiment card) {
        super(card);
    }

    @Override
    public EscapedExperiment copy() {
        return new EscapedExperiment(this);
    }
}
