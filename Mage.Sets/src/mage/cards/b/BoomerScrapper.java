package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.JunkToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoomerScrapper extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("a token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public BoomerScrapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Boomer Scrapper enters the battlefield or attacks, you lose 1 life and create a Junk token.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new LoseLifeSourceControllerEffect(1));
        ability.addEffect(new CreateTokenEffect(new JunkToken()).concatBy("and"));
        this.addAbility(ability);

        // Whenever a token you control leaves the battlefield, put a +1/+1 counter on Boomer Scrapper.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false
        ));
    }

    private BoomerScrapper(final BoomerScrapper card) {
        super(card);
    }

    @Override
    public BoomerScrapper copy() {
        return new BoomerScrapper(this);
    }
}
