package mage.cards.g;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Glamdring extends CardImpl {

    public Glamdring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has first strike and gets +1/+0 for each instant and sorcery card in your graveyard.
        // Whenever equipped creature deals combat damage to a player, you may cast an instant or sorcery spell from your hand with mana value less than or equal to that damage without paying its mana cost.
        // Equip {3}
    }

    private Glamdring(final Glamdring card) {
        super(card);
    }

    @Override
    public Glamdring copy() {
        return new Glamdring(this);
    }
}
