
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ConsulateSkygate extends CardImpl {

    public ConsulateSkygate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private ConsulateSkygate(final ConsulateSkygate card) {
        super(card);
    }

    @Override
    public ConsulateSkygate copy() {
        return new ConsulateSkygate(this);
    }
}
