package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CapennaExpress extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.TREASURE);

    public CapennaExpress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{G}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Sacrifice a Treasure: Capenna Express becomes an artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(new AddCardTypeSourceEffect(
                Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
        ), new SacrificeTargetCost(filter)));

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private CapennaExpress(final CapennaExpress card) {
        super(card);
    }

    @Override
    public CapennaExpress copy() {
        return new CapennaExpress(this);
    }
}
