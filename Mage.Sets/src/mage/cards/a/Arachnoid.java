
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class Arachnoid extends CardImpl {

    public Arachnoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        this.addAbility(ReachAbility.getInstance());
    }

    private Arachnoid(final Arachnoid card) {
        super(card);
    }

    @Override
    public Arachnoid copy() {
        return new Arachnoid(this);
    }
}
