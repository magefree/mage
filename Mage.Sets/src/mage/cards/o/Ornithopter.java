

package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Ornithopter extends CardImpl {

    public Ornithopter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{0}");
        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
    }

    private Ornithopter(final Ornithopter card) {
        super(card);
    }

    @Override
    public Ornithopter copy() {
        return new Ornithopter(this);
    }

}
