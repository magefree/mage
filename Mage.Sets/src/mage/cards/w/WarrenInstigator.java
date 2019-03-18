
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author North
 */
public final class WarrenInstigator extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a Goblin creature card");

    static {
        filter.add(new SubtypePredicate(SubType.GOBLIN));
    }

    public WarrenInstigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever Warren Instigator deals damage to an opponent, you may put a Goblin creature card from your hand onto the battlefield.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(filter), false));
    }

    public WarrenInstigator(final WarrenInstigator card) {
        super(card);
    }

    @Override
    public WarrenInstigator copy() {
        return new WarrenInstigator(this);
    }
}
