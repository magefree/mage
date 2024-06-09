package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PyreOfTheWorldTree extends CardImpl {

    public PyreOfTheWorldTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.color.setRed(true);
        this.nightCard = true;

        // Discard a land card: Pyre of the World Tree deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(2),
                new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_LAND_A))
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Whenever you discard a land card, exile the top card of your library. You may play that card this turn.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn),
                false, StaticFilters.FILTER_CARD_LAND_A
        ));
    }

    private PyreOfTheWorldTree(final PyreOfTheWorldTree card) {
        super(card);
    }

    @Override
    public PyreOfTheWorldTree copy() {
        return new PyreOfTheWorldTree(this);
    }
}
