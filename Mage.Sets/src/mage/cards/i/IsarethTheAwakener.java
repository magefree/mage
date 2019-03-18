package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class IsarethTheAwakener extends CardImpl {

    public IsarethTheAwakener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Isareth the Awakener attacks, you may pay {X}. When you do, return target creature card with converted mana cost X from your graveyard to the battlefield with a corpse counter on it. If that creature would leave the battlefield, exile it instead of putting it anywhere else.
        this.addAbility(new AttacksTriggeredAbility(new IsarethTheAwakenerCreateReflexiveTriggerEffect(), false));
    }

    public IsarethTheAwakener(final IsarethTheAwakener card) {
        super(card);
    }

    @Override
    public IsarethTheAwakener copy() {
        return new IsarethTheAwakener(this);
    }
}

class IsarethTheAwakenerCreateReflexiveTriggerEffect extends OneShotEffect {

    public IsarethTheAwakenerCreateReflexiveTriggerEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {X}. When you do, return target creature card "
                + "with converted mana cost X from your graveyard to the battlefield "
                + "with a corpse counter on it. If that creature would leave the battlefield, "
                + "exile it instead of putting it anywhere else.";
    }

    public IsarethTheAwakenerCreateReflexiveTriggerEffect(final IsarethTheAwakenerCreateReflexiveTriggerEffect effect) {
        super(effect);
    }

    @Override
    public IsarethTheAwakenerCreateReflexiveTriggerEffect copy() {
        return new IsarethTheAwakenerCreateReflexiveTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ManaCosts cost = new ManaCostsImpl("{X}");
        if (player == null
                || !player.chooseUse(Outcome.BoostCreature, "Pay " + cost.getText() + "?", source, game)) {
            return false;
        }
        int costX = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
        cost.add(new GenericManaCost(costX));
        if (!cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
            return false;
        }
        game.addDelayedTriggeredAbility(new IsarethTheAwakenerReflexiveTriggeredAbility(), source);
        return new SendOptionUsedEventEffect(costX).apply(game, source);
    }
}

class IsarethTheAwakenerReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    public IsarethTheAwakenerReflexiveTriggeredAbility() {
        super(new IsarethTheAwakenerEffect(), Duration.OneUse, true);
        this.addEffect(new IsarethTheAwakenerReplacementEffect());
    }

    public IsarethTheAwakenerReflexiveTriggeredAbility(final IsarethTheAwakenerReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IsarethTheAwakenerReflexiveTriggeredAbility copy() {
        return new IsarethTheAwakenerReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())
                || !event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        FilterCard filter = new FilterCreatureCard(
                "creature card with converted mana cost "
                + event.getAmount()
                + " or less from your graveyard"
        );
        filter.add(new ConvertedManaCostPredicate(
                ComparisonType.FEWER_THAN, event.getAmount() + 1
        ));
        this.getTargets().clear();
        this.addTarget(new TargetCardInYourGraveyard(filter));
        return true;
    }

    @Override
    public String getRule() {
        return "When you pay {X}, return target creature card "
                + "with converted mana cost X from your graveyard to the battlefield "
                + "with a corpse counter on it. If that creature would leave the battlefield, "
                + "exile it instead of putting it anywhere else.";
    }
}

class IsarethTheAwakenerEffect extends OneShotEffect {

    public IsarethTheAwakenerEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    public IsarethTheAwakenerEffect(final IsarethTheAwakenerEffect effect) {
        super(effect);
    }

    @Override
    public IsarethTheAwakenerEffect copy() {
        return new IsarethTheAwakenerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || card == null) {
            return false;
        }
        Counters countersToAdd = new Counters();
        countersToAdd.addCounter(CounterType.CORPSE.createInstance());
        game.setEnterWithCounters(source.getSourceId(), countersToAdd);
        return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}

class IsarethTheAwakenerReplacementEffect extends ReplacementEffectImpl {

    public IsarethTheAwakenerReplacementEffect() {
        super(Duration.Custom, Outcome.Exile);
    }

    public IsarethTheAwakenerReplacementEffect(final IsarethTheAwakenerReplacementEffect effect) {
        super(effect);
    }

    @Override
    public IsarethTheAwakenerReplacementEffect copy() {
        return new IsarethTheAwakenerReplacementEffect(this);
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
        if (event.getTargetId().equals(source.getFirstTarget())
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD
                && ((ZoneChangeEvent) event).getToZone() != Zone.EXILED) {
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
