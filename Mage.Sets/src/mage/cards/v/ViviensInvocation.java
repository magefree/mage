package mage.cards.v;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class ViviensInvocation extends CardImpl {

    public ViviensInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}{G}");

        // Look at the top seven cards of your library. You may put a creature card from among them onto the battlefield. Put the rest on the bottom of your library in a random order. When a creature is put onto the battlefield this way, it deals damage equals to its power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new ViviensInvocationEffect());
    }

    public ViviensInvocation(final ViviensInvocation card) {
        super(card);
    }

    @Override
    public ViviensInvocation copy() {
        return new ViviensInvocation(this);
    }
}

class ViviensInvocationEffect extends OneShotEffect {

    public ViviensInvocationEffect(final ViviensInvocationEffect effect) {
        super(effect);
    }

    public ViviensInvocationEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Look at the top seven cards of your library. "
                + "You may put a creature card from among them onto the battlefield. "
                + "Put the rest on the bottom of your library in a random order. "
                + "When a creature is put onto the battlefield this way, "
                + "it deals damage equal to its power to target creature an opponent controls";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 7));
        if (!cards.isEmpty()) {
            TargetCard target = new TargetCard(
                    Zone.LIBRARY,
                    new FilterCreatureCard("creature card to put on the battlefield")
            );
            if (controller.choose(Outcome.PutCreatureInPlay, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        game.addDelayedTriggeredAbility(
                                new ViviensInvocationReflexiveTriggeredAbility(
                                        new MageObjectReference(permanent, game)
                                ), source);
                        new SendOptionUsedEventEffect().apply(game, source);
                    }
                }
            }
            if (!cards.isEmpty()) {
                controller.putCardsOnBottomOfLibrary(cards, game, source, false);
            }
        }
        return true;
    }

    @Override
    public ViviensInvocationEffect copy() {
        return new ViviensInvocationEffect(this);
    }
}

class ViviensInvocationReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    public ViviensInvocationReflexiveTriggeredAbility(MageObjectReference mor) {
        super(new ViviensInvocationDamageEffect(mor), Duration.OneUse, false);
        this.addTarget(new TargetOpponentsCreaturePermanent());
    }

    public ViviensInvocationReflexiveTriggeredAbility(final ViviensInvocationReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ViviensInvocationReflexiveTriggeredAbility copy() {
        return new ViviensInvocationReflexiveTriggeredAbility(this);
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
        return "When a creature is put onto the battlefield with {this}, "
                + "it deals damage equal to its power to target creature an opponent controls";
    }
}

class ViviensInvocationDamageEffect extends OneShotEffect {

    private final MageObjectReference mor;

    public ViviensInvocationDamageEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        this.mor = mor;
    }

    public ViviensInvocationDamageEffect(final ViviensInvocationDamageEffect effect) {
        super(effect);
        mor = effect.mor;
    }

    @Override
    public ViviensInvocationDamageEffect copy() {
        return new ViviensInvocationDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (mor == null) {
            return false;
        }
        Permanent creature = game.getPermanent(source.getFirstTarget());
        Permanent permanent = mor.getPermanent(game);
        if (creature == null || permanent == null) {
            return false;
        }
        return creature.damage(permanent.getPower().getValue(), permanent.getId(), game, false, true) > 0;
    }
}
