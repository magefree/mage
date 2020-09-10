
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.KinshipAbility;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class InkDissolver extends CardImpl {

    public InkDissolver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Kinship - At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Ink Dissolver, you may reveal it. 
        // If you do, each opponent puts the top three cards of their library into their graveyard.
        this.addAbility(new KinshipAbility(new MillCardsEachPlayerEffect(3, TargetController.OPPONENT)));
    }

    public InkDissolver(final InkDissolver card) {
        super(card);
    }

    @Override
    public InkDissolver copy() {
        return new InkDissolver(this);
    }
}
