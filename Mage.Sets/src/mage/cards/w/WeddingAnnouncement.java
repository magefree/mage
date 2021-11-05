package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.HumanToken;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author weirddan455
 */
public final class WeddingAnnouncement extends CardImpl {

    public WeddingAnnouncement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.secondSideCardClazz = mage.cards.w.WeddingFestivity.class;

        // At the beginning of your end step, put an invitation counter on Wedding Announcement.
        // If you attacked with two or more creatures this turn, draw card.
        // Otherwise, create a 1/1 white Human creature token.
        // Then if Wedding Announcement has three or more invitation counters on it, transform it.
        this.addAbility(new TransformAbility());
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(new AddCountersSourceEffect(CounterType.INVITATION.createInstance()), false);
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                new CreateTokenEffect(new HumanToken()),
                WeddingAnnouncementCondition.instance,
                "If you attacked with two or more creatures this turn, draw card. Otherwise, create a 1/1 white Human creature token"
        ));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(),
                new SourceHasCounterCondition(CounterType.INVITATION, 3),
                "Then if {this} has three or more invitation counters on it, transform it"
        ));
        this.addAbility(ability, new AttackedThisTurnWatcher());
    }

    private WeddingAnnouncement(final WeddingAnnouncement card) {
        super(card);
    }

    @Override
    public WeddingAnnouncement copy() {
        return new WeddingAnnouncement(this);
    }
}

enum WeddingAnnouncementCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        return watcher != null && watcher.getAttackedThisTurnCreatures().size() >= 2;
    }
}
