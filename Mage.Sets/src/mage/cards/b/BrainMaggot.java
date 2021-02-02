
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class BrainMaggot extends CardImpl {

    public BrainMaggot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Brain Maggot enters the battlefield, target opponent reveals their hand and you choose a nonland card from it. Exile that card until Brain Maggot leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BrainMaggotExileEffect());
        ability.addTarget(new TargetOpponent());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new BrainMaggotReturnExiledCardAbility()));
        this.addAbility(ability);
    }

    private BrainMaggot(final BrainMaggot card) {
        super(card);
    }

    @Override
    public BrainMaggot copy() {
        return new BrainMaggot(this);
    }
}

class BrainMaggotExileEffect extends OneShotEffect {

    public BrainMaggotExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "target opponent reveals their hand and you choose a nonland card from it. Exile that card until {this} leaves the battlefield";
    }

    public BrainMaggotExileEffect(final BrainMaggotExileEffect effect) {
        super(effect);
    }

    @Override
    public BrainMaggotExileEffect copy() {
        return new BrainMaggotExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && opponent != null && sourcePermanent != null) {
            if (!opponent.getHand().isEmpty()) {
                opponent.revealCards(sourcePermanent.getIdName(), opponent.getHand(), game);

                FilterCard filter = new FilterNonlandCard("nonland card to exile");
                TargetCard target = new TargetCard(Zone.HAND, filter);
                if (opponent.getHand().count(filter, game) > 0 && controller.choose(Outcome.Exile, opponent.getHand(), target, game)) {
                    Card card = opponent.getHand().get(target.getFirstTarget(), game);
                    // If source permanent leaves the battlefield before its triggered ability resolves, the target card won't be exiled.
                    if (card != null && game.getState().getZone(source.getSourceId()) == Zone.BATTLEFIELD) {
                        controller.moveCardToExileWithInfo(card, CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), sourcePermanent.getIdName(), source, game, Zone.HAND, true);
                    }
                }
            }
            return true;
        }
        return false;

    }
}

/**
 * Returns the exiled card as source permanent leaves battlefield Uses no stack
 *
 * @author LevelX2
 */
class BrainMaggotReturnExiledCardAbility extends DelayedTriggeredAbility {

    public BrainMaggotReturnExiledCardAbility() {
        super(new BrainMaggotReturnExiledCardEffect(), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    public BrainMaggotReturnExiledCardAbility(final BrainMaggotReturnExiledCardAbility ability) {
        super(ability);
    }

    @Override
    public BrainMaggotReturnExiledCardAbility copy() {
        return new BrainMaggotReturnExiledCardAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                return true;
            }
        }
        return false;
    }
}

class BrainMaggotReturnExiledCardEffect extends OneShotEffect {

    public BrainMaggotReturnExiledCardEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return exiled nonland card to its owner's hand";
    }

    public BrainMaggotReturnExiledCardEffect(final BrainMaggotReturnExiledCardEffect effect) {
        super(effect);
    }

    @Override
    public BrainMaggotReturnExiledCardEffect copy() {
        return new BrainMaggotReturnExiledCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()));
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (exile != null && sourcePermanent != null) {
                controller.moveCards(exile, Zone.HAND, source, game);
                return true;
            }
        }
        return false;
    }
}
