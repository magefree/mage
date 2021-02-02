

package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class ViashinoOutrider extends CardImpl {

    public ViashinoOutrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.VIASHINO);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

    this.addAbility(new EchoAbility("{2}{R}"));
    }

    private ViashinoOutrider(final ViashinoOutrider card) {
        super(card);
    }

    @Override
    public ViashinoOutrider copy() {
        return new ViashinoOutrider(this);
    }

}