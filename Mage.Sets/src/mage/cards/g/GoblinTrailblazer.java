
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class GoblinTrailblazer extends CardImpl {

    public GoblinTrailblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private GoblinTrailblazer(final GoblinTrailblazer card) {
        super(card);
    }

    @Override
    public GoblinTrailblazer copy() {
        return new GoblinTrailblazer(this);
    }
}
