package mage.cards.g;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GalvanizingSawship extends CardImpl {

    public GalvanizingSawship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}{R}");

        this.subtype.add(SubType.SPACECRAFT);

        // Station
        this.addAbility(new StationAbility());

        // STATION 3+
        // Flying
        // Haste
        // 6/5
        this.addAbility(new StationLevelAbility(3)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(HasteAbility.getInstance())
                .withPT(6, 5));
    }

    private GalvanizingSawship(final GalvanizingSawship card) {
        super(card);
    }

    @Override
    public GalvanizingSawship copy() {
        return new GalvanizingSawship(this);
    }
}
