package mage.cards.g;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoliathDaydreamer extends CardImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell("an instant or sorcery spell from your hand");

    static {
        filter.add(new CastFromZonePredicate(Zone.HAND));
    }

    public GoliathDaydreamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast an instant or sorcery spell from your hand, exile that card with a dream counter on it instead of putting it into your graveyard as it resolves.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new GoliathDaydreamerExileEffect(), filter, false, SetTargetPointer.SPELL
        ));

        // Whenever this creature attacks, you may cast a spell from among cards you own in exile with dream counters on them without paying its mana cost.
        this.addAbility(new AttacksTriggeredAbility(new GoliathDaydreamerCastEffect()));
    }

    private GoliathDaydreamer(final GoliathDaydreamer card) {
        super(card);
    }

    @Override
    public GoliathDaydreamer copy() {
        return new GoliathDaydreamer(this);
    }
}

class GoliathDaydreamerExileEffect extends ReplacementEffectImpl {

    GoliathDaydreamerExileEffect() {
        super(Duration.WhileOnStack, Outcome.Exile);
        staticText = "exile that card with a dream counter on it instead of putting it into your graveyard as it resolves";
    }

    private GoliathDaydreamerExileEffect(final GoliathDaydreamerExileEffect effect) {
        super(effect);
    }

    @Override
    public GoliathDaydreamerExileEffect copy() {
        return new GoliathDaydreamerExileEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell sourceSpell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (sourceSpell == null || sourceSpell.isCopy()) {
            return false;
        }
        Player player = game.getPlayer(sourceSpell.getOwnerId());
        if (player == null) {
            return false;
        }
        player.moveCards(sourceSpell, Zone.EXILED, source, game);
        sourceSpell.getMainCard().addCounters(CounterType.DREAM.createInstance(), source, game);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = ((ZoneChangeEvent) event);
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        return Zone.STACK.match(zEvent.getFromZone())
                && Zone.GRAVEYARD.match(zEvent.getToZone())
                && spell != null
                && event.getSourceId().equals(spell.getId())
                && Optional
                .ofNullable(spell.getMainCard())
                .map(MageItem::getId)
                .filter(event.getTargetId()::equals)
                .isPresent();
    }
}

class GoliathDaydreamerCastEffect extends OneShotEffect {

    GoliathDaydreamerCastEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast a spell from among cards you own in exile with dream counters on them without paying its mana cost";
    }

    private GoliathDaydreamerCastEffect(final GoliathDaydreamerCastEffect effect) {
        super(effect);
    }

    @Override
    public GoliathDaydreamerCastEffect copy() {
        return new GoliathDaydreamerCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Card> cards = game.getExile().getCardsOwned(game, source.getControllerId());
        cards.removeIf(card -> !card.getCounters(game).containsKey(CounterType.DREAM));
        return CardUtil.castSpellWithAttributesForFree(player, source, game, new CardsImpl(cards), StaticFilters.FILTER_CARD);
    }
}
