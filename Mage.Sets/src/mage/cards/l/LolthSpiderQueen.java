package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.command.emblems.LolthSpiderQueenEmblem;
import mage.game.permanent.token.LolthSpiderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LolthSpiderQueen extends CardImpl {

    public LolthSpiderQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.LOLTH);
        this.setStartingLoyalty(4);

        // Whenever a creature you control dies, put a loyalty counter on Lolth, Spider Queen.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.LOYALTY.createInstance()),
                false, StaticFilters.FILTER_CONTROLLED_A_CREATURE
        ));

        // 0: You draw a card and you lose 1 life.
        Ability ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(1).setText("you draw a card"), 0);
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // −3: Create two 2/1 black Spider creature tokens with menace and reach.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new LolthSpiderToken(), 2), -3));

        // −8: You get an emblem with "Whenever an opponent is dealt combat damage by one or more creatures you control, if that player lost less than 8 life this turn, they lose life equal to the difference."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new LolthSpiderQueenEmblem()), -8));
    }

    private LolthSpiderQueen(final LolthSpiderQueen card) {
        super(card);
    }

    @Override
    public LolthSpiderQueen copy() {
        return new LolthSpiderQueen(this);
    }
}
