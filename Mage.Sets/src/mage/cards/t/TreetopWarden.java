package mage.cards.t;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class TreetopWarden extends CardImpl {

    public TreetopWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF, SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private TreetopWarden(final TreetopWarden card) {
        super(card);
    }

    @Override
    public TreetopWarden copy() {
        return new TreetopWarden(this);
    }
}
