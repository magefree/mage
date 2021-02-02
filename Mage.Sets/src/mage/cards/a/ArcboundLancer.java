
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class ArcboundLancer extends CardImpl {

    public ArcboundLancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Modular 4
        this.addAbility(new ModularAbility(this, 4));
    }

    private ArcboundLancer(final ArcboundLancer card) {
        super(card);
    }

    @Override
    public ArcboundLancer copy() {
        return new ArcboundLancer(this);
    }
}
