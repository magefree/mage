

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class GrayscaledGharial extends CardImpl {

    public GrayscaledGharial (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.CROCODILE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new IslandwalkAbility());
    }

    private GrayscaledGharial(final GrayscaledGharial card) {
        super(card);
    }

    @Override
    public GrayscaledGharial copy() {
        return new GrayscaledGharial(this);
    }

}
