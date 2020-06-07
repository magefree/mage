package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class GarruksWarsteed extends CardImpl {

    public GarruksWarsteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        
        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Garruk's Warsteed enters the battlefield, you may search your library and/or graveyard for a card named Garruk, Savage Herald, reveal it, and put it into your hand. If you search your library this way, shuffle it.
    }

    private GarruksWarsteed(final GarruksWarsteed card) {
        super(card);
    }

    @Override
    public GarruksWarsteed copy() {
        return new GarruksWarsteed(this);
    }
}
