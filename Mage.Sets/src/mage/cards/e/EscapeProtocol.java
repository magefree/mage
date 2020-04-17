package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EscapeProtocol extends CardImpl {

    public EscapeProtocol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever you cycle a card, you may pay {1}. When you do, exile target artifact or creature you control, then return it to the battlefield under its owner's control.
        this.addAbility(new CycleControllerTriggeredAbility(new DoIfCostPaid(
                new EscapeProtocolEffect(), new GenericManaCost(1)
        ).setText("you may pay {1}. When you do, exile target artifact or creature you control, " +
                "then return it to the battlefield under its owner's control.")));
    }

    private EscapeProtocol(final EscapeProtocol card) {
        super(card);
    }

    @Override
    public EscapeProtocol copy() {
        return new EscapeProtocol(this);
    }
}

class EscapeProtocolEffect extends OneShotEffect {

    EscapeProtocolEffect() {
        super(Outcome.Benefit);
    }

    private EscapeProtocolEffect(final EscapeProtocolEffect effect) {
        super(effect);
    }

    @Override
    public EscapeProtocolEffect copy() {
        return new EscapeProtocolEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new EscapeProtocolReflexiveTriggeredAbility(), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class EscapeProtocolReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("artifact or creature you control");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    EscapeProtocolReflexiveTriggeredAbility() {
        super(new ExileTargetForSourceEffect(), Duration.OneUse, true);
        this.addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect());
        this.addTarget(new TargetPermanent());
    }

    private EscapeProtocolReflexiveTriggeredAbility(final EscapeProtocolReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EscapeProtocolReflexiveTriggeredAbility copy() {
        return new EscapeProtocolReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Exile target artifact or creature you control, " +
                "then return it to the battlefield under its owner's control.";
    }
}
