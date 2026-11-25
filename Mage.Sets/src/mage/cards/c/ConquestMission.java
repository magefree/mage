package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.BorgToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class ConquestMission extends CardImpl {

    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("a nontoken artifact you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public ConquestMission(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever a nontoken artifact you control enters, put a quest counter on this enchantment. Create a 1/1 colorless Borg artifact creature token. If this enchantment has six or more quest counters on it, create two of those tokens instead.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), filter);
        ability.addEffect(new ConditionalOneShotEffect(
            new CreateTokenEffect(new BorgToken(), 2),
            new CreateTokenEffect(new BorgToken()),
            new SourceHasCounterCondition(CounterType.QUEST, 6),
            "create a 1/1 colorless Borg artifact creature token. If {this} has six or more quest counters on it, create two of those tokens instead"));
        this.addAbility(ability);
    }

    private ConquestMission(final ConquestMission card) {
        super(card);
    }

    @Override
    public ConquestMission copy() {
        return new ConquestMission(this);
    }
}
