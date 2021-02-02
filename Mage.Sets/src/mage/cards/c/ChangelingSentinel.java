
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class ChangelingSentinel extends CardImpl {

    public ChangelingSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.addAbility(new ChangelingAbility());
        this.addAbility(VigilanceAbility.getInstance());
    }

    private ChangelingSentinel(final ChangelingSentinel card) {
        super(card);
    }

    @Override
    public ChangelingSentinel copy() {
        return new ChangelingSentinel(this);
    }
}
