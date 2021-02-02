
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class AvianChangeling extends CardImpl {

    public AvianChangeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new ChangelingAbility());
        this.addAbility(FlyingAbility.getInstance());
    }

    private AvianChangeling(final AvianChangeling card) {
        super(card);
    }

    @Override
    public AvianChangeling copy() {
        return new AvianChangeling(this);
    }
}
