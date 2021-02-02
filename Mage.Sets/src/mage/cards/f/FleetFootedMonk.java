package mage.cards.f;

import mage.MageInt;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class FleetFootedMonk extends CardImpl {

    public FleetFootedMonk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Fleet-Footed Monk can't be blocked by creatures with power 2 or greater.
        this.addAbility(new DauntAbility());
    }

    private FleetFootedMonk(final FleetFootedMonk card) {
        super(card);
    }

    @Override
    public FleetFootedMonk copy() {
        return new FleetFootedMonk(this);
    }
}
