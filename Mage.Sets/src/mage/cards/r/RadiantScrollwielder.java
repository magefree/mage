package mage.cards.r;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RadiantScrollwielder extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant and sorcery spells you control");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public RadiantScrollwielder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Instant and sorcery spells you control have lifelink.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(
                LifelinkAbility.getInstance(), filter
        ).setText("instant and sorcery spells you control have lifelink")));

        // At the beginning of your upkeep, exile an instant or sorcery card at random from your graveyard. You may cast it this turn. If a spell cast this way would be put into your graveyard, exile it instead.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new RadiantScrollwielderEffect(), TargetController.YOU, false
        ), new RadiantScrollwielderWatcher());
    }

    private RadiantScrollwielder(final RadiantScrollwielder card) {
        super(card);
    }

    @Override
    public RadiantScrollwielder copy() {
        return new RadiantScrollwielder(this);
    }
}

class RadiantScrollwielderEffect extends OneShotEffect {

    RadiantScrollwielderEffect() {
        super(Outcome.Benefit);
        staticText = "exile an instant or sorcery card at random from your graveyard. You may cast it this turn. " +
                "If a spell cast this way would be put into your graveyard, exile it instead";
    }

    private RadiantScrollwielderEffect(final RadiantScrollwielderEffect effect) {
        super(effect);
    }

    @Override
    public RadiantScrollwielderEffect copy() {
        return new RadiantScrollwielderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game) < 1) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);
        target.setNotTarget(true);
        target.setRandom(true);
        target.chooseTarget(outcome, player.getId(), source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(
                game, source, card, TargetController.YOU,
                Duration.EndOfTurn, false, false, true
        );
        game.addEffect(new RadiantScrollwielderReplacementEffect(card, game), source);
        return true;
    }
}

class RadiantScrollwielderReplacementEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    RadiantScrollwielderReplacementEffect(Card card, Game game) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.mor = new MageObjectReference(card, game, 1);
    }

    private RadiantScrollwielderReplacementEffect(final RadiantScrollwielderReplacementEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public RadiantScrollwielderReplacementEffect copy() {
        return new RadiantScrollwielderReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
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
        return ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && mor.refersTo(game.getCard(event.getSourceId()), game)
                && RadiantScrollwielderWatcher.checkSpell(game.getCard(event.getSourceId()), source, game);
    }
}

class RadiantScrollwielderWatcher extends Watcher {

    private final Map<MageObjectReference, MageObjectReference> morMap = new HashMap<>();

    RadiantScrollwielderWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST || event.getAdditionalReference() == null) {
            return;
        }
        morMap.put(
                new MageObjectReference(event.getSourceId(), game),
                event.getAdditionalReference().getApprovingMageObjectReference()
        );
    }

    @Override
    public void reset() {
        super.reset();
        this.morMap.clear();
    }

    static boolean checkSpell(Card card, Ability source, Game game) {
        if (card == null) {
            return false;
        }
        RadiantScrollwielderWatcher watcher = game.getState().getWatcher(RadiantScrollwielderWatcher.class);
        if (watcher == null) {
            return false;
        }
        MageObjectReference mor = watcher.morMap.getOrDefault(new MageObjectReference(card, game), null);
        return mor != null && mor.refersTo(source.getSourceObject(game), game);
    }
}
