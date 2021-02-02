
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class KederektCreeper extends CardImpl {

    public KederektCreeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}{R}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch (Any amount of damage this deals to a creature is enough to destroy it.)
        this.addAbility(DeathtouchAbility.getInstance());
        
        // Menace (This creature can't be blocked except by two or more creatures.)
        this.addAbility(new MenaceAbility());
    }

    private KederektCreeper(final KederektCreeper card) {
        super(card);
    }

    @Override
    public KederektCreeper copy() {
        return new KederektCreeper(this);
    }
}
