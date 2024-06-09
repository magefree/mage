

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class StewardOfValeron extends CardImpl {

    public StewardOfValeron (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.KNIGHT);


        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(new GreenManaAbility());
    }

    private StewardOfValeron(final StewardOfValeron card) {
        super(card);
    }

    @Override
    public StewardOfValeron copy() {
        return new StewardOfValeron(this);
    }
}
