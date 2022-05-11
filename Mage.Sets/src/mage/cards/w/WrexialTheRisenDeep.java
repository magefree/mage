package mage.cards.w;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class WrexialTheRisenDeep extends CardImpl {

    public WrexialTheRisenDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(8);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
        // Swampwalk
        this.addAbility(new SwampwalkAbility());

        // Whenever Wrexial, the Risen Deep deals combat damage to a player, 
        // you may cast target instant or sorcery card from that player's graveyard 
        // without paying its mana cost. If that card would be put into a graveyard 
        // this turn, exile it instead.
        this.addAbility(new WrexialTheRisenDeepTriggeredAbility());
    }

    private WrexialTheRisenDeep(final WrexialTheRisenDeep card) {
        super(card);
    }

    @Override
    public WrexialTheRisenDeep copy() {
        return new WrexialTheRisenDeep(this);
    }
}

class WrexialTheRisenDeepTriggeredAbility extends TriggeredAbilityImpl {

    public WrexialTheRisenDeepTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WrexialTheRisenDeepEffect(), true);
    }

    public WrexialTheRisenDeepTriggeredAbility(final WrexialTheRisenDeepTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WrexialTheRisenDeepTriggeredAbility copy() {
        return new WrexialTheRisenDeepTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.sourceId) || !((DamagedPlayerEvent) event).isCombatDamage()) {
            return false;
        }
        Player damagedPlayer = game.getPlayer(event.getTargetId());
        if (damagedPlayer == null) {
            return false;
        }
        FilterCard filter = new FilterCard("target instant or sorcery card from "
                + damagedPlayer.getName() + "'s graveyard");
        filter.add(new OwnerIdPredicate(damagedPlayer.getId()));
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));

        Target target = new TargetCardInGraveyard(filter);
        this.getTargets().clear();
        this.addTarget(target);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, "
                + "you may cast target instant or sorcery card "
                + "from that player's graveyard without paying its mana cost. "
                + "If that spell would be put into a graveyard this turn, exile it instead.";
    }
}

class WrexialTheRisenDeepEffect extends OneShotEffect {

    public WrexialTheRisenDeepEffect() {
        super(Outcome.PlayForFree);
        staticText = "you may cast target instant or sorcery card from "
                + "that player's graveyard without paying its mana cost. "
                + "If that spell would be put into a graveyard this turn, exile it instead";
    }

    public WrexialTheRisenDeepEffect(final WrexialTheRisenDeepEffect effect) {
        super(effect);
    }

    @Override
    public WrexialTheRisenDeepEffect copy() {
        return new WrexialTheRisenDeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (controller == null
                || card == null) {
            return false;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
        controller.cast(controller.chooseAbilityForCast(card, game, true),
                game, true, new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        game.addEffect(new WrexialReplacementEffect(card.getId()), source);
        return true;
    }
}

class WrexialReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardid;

    public WrexialReplacementEffect(UUID cardid) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardid = cardid;
    }

    public WrexialReplacementEffect(final WrexialReplacementEffect effect) {
        super(effect);
        this.cardid = effect.cardid;
    }

    @Override
    public WrexialReplacementEffect copy() {
        return new WrexialReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && event.getTargetId().equals(cardid);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        UUID eventObject = event.getTargetId();
        StackObject card = game.getStack().getStackObject(eventObject);
        Player controller = game.getPlayer(source.getControllerId());
        if (card != null && controller != null) {
            if (card instanceof Card) {
                return controller.moveCards((Card) card, Zone.EXILED, source, game);
            }
        }
        return false;
    }

}
