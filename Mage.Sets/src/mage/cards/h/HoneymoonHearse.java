package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HoneymoonHearse extends CardImpl {

    public HoneymoonHearse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Tap two untapped creatures you control: Honeymoon Hearse becomes an artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new AddCardTypeSourceEffect(
                        Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
                ).setText("{this} becomes an artifact creature until end of turn"),
                new TapTargetCost(new TargetControlledPermanent(
                        2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES
                ))
        ));
    }

    private HoneymoonHearse(final HoneymoonHearse card) {
        super(card);
    }

    @Override
    public HoneymoonHearse copy() {
        return new HoneymoonHearse(this);
    }
}
