package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LorcanWarlockCollector extends CardImpl {

    public LorcanWarlockCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature card is put into an opponent's graveyard from anywhere, you may pay life equal to its mana value. If you do, put it onto the battlefield under your control. It's a Warlock in addition to its other types.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new LorcanWarlockCollectorReturnEffect(),
                true, StaticFilters.FILTER_CARD_CREATURE_A,
                TargetController.OPPONENT, SetTargetPointer.CARD
        ));

        // If a Warlock you control would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(new LorcanWarlockCollectorReplacementEffect()));
    }

    private LorcanWarlockCollector(final LorcanWarlockCollector card) {
        super(card);
    }

    @Override
    public LorcanWarlockCollector copy() {
        return new LorcanWarlockCollector(this);
    }
}

class LorcanWarlockCollectorReturnEffect extends OneShotEffect {

    LorcanWarlockCollectorReturnEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "pay life equal to its mana value. If you do, " +
                "put it onto the battlefield under your control. " +
                "It's a Warlock in addition to its other types";
    }

    private LorcanWarlockCollectorReturnEffect(final LorcanWarlockCollectorReturnEffect effect) {
        super(effect);
    }

    @Override
    public LorcanWarlockCollectorReturnEffect copy() {
        return new LorcanWarlockCollectorReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        Cost cost = new PayLifeCost(card.getManaValue());
        if (!cost.canPay(source, source, source.getControllerId(), game)
                || !cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        game.addEffect(new AddCardSubTypeTargetEffect(SubType.WARLOCK, Duration.Custom).setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game) + 1)), source);
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        return true;
    }
}

class LorcanWarlockCollectorReplacementEffect extends ReplacementEffectImpl {

    LorcanWarlockCollectorReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "if a Warlock you control would die, exile it instead";
    }

    private LorcanWarlockCollectorReplacementEffect(final LorcanWarlockCollectorReplacementEffect effect) {
        super(effect);
    }

    @Override
    public LorcanWarlockCollectorReplacementEffect copy() {
        return new LorcanWarlockCollectorReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        return player != null && player.moveCards(permanent, Zone.EXILED, source, game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getTarget() != null
                && zEvent.getTarget().isControlledBy(source.getControllerId())
                && zEvent.getTarget().hasSubtype(SubType.WARLOCK, game)
                && zEvent.isDiesEvent();
    }
}
