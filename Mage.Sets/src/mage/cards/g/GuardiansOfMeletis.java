
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class GuardiansOfMeletis extends CardImpl {

    public GuardiansOfMeletis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(0);
        this.toughness = new MageInt(6);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
    }

    private GuardiansOfMeletis(final GuardiansOfMeletis card) {
        super(card);
    }

    @Override
    public GuardiansOfMeletis copy() {
        return new GuardiansOfMeletis(this);
    }
}
