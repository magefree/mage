package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import java.util.UUID;
import mage.Mana;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 *
 * @author jeffwadsworth
 */

public class GlimpseTheCosmos extends CardImpl {

    public GlimpseTheCosmos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                StaticValue.get(3), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD, Zone.LIBRARY, false,
                false, false, Zone.HAND, false
        ).setText("look at the top three cards of your library. "
                + "Put one of them into your hand and the rest on the bottom of your library in any order"));

        //As long as you control a Giant, you may cast Glimpse the Cosmos from your graveyard by paying {U} rather than paying its mana cost. If you cast Glimpse the Cosmos this way and it would be put into your graveyard, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD,
                new ConditionalAsThoughEffect(
                        new GlimpseTheCosmosPlayEffect(),
                        new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.GIANT)))));

        this.addAbility(new SimpleStaticAbility(Zone.ALL, new GlimpseTheCosmosReplacementEffect()));

    }

    private GlimpseTheCosmos(final GlimpseTheCosmos card) {
        super(card);
    }

    @Override
    public GlimpseTheCosmos copy() {
        return new GlimpseTheCosmos(this);
    }

}

class GlimpseTheCosmosPlayEffect extends AsThoughEffectImpl {

    public GlimpseTheCosmosPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "As long as you control a Giant, you may cast {this} from your graveyard by paying {U} rather than paying its mana cost";
    }

    public GlimpseTheCosmosPlayEffect(final GlimpseTheCosmosPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GlimpseTheCosmosPlayEffect copy() {
        return new GlimpseTheCosmosPlayEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId())
                && source.isControlledBy(affectedControllerId)) {
            if (game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                Player controller = game.getPlayer(affectedControllerId);
                if (controller != null) {
                    controller.setCastSourceIdWithAlternateMana(sourceId, new ManaCostsImpl<>("{U}"), null);
                    return true;
                }
            }
        }
        return false;
    }

}

class GlimpseTheCosmosReplacementEffect extends ReplacementEffectImpl {

    public GlimpseTheCosmosReplacementEffect() {
        super(Duration.OneUse, Outcome.Exile);
        staticText = "As long as you control a Giant, you may cast {this} from your graveyard by paying {U} rather than paying its mana cost.  If you cast {this} this way and it would be put into your graveyard, exile it instead";
    }

    public GlimpseTheCosmosReplacementEffect(final GlimpseTheCosmosReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GlimpseTheCosmosReplacementEffect copy() {
        return new GlimpseTheCosmosReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                discard();
                return controller.moveCards(
                        card, Zone.EXILED, source, game, false, false, false, event.getAppliedEffects());
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ManaSpentToCastWatcher watcher = game.getState().getWatcher(ManaSpentToCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        Mana payment = watcher.getAndResetLastPayment(source.getSourceId());
        if (payment != null
                && payment.getBlue() == 1 // must be blue mana
                && payment.count() == 1) {  // must be just one
            if (event.getTargetId().equals(source.getSourceId())
                    && ((ZoneChangeEvent) event).getFromZone() == Zone.STACK
                    && ((ZoneChangeEvent) event).getToZone() != Zone.EXILED) {
                return true;
            }
        }
        return false;
    }
}
