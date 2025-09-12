package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SusurianDirgecraft extends CardImpl {

    public SusurianDirgecraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{B}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, each opponent sacrifices a nontoken creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeOpponentsEffect(StaticFilters.FILTER_CREATURE_NON_TOKEN)
        ));

        // Station
        this.addAbility(new StationAbility());

        // STATION 7+
        // Flying
        // 4/3
        this.addAbility(new StationLevelAbility(7)
                .withLevelAbility(FlyingAbility.getInstance())
                .withPT(4, 3));
    }

    private SusurianDirgecraft(final SusurianDirgecraft card) {
        super(card);
    }

    @Override
    public SusurianDirgecraft copy() {
        return new SusurianDirgecraft(this);
    }
}
