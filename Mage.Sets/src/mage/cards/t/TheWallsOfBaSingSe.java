package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheWallsOfBaSingSe extends CardImpl {

    public TheWallsOfBaSingSe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(30);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Other permanents you control have indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS, true
        )));
    }

    private TheWallsOfBaSingSe(final TheWallsOfBaSingSe card) {
        super(card);
    }

    @Override
    public TheWallsOfBaSingSe copy() {
        return new TheWallsOfBaSingSe(this);
    }
}
