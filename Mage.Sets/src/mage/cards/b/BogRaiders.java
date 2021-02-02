

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BogRaiders extends CardImpl {

    public BogRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new SwampwalkAbility());
    }

    private BogRaiders(final BogRaiders card) {
        super(card);
    }

    @Override
    public BogRaiders copy() {
        return new BogRaiders(this);
    }

}
