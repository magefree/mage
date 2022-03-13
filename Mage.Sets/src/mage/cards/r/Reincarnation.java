
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Reincarnation extends CardImpl {

    public Reincarnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{G}");

        // Choose target creature. When that creature dies this turn, return a creature card from its owner's graveyard to the battlefield under the control of that creature's owner.
        this.getSpellAbility().addEffect(new ReincarnationEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Reincarnation(final Reincarnation card) {
        super(card);
    }

    @Override
    public Reincarnation copy() {
        return new Reincarnation(this);
    }
}

class ReincarnationEffect extends OneShotEffect {

    public ReincarnationEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose target creature. When that creature dies this turn, return a creature card from its owner's graveyard to the battlefield under the control of that creature's owner";
    }

    public ReincarnationEffect(final ReincarnationEffect effect) {
        super(effect);
    }

    @Override
    public ReincarnationEffect copy() {
        return new ReincarnationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new ReincarnationDelayedTriggeredAbility(targetPointer.getFirst(game, source));
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}

class ReincarnationDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID target;

    public ReincarnationDelayedTriggeredAbility(UUID target) {
        super(new ReincarnationDelayedEffect(target), Duration.EndOfTurn);
        this.target = target;
    }

    public ReincarnationDelayedTriggeredAbility(ReincarnationDelayedTriggeredAbility ability) {
        super(ability);
        this.target = ability.target;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(target)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ReincarnationDelayedTriggeredAbility copy() {
        return new ReincarnationDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, return a creature card from its owner's graveyard to the battlefield under the control of that creature's owner.";
    }
}

class ReincarnationDelayedEffect extends OneShotEffect {

    private final UUID target;

    public ReincarnationDelayedEffect(UUID target) {
        super(Outcome.Detriment);
        this.target = target;
        this.staticText = "return a creature card from its owner's graveyard to the battlefield under the control of that creature's owner";
    }

    public ReincarnationDelayedEffect(final ReincarnationDelayedEffect effect) {
        super(effect);
        this.target = effect.target;
    }

    @Override
    public ReincarnationDelayedEffect copy() {
        return new ReincarnationDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) game.getLastKnownInformation(target, Zone.BATTLEFIELD);
        if (permanent != null && controller != null) {
            Player player = game.getPlayer(permanent.getOwnerId());
            if (player != null) {
                FilterCreatureCard filter = new FilterCreatureCard("a creature card from " + player.getName() + "'s graveyard");
                filter.add(new OwnerIdPredicate(player.getId()));
                Target targetCreature = new TargetCardInGraveyard(filter);
                targetCreature.setNotTarget(true);
                if (targetCreature.canChoose(controller.getId(), source, game)
                        && controller.chooseTarget(outcome, targetCreature, source, game)) {
                    Card card = game.getCard(targetCreature.getFirstTarget());
                    if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
