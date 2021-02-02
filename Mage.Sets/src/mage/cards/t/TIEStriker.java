package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SpaceflightAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author NinthWorld
 */
public final class TIEStriker extends CardImpl {

    public TIEStriker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");
        
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());
    }

    private TIEStriker(final TIEStriker card) {
        super(card);
    }

    @Override
    public TIEStriker copy() {
        return new TIEStriker(this);
    }
}
