package mage.cards.p;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.RandomUtil;
import mage.util.functions.CopyTokenFunction;

/**
 *
 * @author karapuzz14
 */
public final class PoolOfVigorousGrowth extends CardImpl {

    public PoolOfVigorousGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");
        

        // {X}, {T}, Discard a card: Create a token that's a copy of a random creature card with mana value X. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new PoolOfVigorousGrowthEffect(), new ManaCostsImpl<>("{X}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private PoolOfVigorousGrowth(final PoolOfVigorousGrowth card) {
        super(card);
    }

    @Override
    public PoolOfVigorousGrowth copy() {
        return new PoolOfVigorousGrowth(this);
    }
}
class PoolOfVigorousGrowthEffect extends OneShotEffect {

    PoolOfVigorousGrowthEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Create a token that's a copy of a random creature card with mana value X";
    }

    private PoolOfVigorousGrowthEffect(final PoolOfVigorousGrowthEffect effect) {
        super(effect);
    }

    @Override
    public PoolOfVigorousGrowthEffect copy() {
        return new PoolOfVigorousGrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        int xValue = CardUtil.getSourceCostsTag(game, source, "X", 0);
        if (game.isSimulation()) {
            // Create dummy token to prevent multiple DB find cards what causes H2 java.lang.IllegalStateException if AI cancels calculation because of time out
            Token token = new CreatureToken(xValue, xValue + 1);
            token.putOntoBattlefield(1, game, source, source.getControllerId(), false, false);
            return true;
        }

        CardCriteria criteria = new CardCriteria().types(CardType.CREATURE).manaValue(xValue);
        List<CardInfo> options = CardRepository.instance.findCards(criteria);
        if (options == null || options.isEmpty()) {
            game.informPlayers("No random creature card with mana value of " + xValue + " was found.");
            return false;
        }

        Token token = null;
        while (!options.isEmpty()) {
            int index = RandomUtil.nextInt(options.size());
            ExpansionSet expansionSet = Sets.findSet(options.get(index).getSetCode());
            if (expansionSet == null || expansionSet.getSetType().isCustomSet() || expansionSet.getSetType().isJokeSet()) {
                options.remove(index);
            } else {
                Card card = options.get(index).createCard();
                if (card != null) {
                    token = CopyTokenFunction.createTokenCopy(card, game);
                    break;
                } else {
                    options.remove(index);
                }
            }
        }
        if (token != null) {
            token.putOntoBattlefield(1, game, source, source.getControllerId(), false, false);
            return true;
        }

        return false;

    }
}