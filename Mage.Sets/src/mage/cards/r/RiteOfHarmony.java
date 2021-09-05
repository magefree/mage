package mage.cards.r;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class RiteOfHarmony extends CardImpl {

    public RiteOfHarmony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{W}");
        

        // Whenever a creature or enchantment enters the battlefield under your control this turn, draw a card.
        getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new RiteOfHarmonyTriggeredAbility()));

        // Flashback {2}{G}{W}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{2}{G}{W}"), TimingRule.INSTANT));

    }

    private RiteOfHarmony(final RiteOfHarmony card) {
        super(card);
    }

    @Override
    public RiteOfHarmony copy() {
        return new RiteOfHarmony(this);
    }
}

class RiteOfHarmonyTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("creature or enchantment");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
    }

    public RiteOfHarmonyTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(1), Duration.EndOfTurn, false);
        optional = false;
    }

    public RiteOfHarmonyTriggeredAbility(RiteOfHarmonyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        return filter.match(permanent, getSourceId(), getControllerId(), game);
    }

    @Override
    public RiteOfHarmonyTriggeredAbility copy() {
        return new RiteOfHarmonyTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature or enchantment enters the battlefield under your control this turn, draw a card.";
    }
}