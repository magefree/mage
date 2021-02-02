
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class TritonShorestalker extends CardImpl {

    public TritonShorestalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Triton Shorestalker can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility("{this} can't be blocked"));
    }

    private TritonShorestalker(final TritonShorestalker card) {
        super(card);
    }

    @Override
    public TritonShorestalker copy() {
        return new TritonShorestalker(this);
    }
}
