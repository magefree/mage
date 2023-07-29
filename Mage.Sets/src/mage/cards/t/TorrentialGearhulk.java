package mage.cards.t;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TorrentialGearhulk extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant card from your graveyard");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public TorrentialGearhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Torrential Gearhulk enters the battlefield, you may cast target 
        // instant card from your graveyard without paying its mana cost.
        // If that card would be put into your graveyard this turn, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TorrentialGearhulkEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private TorrentialGearhulk(final TorrentialGearhulk card) {
        super(card);
    }

    @Override
    public TorrentialGearhulk copy() {
        return new TorrentialGearhulk(this);
    }
}

class TorrentialGearhulkEffect extends OneShotEffect {

    TorrentialGearhulkEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast target instant card from your graveyard without paying its mana cost. "
                + "If that spell would be put into your graveyard, exile it instead";
    }

    TorrentialGearhulkEffect(final TorrentialGearhulkEffect effect) {
        super(effect);
    }

    @Override
    public TorrentialGearhulkEffect copy() {
        return new TorrentialGearhulkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (controller != null && card != null) {
            if (controller.chooseUse(outcome, "Cast " + card.getLogName() + '?', source, game)) {
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                if (cardWasCast) {
                    ContinuousEffect effect = new TorrentialGearhulkReplacementEffect(card.getId());
                    effect.setTargetPointer(new FixedTarget(card.getId(), game.getState().getZoneChangeCounter(card.getId())));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class TorrentialGearhulkReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    TorrentialGearhulkReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
        staticText = "If that card would be put into your graveyard this turn, exile it instead";
    }

    TorrentialGearhulkReplacementEffect(final TorrentialGearhulkReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public TorrentialGearhulkReplacementEffect copy() {
        return new TorrentialGearhulkReplacementEffect(this);
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
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getTargetId().equals(this.cardId);
    }
}
