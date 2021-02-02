
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class FelhideMinotaur extends CardImpl {

    public FelhideMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.MINOTAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private FelhideMinotaur(final FelhideMinotaur card) {
        super(card);
    }

    @Override
    public FelhideMinotaur copy() {
        return new FelhideMinotaur(this);
    }
}
