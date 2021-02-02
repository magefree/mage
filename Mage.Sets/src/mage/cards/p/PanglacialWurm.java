
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.WhileSearchingPlayFromLibraryAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class PanglacialWurm extends CardImpl {

    public PanglacialWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(9);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // While you're searching your library, you may cast Panglacial Wurm from your library.
        this.addAbility(WhileSearchingPlayFromLibraryAbility.getInstance());
    }

    private PanglacialWurm(final PanglacialWurm card) {
        super(card);
    }

    @Override
    public PanglacialWurm copy() {
        return new PanglacialWurm(this);
    }
}
