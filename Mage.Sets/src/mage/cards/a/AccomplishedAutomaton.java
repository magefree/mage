
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FabricateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class AccomplishedAutomaton extends CardImpl {

    public AccomplishedAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Fabricate 1
        this.addAbility(new FabricateAbility(1));
    }

    private AccomplishedAutomaton(final AccomplishedAutomaton card) {
        super(card);
    }

    @Override
    public AccomplishedAutomaton copy() {
        return new AccomplishedAutomaton(this);
    }
}
