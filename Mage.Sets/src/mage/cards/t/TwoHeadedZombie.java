package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class TwoHeadedZombie extends CardImpl {

    public TwoHeadedZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

    }

    private TwoHeadedZombie(final TwoHeadedZombie card) {
        super(card);
    }

    @Override
    public TwoHeadedZombie copy() {
        return new TwoHeadedZombie(this);
    }
}
