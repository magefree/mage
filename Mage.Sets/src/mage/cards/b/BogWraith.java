

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
public final class BogWraith extends CardImpl {

    public BogWraith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");

        this.subtype.add(SubType.WRAITH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new SwampwalkAbility());
    }

    private BogWraith(final BogWraith card) {
        super(card);
    }

    @Override
    public BogWraith copy() {
        return new BogWraith(this);
    }

}
