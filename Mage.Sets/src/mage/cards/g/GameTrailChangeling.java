
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class GameTrailChangeling extends CardImpl {

    public GameTrailChangeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new ChangelingAbility());
        this.addAbility(TrampleAbility.getInstance());
    }

    private GameTrailChangeling(final GameTrailChangeling card) {
        super(card);
    }

    @Override
    public GameTrailChangeling copy() {
        return new GameTrailChangeling(this);
    }
}
