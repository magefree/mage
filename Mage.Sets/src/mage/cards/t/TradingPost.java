
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GoatToken;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class TradingPost extends CardImpl {

    public TradingPost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {1}, {T}, Discard a card: You gain 4 life.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(4), new GenericManaCost(1));
        ability1.addCost(new TapSourceCost());
        ability1.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(ability1);

        // {1}, {T}, Pay 1 life: Create a 0/1 white Goat creature token.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new GoatToken()), new GenericManaCost(1));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new PayLifeCost(1));
        this.addAbility(ability2);

        // {1}, {T}, Sacrifice a creature: Return target artifact card from your graveyard to your hand.
        Ability ability3 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new GenericManaCost(1));
        ability3.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        ability3.addCost(new TapSourceCost());
        ability3.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability3);

        // {1}, {T}, Sacrifice an artifact: Draw a card.
        Ability ability4 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability4.addCost(new TapSourceCost());
        ability4.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN)));
        this.addAbility(ability4);

    }

    private TradingPost(final TradingPost card) {
        super(card);
    }

    @Override
    public TradingPost copy() {
        return new TradingPost(this);
    }
}
