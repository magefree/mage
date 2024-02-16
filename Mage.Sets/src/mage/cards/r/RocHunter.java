package mage.cards.r;

import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RocHunter extends CardImpl {

    public RocHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private RocHunter(final RocHunter card) {
        super(card);
    }

    @Override
    public RocHunter copy() {
        return new RocHunter(this);
    }
}
