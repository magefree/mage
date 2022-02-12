package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HighSpeedHoverbike extends CardImpl {

    public HighSpeedHoverbike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When High-Speed Hoverbike enters the battlefield, tap up to one target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private HighSpeedHoverbike(final HighSpeedHoverbike card) {
        super(card);
    }

    @Override
    public HighSpeedHoverbike copy() {
        return new HighSpeedHoverbike(this);
    }
}
