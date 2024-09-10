

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class Gloomhunter extends CardImpl {

    public Gloomhunter (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.BAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    private Gloomhunter(final Gloomhunter card) {
        super(card);
    }

    @Override
    public Gloomhunter copy() {
        return new Gloomhunter(this);
    }

}
