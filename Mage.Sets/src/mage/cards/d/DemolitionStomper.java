package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class DemolitionStomper extends CardImpl {

    public DemolitionStomper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(10);
        this.toughness = new MageInt(7);

        // Demolition Stomper can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Crew 5
        this.addAbility(new CrewAbility(5));
    }

    private DemolitionStomper(final DemolitionStomper card) {
        super(card);
    }

    @Override
    public DemolitionStomper copy() {
        return new DemolitionStomper(this);
    }
}
