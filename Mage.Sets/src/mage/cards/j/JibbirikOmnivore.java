package mage.cards.j;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JibbirikOmnivore extends CardImpl {

    public JibbirikOmnivore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private JibbirikOmnivore(final JibbirikOmnivore card) {
        super(card);
    }

    @Override
    public JibbirikOmnivore copy() {
        return new JibbirikOmnivore(this);
    }
}
