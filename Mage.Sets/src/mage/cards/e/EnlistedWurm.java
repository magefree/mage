
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class EnlistedWurm extends CardImpl {

    public EnlistedWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{W}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Cascade (When you cast this spell, exile cards from the top of your library until you exile a nonland card that costs less. You may cast it without paying its mana cost. Put the exiled cards on the bottom in a random order.)
        this.addAbility(new CascadeAbility());
    }

    private EnlistedWurm(final EnlistedWurm card) {
        super(card);
    }

    @Override
    public EnlistedWurm copy() {
        return new EnlistedWurm(this);
    }
}
