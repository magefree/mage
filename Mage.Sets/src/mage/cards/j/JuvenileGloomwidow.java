
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class JuvenileGloomwidow extends CardImpl {

    public JuvenileGloomwidow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.addAbility(ReachAbility.getInstance());
        this.addAbility(WitherAbility.getInstance());
    }

    private JuvenileGloomwidow(final JuvenileGloomwidow card) {
        super(card);
    }

    @Override
    public JuvenileGloomwidow copy() {
        return new JuvenileGloomwidow(this);
    }
}
