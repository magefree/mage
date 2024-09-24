package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class FestivalOfEmbers extends CardImpl {

    public FestivalOfEmbers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");


        // During your turn, you may cast instant and sorcery spells from your graveyard by paying 1 life in addition to their other costs.
        this.addAbility(new SimpleStaticAbility(new FestivalOfEmbersCastEffect()));

        // If a card or token would be put into your graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(new FestivalOfEmbersReplacementEffect()));

        // {1}{R}: Sacrifice Festival of Embers.
        this.addAbility(new SimpleActivatedAbility(new SacrificeSourceEffect(), new ManaCostsImpl<>("{1}{R}")));
    }

    private FestivalOfEmbers(final FestivalOfEmbers card) {
        super(card);
    }

    @Override
    public FestivalOfEmbers copy() {
        return new FestivalOfEmbers(this);
    }
}

//Based on Osteomancer Adept
class FestivalOfEmbersCastEffect extends AsThoughEffectImpl {

    FestivalOfEmbersCastEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.AIDontUseIt);
        staticText = "During your turn, you may cast instant and sorcery spells from your graveyard by paying 1 life in addition to their other costs.";
    }

    private FestivalOfEmbersCastEffect(final FestivalOfEmbersCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public FestivalOfEmbersCastEffect copy() {
        return new FestivalOfEmbersCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        Player player = game.getPlayer(affectedControllerId);
        if (card == null
                || player == null
                || !game.getActivePlayerId().equals(affectedControllerId)
                || !card.isOwnedBy(affectedControllerId)
                || !card.isInstantOrSorcery(game)
                || !game.getState().getZone(objectId).match(Zone.GRAVEYARD)) {
            return false;
        }
        Costs<Cost> newCosts = new CostsImpl<>();
        newCosts.addAll(card.getSpellAbility().getCosts());
        newCosts.add(new PayLifeCost(1));
        player.setCastSourceIdWithAlternateMana(
                card.getId(), card.getManaCost(), newCosts
        );
        return true;
    }
}

// Exact copy of RestInPeaceReplacementEffect
class FestivalOfEmbersReplacementEffect extends ReplacementEffectImpl {

    FestivalOfEmbersReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a card or token would be put into a graveyard from anywhere, exile it instead";
    }

    private FestivalOfEmbersReplacementEffect(final FestivalOfEmbersReplacementEffect effect) {
        super(effect);
    }

    @Override
    public FestivalOfEmbersReplacementEffect copy() {
        return new FestivalOfEmbersReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD;
    }
}
