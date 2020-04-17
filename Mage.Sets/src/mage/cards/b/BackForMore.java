package mage.cards.b;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BackForMore extends CardImpl {

    public BackForMore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}{G}");

        // Return target creature card from your graveyard to the battlefield. When you do, it fights up to one target creature you don't control.
        this.getSpellAbility().addEffect(new BackForMoreEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
        ));
    }

    private BackForMore(final BackForMore card) {
        super(card);
    }

    @Override
    public BackForMore copy() {
        return new BackForMore(this);
    }
}

class BackForMoreEffect extends OneShotEffect {

    BackForMoreEffect() {
        super(Outcome.Benefit);
        staticText = "Return target creature card from your graveyard to the battlefield. " +
                "When you do, it fights up to one target creature you don't control.";
    }

    private BackForMoreEffect(final BackForMoreEffect effect) {
        super(effect);
    }

    @Override
    public BackForMoreEffect copy() {
        return new BackForMoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new BackForMoreReflexiveTriggeredAbility(
                new MageObjectReference(permanent, game)
        ), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class BackForMoreReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    BackForMoreReflexiveTriggeredAbility(MageObjectReference mor) {
        super(new BackForMoreDamageEffect(mor), Duration.OneUse, false);
        this.addTarget(new TargetPermanent(0, 1, filter, false));
    }

    private BackForMoreReflexiveTriggeredAbility(final BackForMoreReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BackForMoreReflexiveTriggeredAbility copy() {
        return new BackForMoreReflexiveTriggeredAbility(this);
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
        return "When you do, it fights up to one target creature you don't control.";
    }
}

class BackForMoreDamageEffect extends OneShotEffect {

    private final MageObjectReference mor;

    BackForMoreDamageEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        this.mor = mor;
    }

    private BackForMoreDamageEffect(final BackForMoreDamageEffect effect) {
        super(effect);
        mor = effect.mor;
    }

    @Override
    public BackForMoreDamageEffect copy() {
        return new BackForMoreDamageEffect(this);
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
        return permanent.fight(creature, source, game);
    }
}
