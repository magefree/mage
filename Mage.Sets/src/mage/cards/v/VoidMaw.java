
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

/**
 * @author jeffwadsworth & L_J
 */
public final class VoidMaw extends CardImpl {

    public VoidMaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // If another creature would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VoidMawEffect()));

        // Put a card exiled with Void Maw into its owner's graveyard: Void Maw gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), new VoidMawCost()));
    }

    private VoidMaw(final VoidMaw card) {
        super(card);
    }

    @Override
    public VoidMaw copy() {
        return new VoidMaw(this);
    }
}

class VoidMawEffect extends ReplacementEffectImpl {

    public VoidMawEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If another creature would die, exile it instead";
    }

    public VoidMawEffect(final VoidMawEffect effect) {
        super(effect);
    }

    @Override
    public VoidMawEffect copy() {
        return new VoidMawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
                Permanent permanent = ((ZoneChangeEvent) event).getTarget();
                if (permanent != null) {
                    UUID exileZoneId = CardUtil.getCardExileZoneId(game, source);
                    if (controller.moveCardsToExile(permanent, source, game, false, exileZoneId, sourceObject.getIdName())) {
                        return true;
                    }
                }
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
        if (zEvent.getToZone() == Zone.GRAVEYARD) {
            Permanent permanent = ((ZoneChangeEvent) event).getTarget();
            if (permanent != null && !permanent.getId().equals(source.getSourceId())) {
                if (zEvent.getTarget() != null) { // if it comes from permanent, check if it was a creature on the battlefield
                    if (zEvent.getTarget().isCreature(game)) {
                        return true;
                    }
                } else if (permanent.isCreature(game)) {
                    return true;
                }
            }
        }
        return false;
    }

}

class VoidMawCost extends CostImpl {

    public VoidMawCost() {
        this.text = "Put a card exiled with {this} into its owner's graveyard";
    }

    public VoidMawCost(VoidMawCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            TargetCardInExile target = new TargetCardInExile(new FilterCard(), CardUtil.getCardExileZoneId(game, ability));
            target.setNotTarget(true);
            Cards cards = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, ability));
            if (cards != null
                    && !cards.isEmpty()
                    && controller.choose(Outcome.Benefit, cards, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    if (controller.moveCardToGraveyardWithInfo(card, source, game, Zone.EXILED)) {
                        paid = true;
                    }
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        return player != null;
    }

    @Override
    public VoidMawCost copy() {
        return new VoidMawCost(this);
    }
}
