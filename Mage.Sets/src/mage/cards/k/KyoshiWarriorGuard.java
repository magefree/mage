package mage.cards.k;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KyoshiWarriorGuard extends CardImpl {

    public KyoshiWarriorGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private KyoshiWarriorGuard(final KyoshiWarriorGuard card) {
        super(card);
    }

    @Override
    public KyoshiWarriorGuard copy() {
        return new KyoshiWarriorGuard(this);
    }
}
