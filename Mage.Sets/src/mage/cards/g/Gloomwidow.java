
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class Gloomwidow extends CardImpl {

    public Gloomwidow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(ReachAbility.getInstance());
        // Gloomwidow can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private Gloomwidow(final Gloomwidow card) {
        super(card);
    }

    @Override
    public Gloomwidow copy() {
        return new Gloomwidow(this);
    }
}
