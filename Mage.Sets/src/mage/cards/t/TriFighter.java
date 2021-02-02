
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.RepairAbility;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class TriFighter extends CardImpl {

    public TriFighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U/B}{U/B}");
        this.subtype.add(SubType.DROID);
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // Repair 3
        this.addAbility(new RepairAbility(3));
    }

    private TriFighter(final TriFighter card) {
        super(card);
    }

    @Override
    public TriFighter copy() {
        return new TriFighter(this);
    }
}
