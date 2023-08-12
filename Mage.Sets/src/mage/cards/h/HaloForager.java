package mage.cards.h;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HaloForager extends CardImpl {

    public HaloForager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Halo Forager enters the battlefield, you may pay {X}. When you do, you may cast target instant or sorcery card with mana value X from a graveyard without paying its mana cost. If that spell would be put into a graveyard, exile it instead.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HaloForagerPayEffect()));
    }

    private HaloForager(final HaloForager card) {
        super(card);
    }

    @Override
    public HaloForager copy() {
        return new HaloForager(this);
    }
}

class HaloForagerPayEffect extends OneShotEffect {

    HaloForagerPayEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}. When you do, you may cast target instant or sorcery card " +
                "with mana value X from a graveyard without paying its mana cost. " +
                "If that spell would be put into a graveyard, exile it instead.";
    }

    private HaloForagerPayEffect(final HaloForagerPayEffect effect) {
        super(effect);
    }

    @Override
    public HaloForagerPayEffect copy() {
        return new HaloForagerPayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ManaCosts cost = new ManaCostsImpl<>("{X}");
        if (player == null || !player.chooseUse(outcome, "Pay " + cost.getText() + "?", source, game)) {
            return false;
        }
        int costX = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
        cost.add(new GenericManaCost(costX));
        if (!cost.pay(source, game, source, source.getControllerId(), false, null)) {
            return false;
        }
        FilterCard filter = new FilterInstantOrSorceryCard(
                "instant or sorcery card with mana value " + costX + " from a graveyard"
        );
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, costX));
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new HaloForagerCastEffect(costX), false);
        ability.addTarget(new TargetCardInGraveyard(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class HaloForagerCastEffect extends OneShotEffect {

    HaloForagerCastEffect(int costX) {
        super(Outcome.Benefit);
        staticText = "You may cast target instant or sorcery card with mana value " + costX + " from a graveyard " +
                "without paying its mana cost. If that spell would be put into a graveyard, exile it instead";
    }

    private HaloForagerCastEffect(final HaloForagerCastEffect effect) {
        super(effect);
    }

    @Override
    public HaloForagerCastEffect copy() {
        return new HaloForagerCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        return player != null && card != null
                && CardUtil.castSpellWithAttributesForFree(
                player, source, game, new CardsImpl(card),
                StaticFilters.FILTER_CARD, HaloForagerTracker.instance
        );
    }
}

enum HaloForagerTracker implements CardUtil.SpellCastTracker {
    instance;

    @Override
    public boolean checkCard(Card card, Game game) {
        return true;
    }

    @Override
    public void addCard(Card card, Ability source, Game game) {
        game.addEffect(new HaloForagerReplacementEffect(card, game), source);
    }
}

class HaloForagerReplacementEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    HaloForagerReplacementEffect(Card card, Game game) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.mor = new MageObjectReference(card.getMainCard(), game);
    }

    private HaloForagerReplacementEffect(final HaloForagerReplacementEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public HaloForagerReplacementEffect copy() {
        return new HaloForagerReplacementEffect(this);
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
