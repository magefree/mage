package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SHIELDFlyingCar extends CardImpl {

    public SHIELDFlyingCar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this Vehicle enters, exile up to one target creature you control. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileReturnBattlefieldNextEndStepTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED));
        this.addAbility(ability);

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private SHIELDFlyingCar(final SHIELDFlyingCar card) {
        super(card);
    }

    @Override
    public SHIELDFlyingCar copy() {
        return new SHIELDFlyingCar(this);
    }
}
