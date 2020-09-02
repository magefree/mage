package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class TajuruSnarecaster extends CardImpl {

    public TajuruSnarecaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private TajuruSnarecaster(final TajuruSnarecaster card) {
        super(card);
    }

    @Override
    public TajuruSnarecaster copy() {
        return new TajuruSnarecaster(this);
    }
}
