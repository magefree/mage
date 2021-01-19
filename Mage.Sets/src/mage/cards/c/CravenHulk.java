package mage.cards.c;

import mage.MageInt;
import mage.abilities.keyword.CantBlockAloneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CravenHulk extends CardImpl {

    public CravenHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.COWARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Craven Hulk can't block alone.
        this.addAbility(CantBlockAloneAbility.getInstance());
    }

    private CravenHulk(final CravenHulk card) {
        super(card);
    }

    @Override
    public CravenHulk copy() {
        return new CravenHulk(this);
    }
}
