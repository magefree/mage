

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class CourtArchers extends CardImpl {

    public CourtArchers (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.addAbility(ReachAbility.getInstance());
        this.addAbility(new ExaltedAbility());
    }

    private CourtArchers(final CourtArchers card) {
        super(card);
    }

    @Override
    public CourtArchers copy() {
        return new CourtArchers(this);
    }

}
