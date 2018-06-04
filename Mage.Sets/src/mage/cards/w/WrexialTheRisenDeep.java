
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
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
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
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

        // Whenever Wrexial, the Risen Deep deals combat damage to a player, you may cast target instant or sorcery card from that player's graveyard without paying its mana cost. If that card would be put into a graveyard this turn, exile it instead.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new WrexialEffect(), true, true));
    }

    public WrexialTheRisenDeep(final WrexialTheRisenDeep card) {
        super(card);
    }

    @Override
    public WrexialTheRisenDeep copy() {
        return new WrexialTheRisenDeep(this);
    }
}

class WrexialEffect extends OneShotEffect {

    public WrexialEffect() {
        super(Outcome.PlayForFree);
        staticText = "you may cast target instant or sorcery card from that player's graveyard without paying its mana cost. If that card would be put into a graveyard this turn, exile it instead";
    }

    public WrexialEffect(final WrexialEffect effect) {
        super(effect);
    }

    @Override
    public WrexialEffect copy() {
        return new WrexialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
            if (damagedPlayer == null) {
                return false;
            }
            FilterCard filter = new FilterCard("target instant or sorcery card from " + damagedPlayer.getName() + "'s graveyard");
            filter.add(new OwnerIdPredicate(damagedPlayer.getId()));
            filter.add(Predicates.or(
                    new CardTypePredicate(CardType.INSTANT),
                    new CardTypePredicate(CardType.SORCERY)));

            Target target = new TargetCardInGraveyard(filter);
            if (controller.chooseTarget(Outcome.PlayForFree, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    controller.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                    game.addEffect(new WrexialReplacementEffect(card.getId()), source);
                }
            }
            return true;
        }
        return false;
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
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getTargetId().equals(cardid);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        UUID eventObject = ((ZoneChangeEvent) event).getTargetId();
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
