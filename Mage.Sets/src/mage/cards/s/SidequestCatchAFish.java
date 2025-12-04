package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author balazskristof
 */
public final class SidequestCatchAFish extends TransformingDoubleFacedCard {

    public SidequestCatchAFish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{2}{W}",
                "Cooking Campsite",
                new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // Sidequest: Catch a Fish
        // At the beginning of your upkeep, look at the top card of your library. If it's an artifact or creature card, you may reveal it and put it into your hand. If you put a card into your hand this way, create a Food token and transform this enchantment.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new SidequestCatchAFishEffect()));

        // Cooking Campsite
        // {T}: Add {W}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());

        // {3}, {T}, Sacrifice an artifact: Put a +1/+1 counter on each creature you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE), new ManaCostsImpl<>("{3}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_AN));
        this.getRightHalfCard().addAbility(ability);
    }

    private SidequestCatchAFish(final SidequestCatchAFish card) {
        super(card);
    }

    @Override
    public SidequestCatchAFish copy() {
        return new SidequestCatchAFish(this);
    }
}

class SidequestCatchAFishEffect extends OneShotEffect {

    SidequestCatchAFishEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. " +
                "If it's an artifact or creature card, you may reveal it and put it into your hand. " +
                "If you put a card into your hand this way, create a Food token and transform this enchantment.";
    }

    private SidequestCatchAFishEffect(final SidequestCatchAFishEffect effect) {
        super(effect);
    }

    @Override
    public SidequestCatchAFishEffect copy() {
        return new SidequestCatchAFishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }
        controller.lookAtCards("Top card of library", topCard, game);
        if (topCard.isArtifact(game) || topCard.isCreature(game)) {
            if (controller.chooseUse(Outcome.DrawCard, "Reveal " + topCard.getName() + " and put it into your hand?", source, game)) {
                controller.revealCards(source, new CardsImpl(topCard), game);
                controller.moveCards(topCard, Zone.HAND, source, game);
                new FoodToken().putOntoBattlefield(1, game, source);
                new TransformSourceEffect().apply(game, source);
                return true;
            }
        }
        return true;
    }
}