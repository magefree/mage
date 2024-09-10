package mage.cards.b;

import java.util.UUID;

import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class BranchblightStalker extends CardImpl {

    public BranchblightStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Toxic 2
        this.addAbility(new ToxicAbility(2));
    }

    private BranchblightStalker(final BranchblightStalker card) {
        super(card);
    }

    @Override
    public BranchblightStalker copy() {
        return new BranchblightStalker(this);
    }
}
