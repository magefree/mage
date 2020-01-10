package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EscapesWithAbility;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnderworldCharger extends CardImpl {

    public UnderworldCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Underworld Charger can't block.
        this.addAbility(new CantBlockAbility());

        // Escape—{4}{B}, Exile three other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{4}{B}", 3));

        // Underworld Charger escapes with two +1/+1 counters on it.
        this.addAbility(new EscapesWithAbility(2));
    }

    private UnderworldCharger(final UnderworldCharger card) {
        super(card);
    }

    @Override
    public UnderworldCharger copy() {
        return new UnderworldCharger(this);
    }
}
