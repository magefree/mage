package mage.cards.t;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class ToshiroUmezawa extends CardImpl {
    private static final FilterCard filterInstant = new FilterCard("instant card from your graveyard");

    static {
        filterInstant.add(CardType.INSTANT.getPredicate());
    }

    public ToshiroUmezawa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bushido 1
        this.addAbility(new BushidoAbility(1));
        // Whenever a creature an opponent controls dies, you may cast target 
        // instant card from your graveyard. If that card would be put into a 
        // graveyard this turn, exile it instead.
        Ability ability = new DiesCreatureTriggeredAbility(new ToshiroUmezawaEffect(), true, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE);
        ability.addTarget(new TargetCardInYourGraveyard(1, 1, filterInstant));
        this.addAbility(ability);

    }

    private ToshiroUmezawa(final ToshiroUmezawa card) {
        super(card);
    }

    @Override
    public ToshiroUmezawa copy() {
        return new ToshiroUmezawa(this);
    }
}

class ToshiroUmezawaEffect extends OneShotEffect {

    public ToshiroUmezawaEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may cast target instant card from your graveyard. "
                + "If that spell would be put into a graveyard this turn, exile it instead";
    }

    public ToshiroUmezawaEffect(final ToshiroUmezawaEffect effect) {
        super(effect);
    }

    @Override
    public ToshiroUmezawaEffect copy() {
        return new ToshiroUmezawaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null
                    && controller.getGraveyard().contains(card.getId())) { // must be in graveyard
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                controller.cast(controller.chooseAbilityForCast(card, game, false),
                        game, false, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                game.addEffect(new ToshiroUmezawaReplacementEffect(card.getId()), source);
            }
        }
        return false;
    }
}

class ToshiroUmezawaReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    public ToshiroUmezawaReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
    }

    public ToshiroUmezawaReplacementEffect(final ToshiroUmezawaReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public ToshiroUmezawaReplacementEffect copy() {
        return new ToshiroUmezawaReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        UUID eventObject = event.getTargetId();
        StackObject stackObject = game.getStack().getStackObject(eventObject);
        Player controller = game.getPlayer(source.getControllerId());
        if (stackObject != null && controller != null) {
            if (stackObject instanceof Spell) {
                game.rememberLKI(stackObject.getId(), Zone.STACK, stackObject);
            }
            if (stackObject instanceof Card && eventObject.equals(cardId)) {
                return controller.moveCards((Card) stackObject, Zone.EXILED, source, game);
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
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && event.getTargetId().equals(cardId);
    }
}
