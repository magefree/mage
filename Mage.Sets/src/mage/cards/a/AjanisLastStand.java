package mage.cards.a;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.DiscardedByOpponentTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.AvatarToken2;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AjanisLastStand extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.PLAINS, "you control a Plains")
    );

    public AjanisLastStand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Whenever a creature or planeswalker you control dies, you may sacrifice Ajani's Last Stand. If you do, create a 4/4 white Avatar creature token with flying.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DoIfCostPaid(new CreateTokenEffect(new AvatarToken2()), new SacrificeSourceCost()),
                false, StaticFilters.FILTER_CONTROLLED_PERMANENT_CREATURE_OR_PLANESWALKER
        ));

        // When a spell or ability an opponent controls causes you to discard this card, if you control a Plains, create a 4/4 white Avatar creature token with flying.
        this.addAbility(new DiscardedByOpponentTriggeredAbility(new CreateTokenEffect(new AvatarToken2())).withInterveningIf(condition));
    }

    private AjanisLastStand(final AjanisLastStand card) {
        super(card);
    }

    @Override
    public AjanisLastStand copy() {
        return new AjanisLastStand(this);
    }
}
