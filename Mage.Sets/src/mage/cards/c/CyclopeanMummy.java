
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Derpthemeus
 */
public final class CyclopeanMummy extends CardImpl {

    public CyclopeanMummy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Cyclopean Mummy dies, exile it.
        this.addAbility(new DiesSourceTriggeredAbility(new ExileSourceEffect().setText("exile it")));
    }

    private CyclopeanMummy(final CyclopeanMummy card) {
        super(card);
    }

    @Override
    public CyclopeanMummy copy() {
        return new CyclopeanMummy(this);
    }
}
