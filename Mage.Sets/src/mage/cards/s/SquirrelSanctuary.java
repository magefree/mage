package mage.cards.s;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SquirrelToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SquirrelSanctuary extends CardImpl {

    public SquirrelSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // When Squirrel Sanctuary enters the battlefield, create a 1/1 green Squirrel creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SquirrelToken())));

        // Whenever a nontoken creature you control dies, you may pay {1}. If you do, return Squirrel Sanctuary to its owner's hand.
        this.addAbility(new DiesCreatureTriggeredAbility(new DoIfCostPaid(
                new ReturnToHandSourceEffect(true), new GenericManaCost(1)
        ), false, StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN));
    }

    private SquirrelSanctuary(final SquirrelSanctuary card) {
        super(card);
    }

    @Override
    public SquirrelSanctuary copy() {
        return new SquirrelSanctuary(this);
    }
}
