package mage.cards.l;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LumenClassFrigate extends CardImpl {

    public LumenClassFrigate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.SPACECRAFT);

        // Station
        this.addAbility(new StationAbility());

        // STATION 2+
        // Other creatures you control get +1/+1.
        this.addAbility(new StationLevelAbility(2).withLevelAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, true)
        )));

        // STATION 12+
        // Flying
        // Lifelink
        // 3/5
        this.addAbility(new StationLevelAbility(12)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(LifelinkAbility.getInstance())
                .withPT(3, 5));
    }

    private LumenClassFrigate(final LumenClassFrigate card) {
        super(card);
    }

    @Override
    public LumenClassFrigate copy() {
        return new LumenClassFrigate(this);
    }
}
