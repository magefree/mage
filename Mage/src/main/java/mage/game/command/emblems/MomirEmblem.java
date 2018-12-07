package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.token.EmptyToken;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.util.List;

/**
 * @author spjspj
 */
public final class MomirEmblem extends Emblem {
    // Faking Vanguard as an Emblem; need to come back to this and add a new type of CommandObject

    public MomirEmblem() {
        setName("Emblem Momir Vig, Simic Visionary");
        setExpansionSetCodeForImage("DIS");
        // {X}, Discard a card: Put a token into play as a copy of a random creature card with converted mana cost X. Play this ability only any time you could play a sorcery and only once each turn.
        LimitedTimesPerTurnActivatedAbility ability = new LimitedTimesPerTurnActivatedAbility(Zone.COMMAND, new MomirEffect(), new VariableManaCost());
        ability.addCost(new DiscardCardCost());
        ability.setTiming(TimingRule.SORCERY);
        this.getAbilities().add(ability);

    }
}

class MomirEffect extends OneShotEffect {

    public MomirEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    public MomirEffect(MomirEffect effect) {
        super(effect);
        staticText = "Put a token into play as a copy of a random creature card with converted mana cost X";
    }

    @Override
    public MomirEffect copy() {
        return new MomirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = source.getManaCostsToPay().getX();
        // should this be random across card names, or card printings?
        CardCriteria criteria = new CardCriteria().types(CardType.CREATURE).convertedManaCost(value);
        List<CardInfo> options = CardRepository.instance.findCards(criteria);
        if (options == null || options.isEmpty()) {
            game.informPlayers("No random creature card with converted mana cost of " + value + " was found.");
            return false;
        }
        EmptyToken token = new EmptyToken(); // search for a non custom set creature
        while (!options.isEmpty()) {
            int index = RandomUtil.nextInt(options.size());
            ExpansionSet expansionSet = Sets.findSet(options.get(index).getSetCode());
            if (expansionSet == null || expansionSet.getSetType() == SetType.CUSTOM_SET) {
                options.remove(index);
            } else {
                Card card = options.get(index).getCard();
                if (card != null) {
                    CardUtil.copyTo(token).from(card);
                    break;
                } else {
                    options.remove(index);
                }
            }
        }
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId(), false, false);
        return true;
    }
}
