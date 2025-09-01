package mage.cards.t;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheDarknessCrystal extends CardImpl {

    private static final FilterCard filter = new FilterCard("black spells");
    private static final FilterCard filter2 = new FilterCreatureCard("exiled with this");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter2.add(TheDarknessCrystalPredicate.instance);
    }

    public TheDarknessCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // Black spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // If a nontoken creature an opponent controls would die, instead exile it and you gain 2 life.
        this.addAbility(new SimpleStaticAbility(new TheDarknessCrystalExileEffect()));

        // {4}{B}{B}, {T}: Put target creature card exiled with The Darkness Crystal onto the battlefield tapped under your control with two additional +1/+1 counters on it.
        Ability ability = new SimpleActivatedAbility(
                new TheDarknessCrystalReturnEffect(), new ManaCostsImpl<>("{4}{B}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInExile(filter2));
        this.addAbility(ability);
    }

    private TheDarknessCrystal(final TheDarknessCrystal card) {
        super(card);
    }

    @Override
    public TheDarknessCrystal copy() {
        return new TheDarknessCrystal(this);
    }
}

enum TheDarknessCrystalPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .ofNullable(CardUtil.getExileZoneId(
                        game, input.getSourceId(),
                        game.getState().getZoneChangeCounter(input.getSourceId())
                ))
                .map(game.getExile()::getExileZone)
                .map(e -> e.contains(input.getObject().getId()))
                .orElse(false);
    }
}

class TheDarknessCrystalExileEffect extends ReplacementEffectImpl {

    TheDarknessCrystalExileEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a nontoken creature an opponent controls would die, instead exile it and you gain 2 life";
    }

    private TheDarknessCrystalExileEffect(final TheDarknessCrystalExileEffect effect) {
        super(effect);
    }

    @Override
    public TheDarknessCrystalExileEffect copy() {
        return new TheDarknessCrystalExileEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = Optional
                .ofNullable(event)
                .map(ZoneChangeEvent.class::cast)
                .map(ZoneChangeEvent::getTarget)
                .map(Card.class::cast)
                .orElseGet(() -> game.getCard(event.getTargetId()));
        if (player == null || card == null) {
            return false;
        }
        player.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(
                        game, source.getSourceId(),
                        game.getState().getZoneChangeCounter(source.getSourceId())
                ),
                CardUtil.getSourceName(game, source)
        );
        player.gainLife(2, game, source);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent()
                && zEvent.getTarget().isCreature(game)
                && game.getOpponents(source.getControllerId()).contains(zEvent.getTarget().getControllerId());
    }

}

class TheDarknessCrystalReturnEffect extends OneShotEffect {

    TheDarknessCrystalReturnEffect() {
        super(Outcome.Benefit);
        staticText = "put target creature card exiled with {this} onto the battlefield tapped " +
                "under your control with two additional +1/+1 counters on it";
    }

    private TheDarknessCrystalReturnEffect(final TheDarknessCrystalReturnEffect effect) {
        super(effect);
    }

    @Override
    public TheDarknessCrystalReturnEffect copy() {
        return new TheDarknessCrystalReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        game.setEnterWithCounters(card.getId(), new Counters(CounterType.P1P1.createInstance(2)));
        return player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
    }
}
