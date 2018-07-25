package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Larva extends CardImpl {

    public Larva(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        
        this.subtype.add(SubType.ZERG);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}{B}, {T}, Sacrifice Larva: Return target Zerg creature card from your graveyard to the battlefield. Activate this ability only any time you can cast a sorcery.
    }

    public Larva(final Larva card) {
        super(card);
    }

    @Override
    public Larva copy() {
        return new Larva(this);
    }
}
