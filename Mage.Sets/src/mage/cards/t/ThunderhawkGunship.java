package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;
import mage.game.permanent.token.WhiteAstartesWarriorToken;

/**
 * @author TheElk801
 */
public final class ThunderhawkGunship extends CardImpl {

    public ThunderhawkGunship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Thunderhawk Gunship enters the battlefield, create two 2/2 white Astartes Warrior creature tokens with vigilance.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new WhiteAstartesWarriorToken(), 2)
        ));

        // Whenever Thunderhawk Gunship attacks, attacking creatures you control gain flying until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES
        )));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private ThunderhawkGunship(final ThunderhawkGunship card) {
        super(card);
    }

    @Override
    public ThunderhawkGunship copy() {
        return new ThunderhawkGunship(this);
    }
}
