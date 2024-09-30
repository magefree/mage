package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class EmissaryGreen extends CardImpl {

    public EmissaryGreen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        //Whenever Emissary Green attacks, starting with you, each player votes for profit or security.
        // You create a number of Treasure tokens equal to twice the number of profit votes.
        // Put a number of +1/+1 counters on each creature you control equal to the number of security votes.
        this.addAbility(new AttacksTriggeredAbility(new EmissaryGreenEffect()));
    }

    private EmissaryGreen(final EmissaryGreen card) {
        super(card);
    }

    @Override
    public EmissaryGreen copy() {
        return new EmissaryGreen(this);
    }
}

class EmissaryGreenEffect extends OneShotEffect {

    EmissaryGreenEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player votes for profit or security. " +
                "You create a number of Treasure tokens equal to twice the number of profit votes. " +
                "Put a number of +1/+1 counters on each creature you control equal to the number of security votes.";
    }

    private EmissaryGreenEffect(final EmissaryGreenEffect effect) {
        super(effect);
    }

    @Override
    public EmissaryGreenEffect copy() {
        return new EmissaryGreenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TwoChoiceVote vote = new TwoChoiceVote(
                "Profit (treasure)", "Security (+1/+1 counters)", Outcome.Detriment
        );
        vote.doVotes(source, game);
        int profitCounter = vote.getVoteCount(true);
        int securityCounter = vote.getVoteCount(false);
        if (profitCounter > 0) {
            new TreasureToken().putOntoBattlefield(2 * profitCounter, game, source);
        }
        if (securityCounter > 0) {
            AddCountersAllEffect countersAllEffect = new AddCountersAllEffect(
                    CounterType.P1P1.createInstance(),
                    StaticValue.get(securityCounter),
                    StaticFilters.FILTER_CONTROLLED_CREATURE
            );
            countersAllEffect.setText("put a number of +1/+1 counters on each creature you control equal to the number of security votes.");
            countersAllEffect.apply(game, source);

        }
        return profitCounter + securityCounter > 0;
    }
}
