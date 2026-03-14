package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HerbologyInstructor extends TransformingDoubleFacedCard {

    public HerbologyInstructor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.TREEFOLK, SubType.DRUID}, "{1}{G}",
                "Malady Invoker",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.TREEFOLK}, "BG"
        );

        // Herbology Instructor
        this.getLeftHalfCard().setPT(1, 3);

        // When Herbology Instructor enters the battlefield, you gain 3 life.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));

        // {6}{B/P}: Transform Herbology Instructor. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{6}{B/P}")));

        // Malady Invoker
        this.getRightHalfCard().setPT(3, 3);

        // When this creature transforms into Malady Invoker, target creature an opponent controls gets -0/-X until end of turn, where X is Malady Invoker's power.
        Ability ability = new TransformIntoSourceTriggeredAbility(new BoostTargetEffect(
                StaticValue.get(0), new SignInversionDynamicValue(SourcePermanentPowerValue.NOT_NEGATIVE), Duration.EndOfTurn
        ).setText("target creature an opponent controls gets -0/-X until end of turn, where X is {this}'s power"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.getRightHalfCard().addAbility(ability);
    }

    private HerbologyInstructor(final HerbologyInstructor card) {
        super(card);
    }

    @Override
    public HerbologyInstructor copy() {
        return new HerbologyInstructor(this);
    }
}
