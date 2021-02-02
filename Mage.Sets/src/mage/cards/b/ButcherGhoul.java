
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ButcherGhoul extends CardImpl {

    public ButcherGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Undying (When this creature dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.)
        this.addAbility(new UndyingAbility());
    }

    private ButcherGhoul(final ButcherGhoul card) {
        super(card);
    }

    @Override
    public ButcherGhoul copy() {
        return new ButcherGhoul(this);
    }
}
