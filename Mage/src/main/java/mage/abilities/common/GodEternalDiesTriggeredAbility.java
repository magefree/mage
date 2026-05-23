package mage.abilities.common;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class GodEternalDiesTriggeredAbility extends TriggeredAbilityImpl {

    public GodEternalDiesTriggeredAbility() {
        super(Zone.ALL, new GodEternalEffect(), true);
        setLeavesTheBattlefieldTrigger(true);
    }

    private GodEternalDiesTriggeredAbility(GodEternalDiesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            return zEvent.getFromZone() == Zone.BATTLEFIELD
                    && (zEvent.getToZone() == Zone.GRAVEYARD
                    || zEvent.getToZone() == Zone.EXILED);
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getTargetId().equals(this.getSourceId())) {
            this.getEffects().setTargetPointer(new FixedTargets(
                    CardUtil.getAllCardsFromPermanentLeftBattlefield(zEvent.getTarget(), game), game));
            return true;
        }
        return false;
    }

    @Override
    public GodEternalDiesTriggeredAbility copy() {
        return new GodEternalDiesTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When {this} dies or is put into exile from the battlefield, "
                + "you may put it into its owner's library third from the top.";
    }
}

class GodEternalEffect extends OneShotEffect {

    GodEternalEffect() {
        super(Outcome.Benefit);
    }

    private GodEternalEffect(final GodEternalEffect effect) {
        super(effect);
    }

    @Override
    public GodEternalEffect copy() {
        return new GodEternalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .map(MageItem::getId)
                .collect(Collectors.toCollection(CardsImpl::new));
        if (player == null || cards.isEmpty()) {
            return false;
        }
        return player.putCardsOnTopXOfLibrary(cards, game, source, 3, true);
    }
}
