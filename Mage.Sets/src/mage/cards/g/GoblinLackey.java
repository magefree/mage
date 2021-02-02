
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterPermanentCard;

/**
 *
 * @author jonubuu
 */
public final class GoblinLackey extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("a Goblin permanent card");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public GoblinLackey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Goblin Lackey deals damage to a player, you may put a Goblin permanent card from your hand onto the battlefield.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(filter), false));
    }

    private GoblinLackey(final GoblinLackey card) {
        super(card);
    }

    @Override
    public GoblinLackey copy() {
        return new GoblinLackey(this);
    }
}
