package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.MayhemAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiderIslanders extends CardImpl {

    public SpiderIslanders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Mayhem {1}{R}
        this.addAbility(new MayhemAbility(this, "{1}{R}"));
    }

    private SpiderIslanders(final SpiderIslanders card) {
        super(card);
    }

    @Override
    public SpiderIslanders copy() {
        return new SpiderIslanders(this);
    }
}
