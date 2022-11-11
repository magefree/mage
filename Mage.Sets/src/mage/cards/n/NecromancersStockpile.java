package mage.cards.n;

import java.util.Collection;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 * @author awjackson
 */
public final class NecromancersStockpile extends CardImpl {

    public NecromancersStockpile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // {1}{B}, Discard a creature card: Draw a card.
        // If the discarded card was a Zombie card, create a tapped 2/2 black Zombie creature token.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE_A)));
        ability.addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new ZombieToken(), 1, true, false),
                NecromancersStockpileCondition.instance
        ));
        this.addAbility(ability);
    }

    private NecromancersStockpile(final NecromancersStockpile card) {
        super(card);
    }

    @Override
    public NecromancersStockpile copy() {
        return new NecromancersStockpile(this);
    }
}

enum NecromancersStockpileCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.castStream(source.getCosts().stream(), DiscardTargetCost.class)
                .map(DiscardTargetCost::getCards)
                .flatMap(Collection::stream)
                .anyMatch(card -> card.hasSubtype(SubType.ZOMBIE, game));
    }

    @Override
    public String toString() {
        return "the discarded card was a Zombie card";
    }
}
