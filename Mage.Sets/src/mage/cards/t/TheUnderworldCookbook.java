package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheUnderworldCookbook extends CardImpl {

    public TheUnderworldCookbook(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {T}, Discard a card: Create a Food token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new FoodToken()), new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);

        // {4}, {T}, Sacrifice The Underworld Cookbook: Return target creature card from your graveyard to your hand.
        ability = new SimpleActivatedAbility(new ReturnFromGraveyardToHandTargetEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private TheUnderworldCookbook(final TheUnderworldCookbook card) {
        super(card);
    }

    @Override
    public TheUnderworldCookbook copy() {
        return new TheUnderworldCookbook(this);
    }
}
