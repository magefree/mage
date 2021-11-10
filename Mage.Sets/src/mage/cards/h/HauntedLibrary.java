package mage.cards.h;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HauntedLibrary extends CardImpl {

    public HauntedLibrary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever a creature an opponent controls dies, you may pay {1}. If you do, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new DiesCreatureTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new SpiritWhiteToken()), new GenericManaCost(1)
        ), false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE));
    }

    private HauntedLibrary(final HauntedLibrary card) {
        super(card);
    }

    @Override
    public HauntedLibrary copy() {
        return new HauntedLibrary(this);
    }
}
