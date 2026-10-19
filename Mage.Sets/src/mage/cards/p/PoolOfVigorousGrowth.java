package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;
import mage.util.RandomCardUtil;
import mage.util.functions.CopyTokenFunction;

/**
 *
 * @author muz
 */
public final class PoolOfVigorousGrowth extends CardImpl {

    public PoolOfVigorousGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        // {X}, {T}, Discard a card: Create a token that's a copy of a random creature card with mana value X. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new PoolOfVigorousGrowthEffect(), new ManaCostsImpl<>("{X}"));
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
        super(Outcome.PutCreatureInPlay);
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
        int value = CardUtil.getSourceCostsTag(game, source, "X", 0);
        Card card = RandomCardUtil.createRandomCreature(value);
        if (card == null) {
            game.informPlayers("No random creature card with mana value of " + value + " was found.");
            return false;
        }
        Token token = CopyTokenFunction.createTokenCopy(card, game);
        if (token != null) {
            token.putOntoBattlefield(1, game, source, source.getControllerId(), false, false);
            return true;
        }
        return false;
    }
}
