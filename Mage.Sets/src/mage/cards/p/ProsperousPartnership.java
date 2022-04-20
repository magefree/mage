package mage.cards.p;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.CitizenGreenWhiteToken;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProsperousPartnership extends CardImpl {

    public ProsperousPartnership(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{W}");

        // When Prosperous Partnership enters the battlefield, create two 1/1 green and white Citizen creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new CitizenGreenWhiteToken(), 2)
        ));

        // Tap three untapped creatures you control: Create a Treasure token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new TreasureToken()),
                new TapTargetCost(new TargetControlledPermanent(
                        3, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES
                ))
        ));
    }

    private ProsperousPartnership(final ProsperousPartnership card) {
        super(card);
    }

    @Override
    public ProsperousPartnership copy() {
        return new ProsperousPartnership(this);
    }
}
