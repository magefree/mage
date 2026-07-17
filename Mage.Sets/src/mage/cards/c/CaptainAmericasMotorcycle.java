package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CaptainAmericasMotorcycle extends CardImpl {

    public CaptainAmericasMotorcycle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this Vehicle enters, target creature or Vehicle gets +2/+0 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 0));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_VEHICLE));
        this.addAbility(ability);

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private CaptainAmericasMotorcycle(final CaptainAmericasMotorcycle card) {
        super(card);
    }

    @Override
    public CaptainAmericasMotorcycle copy() {
        return new CaptainAmericasMotorcycle(this);
    }
}
