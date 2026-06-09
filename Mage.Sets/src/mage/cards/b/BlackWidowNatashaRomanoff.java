package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BlackWidowNatashaRomanoff extends CardImpl {

    public BlackWidowNatashaRomanoff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private BlackWidowNatashaRomanoff(final BlackWidowNatashaRomanoff card) {
        super(card);
    }

    @Override
    public BlackWidowNatashaRomanoff copy() {
        return new BlackWidowNatashaRomanoff(this);
    }
}
