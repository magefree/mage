
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class FallenAskari extends CardImpl {

    public FallenAskari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flanking
        this.addAbility(new FlankingAbility());
        
        // Fallen Askari can't block.
        this.addAbility(new CantBlockAbility());
    }

    private FallenAskari(final FallenAskari card) {
        super(card);
    }

    @Override
    public FallenAskari copy() {
        return new FallenAskari(this);
    }
}
