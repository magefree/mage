package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BorgToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class Class4TacticalCube extends CardImpl {

    public Class4TacticalCube(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, create two 1/1 colorless Borg artifact creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BorgToken(), 2)));

        // Station
        this.addAbility(new StationAbility());

        // STATION 7+
        // Flying
        // Artifact creatures you control get +1/+1.
        // 4/4
        this.addAbility(new StationLevelAbility(7).withLevelAbility(FlyingAbility.getInstance())
            .withLevelAbility(new SimpleStaticAbility(
                new BoostControlledEffect(
                    1, 1,
                    Duration.WhileOnBattlefield,
                    StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
                )
            )).withPT(4, 4));
    }

    private Class4TacticalCube(final Class4TacticalCube card) {
        super(card);
    }

    @Override
    public Class4TacticalCube copy() {
        return new Class4TacticalCube(this);
    }
}
