
package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class VoyagerDrake extends CardImpl {

    public VoyagerDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Multikicker {U}
        this.addAbility(new MultikickerAbility("{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Voyager Drake enters the battlefield, up to X target creatures gain flying until end of turn, where X is the number of times Voyager Drake was kicked.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainAbilityTargetEffect(
                        FlyingAbility.getInstance(), Duration.EndOfTurn
                ).setText("up to X target creatures gain flying until end of turn, " +
                        "where X is the number of times {this} was kicked.")
        );
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.setTargetAdjuster(new TargetsCountAdjuster(MultikickerCount.instance));
        this.addAbility(ability);
    }

    private VoyagerDrake(final VoyagerDrake card) {
        super(card);
    }

    @Override
    public VoyagerDrake copy() {
        return new VoyagerDrake(this);
    }
}
