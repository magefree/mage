package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BrambleFamiliar extends AdventureCard {

    public BrambleFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{1}{G}", "Fetch Quest", "{5}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.RACCOON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {1}{G}, {T}, Discard a card: Return Bramble Familiar to its owner's hand.
        Ability ability = new SimpleActivatedAbility(
                new ReturnToHandSourceEffect(true),
                new ManaCostsImpl<>("{1}{G}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD)));
        this.addAbility(ability);

        // Fetch Quest
        // Mill seven cards, then put a creature, enchantment, or land card from among cards milled this way onto the battlefield.
        this.getSpellCard().getSpellAbility().addEffect(new FetchQuestEffect());

        this.finalizeAdventure();
    }

    private BrambleFamiliar(final BrambleFamiliar card) {
        super(card);
    }

    @Override
    public BrambleFamiliar copy() {
        return new BrambleFamiliar(this);
    }
}

class FetchQuestEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature, enchantment, or land card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    FetchQuestEffect() {
        super(Outcome.Benefit);
        staticText = "mill seven cards, then put a creature, enchantment, or land card "
                + "from among cards milled this way onto the battlefield";
    }

    private FetchQuestEffect(final FetchQuestEffect effect) {
        super(effect);
    }

    @Override
    public FetchQuestEffect copy() {
        return new FetchQuestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = player.millCards(7, source, game);
        if (cards.count(filter, game) < 1) {
            return true;
        }

        TargetCard target = new TargetCardInYourGraveyard(1, 1, filter, true);
        player.choose(Outcome.PutCardInPlay, cards, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        return true;
    }

}