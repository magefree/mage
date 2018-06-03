

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki, nantuko, North
 */
public final class Thrummingbird extends CardImpl {

    public Thrummingbird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ProliferateEffect(), false));
    }

    public Thrummingbird(final Thrummingbird card) {
        super(card);
    }

    @Override
    public Thrummingbird copy() {
        return new Thrummingbird(this);
    }
}
