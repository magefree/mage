
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ArcboundWanderer extends CardImpl {

    public ArcboundWanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Modular-Sunburst
        this.addAbility(new ModularAbility(this,0,true));

    }

    private ArcboundWanderer(final ArcboundWanderer card) {
        super(card);
    }

    @Override
    public ArcboundWanderer copy() {
        return new ArcboundWanderer(this);
    }
}
