

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ChildOfNight extends CardImpl {

    public ChildOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(LifelinkAbility.getInstance());
    }

    private ChildOfNight(final ChildOfNight card) {
        super(card);
    }

    @Override
    public ChildOfNight copy() {
        return new ChildOfNight(this);
    }

}
