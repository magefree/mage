package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlexibleWaterbender extends CardImpl {

    public FlexibleWaterbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Waterbend {3}: This creature has base power and toughness 5/2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new SetBasePowerToughnessSourceEffect(3, 2, Duration.EndOfTurn), new WaterbendCost(3)
        ));
    }

    private FlexibleWaterbender(final FlexibleWaterbender card) {
        super(card);
    }

    @Override
    public FlexibleWaterbender copy() {
        return new FlexibleWaterbender(this);
    }
}
