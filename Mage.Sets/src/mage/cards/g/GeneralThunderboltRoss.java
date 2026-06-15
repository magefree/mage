package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class GeneralThunderboltRoss extends CardImpl {

    public GeneralThunderboltRoss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Battalion -- Whenever General Thunderbolt Ross and at least two other creatures attack, attacking creatures get +1/+0 until end of turn.
        this.addAbility(new BattalionAbility(
            new BoostAllEffect(1, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false)
        ));
    }

    private GeneralThunderboltRoss(final GeneralThunderboltRoss card) {
        super(card);
    }

    @Override
    public GeneralThunderboltRoss copy() {
        return new GeneralThunderboltRoss(this);
    }
}
