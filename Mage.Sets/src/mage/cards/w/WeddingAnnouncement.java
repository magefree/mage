package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.HumanToken;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class WeddingAnnouncement extends TransformingDoubleFacedCard {

    public WeddingAnnouncement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{2}{W}",
                "Wedding Festivity",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "W"
        );

        // Wedding Announcement
        // At the beginning of your end step, put an invitation counter on Wedding Announcement.
        // If you attacked with two or more creatures this turn, draw card.
        // Otherwise, create a 1/1 white Human creature token.
        // Then if Wedding Announcement has three or more invitation counters on it, transform it.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(CounterType.INVITATION.createInstance()));
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                new CreateTokenEffect(new HumanToken()),
                WeddingAnnouncementCondition.instance,
                "If you attacked with two or more creatures this turn, draw a card. Otherwise, create a 1/1 white Human creature token"
        ));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(),
                new SourceHasCounterCondition(CounterType.INVITATION, 3),
                "Then if {this} has three or more invitation counters on it, transform it"
        ));
        this.getLeftHalfCard().addAbility(ability);

        // Wedding Festivity
        // Creatures you control get +1/+1
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)));
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
