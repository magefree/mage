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
 * @author bobby-mccann
 */
public final class BillFernyBreeSwindler extends CardImpl {

    public BillFernyBreeSwindler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Bill Ferny, Bree Swindler becomes blocked, choose one --
        // * Create a Treasure token.
        // * Target opponent gains control of target Horse you control. If they do, remove Bill Ferny from combat and create three Treasure tokens.
    }

    private BillFernyBreeSwindler(final BillFernyBreeSwindler card) {
        super(card);
    }

    @Override
    public BillFernyBreeSwindler copy() {
        return new BillFernyBreeSwindler(this);
    }
}
