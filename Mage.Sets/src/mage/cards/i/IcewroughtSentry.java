package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.TapUntappedPermanentTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class IcewroughtSentry extends CardImpl {

    public IcewroughtSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Icewrought Sentry attacks, you may pay {1}{U}. When you do, tap target creature an opponent controls.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(new TapTargetEffect(), false);
        reflexive.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(new AttacksTriggeredAbility(
                new DoWhenCostPaid(
                        reflexive, new ManaCostsImpl<>("{1}{U}"),
                        "pay to tap a creature?"
                )
        ));

        // Whenever you tap an untapped creature an opponent controls, Icewrought Sentry gets +2/+1 until end of turn.
        this.addAbility(new TapUntappedPermanentTriggeredAbility(
                new BoostSourceEffect(2, 1, Duration.EndOfTurn),
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ));
    }

    private IcewroughtSentry(final IcewroughtSentry card) {
        super(card);
    }

    @Override
    public IcewroughtSentry copy() {
        return new IcewroughtSentry(this);
    }
}
