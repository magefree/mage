package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.custom.CreatureToken;
import mage.util.RandomUtil;
import mage.util.functions.CopyTokenFunction;

import java.util.List;

/**
 * @author spjspj
 */
public final class MomirEmblem extends Emblem {
    // Faking Vanguard as an Emblem; need to come back to this and add a new type of CommandObject

    public MomirEmblem() {
        super("Emblem Momir");

        // {X}, Discard a card: Create a token that's a copy of a creature card with converted mana cost X chosen at random.
        // Activate this ability only any time you could cast a sorcery and only once each turn.
        LimitedTimesPerTurnActivatedAbility ability = new LimitedTimesPerTurnActivatedAbility(Zone.COMMAND, new MomirEffect(), new VariableManaCost(VariableCostType.NORMAL));
        ability.addCost(new DiscardCardCost());
        ability.setTiming(TimingRule.SORCERY);
        this.getAbilities().add(ability);
    }

    private MomirEmblem(final MomirEmblem card) {
        super(card);
    }

    @Override
    public MomirEmblem copy() {
        return new MomirEmblem(this);
    }
}

class MomirEffect extends OneShotEffect {

    public MomirEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    public MomirEffect(MomirEffect effect) {
        super(effect);
        staticText = "Create a token that's a copy of a creature card with mana value X chosen at random";
    }

    @Override
    public MomirEffect copy() {
        return new MomirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = source.getManaCostsToPay().getX();
        if (game.isSimulation()) {
            // Create dummy token to prevent multiple DB find cards what causes H2 java.lang.IllegalStateException if AI cancels calculation because of time out
            Token token = new CreatureToken(value, value + 1);
            token.putOntoBattlefield(1, game, source, source.getControllerId(), false, false);
            return true;
        }
        // should this be random across card names
        CardCriteria criteria = new CardCriteria().types(CardType.CREATURE).manaValue(value);
        List<CardInfo> options = CardRepository.instance.findCards(criteria);
        if (options == null || options.isEmpty()) {
            game.informPlayers("No random creature card with mana value of " + value + " was found.");
            return false;
        }

        // search for a random non custom set creature
        Token token = null;
        while (!options.isEmpty()) {
            int index = RandomUtil.nextInt(options.size());
            ExpansionSet expansionSet = Sets.findSet(options.get(index).getSetCode());
            if (expansionSet == null || !expansionSet.getSetType().isEternalLegal()) {
                options.remove(index);
            } else {
                Card card = options.get(index).getCard();
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
