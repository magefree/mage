
package mage.cards.p;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * 
 * @author L_J
 */
public final class Purgatory extends CardImpl {

    public Purgatory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{B}");

        // Whenever a nontoken creature is put into your graveyard from the battlefield, exile that card.
        this.addAbility(new PurgatoryTriggeredAbility());

        // At the beginning of your upkeep, you may pay {4} and 2 life. If you do, return a card exiled with Purgatory to the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, 
            new DoIfCostPaid(new PurgatoryReturnEffect(), 
            new CompositeCost(new GenericManaCost(4), new PayLifeCost(2), "{4} and 2 life")),
            TargetController.YOU, 
            false));
    }

    private Purgatory(final Purgatory card) {
        super(card);
    }

    @Override
    public Purgatory copy() {
        return new Purgatory(this);
    }
}

class PurgatoryTriggeredAbility extends TriggeredAbilityImpl {

    PurgatoryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PurgatoryExileEffect(), false);
    }

    private PurgatoryTriggeredAbility(final PurgatoryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PurgatoryTriggeredAbility copy() {
        return new PurgatoryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(getSourceId());
        if (sourcePermanent != null) {
            Player controller = game.getPlayer(sourcePermanent.getControllerId());
            if (controller != null) {
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                Permanent permanent = zEvent.getTarget();
                if (permanent != null && zEvent.isDiesEvent()
                        && !(permanent instanceof PermanentToken)
                        && permanent.isCreature(game)
                        && permanent.isOwnedBy(controller.getId())) {
        
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(permanent, game));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature is put into your graveyard from the battlefield, exile that card.";
    }
}

class PurgatoryExileEffect extends OneShotEffect {

    public PurgatoryExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile that card";
    }

    private PurgatoryExileEffect(final PurgatoryExileEffect effect) {
        super(effect);
    }
    
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player sourceController = game.getPlayer(source.getControllerId());
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        MageObject sourceObject = source.getSourceObject(game);
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (sourceController != null && exileId != null && sourceObject != null && card != null) {
            if (game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                sourceController.moveCardsToExile(card, source, game, true, exileId, sourceObject.getIdName());
            }
            return true;
        }
        return false;
    }

    @Override
    public PurgatoryExileEffect copy() {
        return new PurgatoryExileEffect(this);
    }

}

class PurgatoryReturnEffect extends OneShotEffect {

    public PurgatoryReturnEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return a card exiled with {this} to the battlefield";
    }

    private PurgatoryReturnEffect(final PurgatoryReturnEffect effect) {
        super(effect);
    }

    @Override
    public PurgatoryReturnEffect copy() {
        return new PurgatoryReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && exileId != null && sourceObject != null) {
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            if (exileZone != null) {
                TargetCard targetCard = new TargetCard(Zone.EXILED, new FilterCard());
                controller.chooseTarget(outcome, exileZone, targetCard, source, game);
                Card card = game.getCard(targetCard.getFirstTarget());
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
