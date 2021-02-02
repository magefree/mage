

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class GarruksCompanion extends CardImpl {

    public GarruksCompanion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(TrampleAbility.getInstance());
    }

    private GarruksCompanion(final GarruksCompanion card) {
        super(card);
    }

    @Override
    public GarruksCompanion copy() {
        return new GarruksCompanion(this);
    }

}
