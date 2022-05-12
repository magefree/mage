package mage.cards.i;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvokeCalamity extends CardImpl {

    public InvokeCalamity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}{R}{R}");

        // You may cast up to two instant and/or sorcery spells with total mana value 6 or less from your graveyard and/or hand without paying their mana costs. If those spells would be put into your graveyard, exile them instead. Exile Invoke Calamity.
        this.getSpellAbility().addEffect(new InvokeCalamityEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private InvokeCalamity(final InvokeCalamity card) {
        super(card);
    }

    @Override
    public InvokeCalamity copy() {
        return new InvokeCalamity(this);
    }
}

class InvokeCalamityEffect extends OneShotEffect {

    InvokeCalamityEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast up to two instant and/or sorcery spells " +
                "with total mana value 6 or less from your graveyard and/or hand without paying their mana costs. " +
                "If those spells would be put into your graveyard, exile them instead";
    }

    private InvokeCalamityEffect(final InvokeCalamityEffect effect) {
        super(effect);
    }

    @Override
    public InvokeCalamityEffect copy() {
        return new InvokeCalamityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getHand());
        cards.addAll(player.getGraveyard());
        CardUtil.castMultipleWithAttributeForFree(
                player, source, game, cards,
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY,
                2, new InvokeCalamityTracker()
        );
        return true;
    }
}

class InvokeCalamityTracker implements CardUtil.SpellCastTracker {

    private int totalManaValue = 0;

    @Override
    public boolean checkCard(Card card, Game game) {
        return card.getManaValue() + totalManaValue <= 6;
    }

    @Override
    public void addCard(Card card, Ability source, Game game) {
        totalManaValue += card.getManaValue();
        game.addEffect(new InvokeCalamityReplacementEffect(card, game), source);
    }
}

class InvokeCalamityReplacementEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    InvokeCalamityReplacementEffect(Card card, Game game) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.mor = new MageObjectReference(card.getMainCard(), game);
    }

    private InvokeCalamityReplacementEffect(final InvokeCalamityReplacementEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public InvokeCalamityReplacementEffect copy() {
        return new InvokeCalamityReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = mor.getCard(game);
        return controller != null
                && card != null
                && controller.moveCards(card, Zone.EXILED, source, game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getTargetId().equals(mor.getSourceId());
    }
}
