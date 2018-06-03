
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author cbt33
 */
public final class Mindslicer extends CardImpl {

    public Mindslicer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Mindslicer dies, each player discards their hand.
        this.addAbility(new DiesTriggeredAbility(new DiscardHandAllEffect(),false));
    }

    public Mindslicer(final Mindslicer card) {
        super(card);
    }

    @Override
    public Mindslicer copy() {
        return new Mindslicer(this);
    }
}
