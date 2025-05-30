package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.abilities.keyword.HasteAbility;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author Momo2907
 */
public final class ArthurMarigoldKnight extends CardImpl {

    public ArthurMarigoldKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Arthur, Marigold Knight and at least one other creature attack, look at the top six cards of your library. You may put a creature card from among them onto the battlefield tapped and attacking. Put the rest on the bottom of your library in a random order. Return that creature to its owner's hand at end of combat.
        this.addAbility(new ArthurMarigoldKnightTriggeredAbility());
    }

    private ArthurMarigoldKnight(final ArthurMarigoldKnight card) {
        super(card);
    }

    @Override
    public ArthurMarigoldKnight copy() {
        return new ArthurMarigoldKnight(this);
    }
}

class ArthurMarigoldKnightTriggeredAbility extends TriggeredAbilityImpl {

    ArthurMarigoldKnightTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ArthurMarigoldKnightEffect());
        setTriggerPhrase("Whenever {this} and at least one other creature attack, ");
    }

    protected ArthurMarigoldKnightTriggeredAbility(final ArthurMarigoldKnightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= 2 && game.getCombat().getAttackers().contains(this.sourceId);
    }

    @Override
    public ArthurMarigoldKnightTriggeredAbility copy() {
        return new ArthurMarigoldKnightTriggeredAbility(this);
    }
}

class ArthurMarigoldKnightEffect extends OneShotEffect {

    ArthurMarigoldKnightEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "look at the top six cards of your library. " +
                "You may put a creature card from among them onto the battlefield tapped and attacking. " +
                "Put the rest on the bottom of your library in a random order. " +
                "Return that creature to its owner's hand at end of combat.";
    }

    private ArthurMarigoldKnightEffect(final ArthurMarigoldKnightEffect effect){
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null){
            return false;
        }
        // Look at the top six and put one creature on the Battlefield tapped and attacking
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        TargetCardInLibrary targetCardInLibrary = new TargetCardInLibrary(0, 1,
                StaticFilters.FILTER_CARD_CREATURE);
        player.choose(outcome, cards, targetCardInLibrary, source, game);
        Card card = game.getCard(targetCardInLibrary.getFirstTarget());
        if (card == null || !player.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, true, null
        )) {
            return player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent == null) {
            return player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        game.getCombat().addAttackingCreature(permanent.getId(), game);
        cards.remove(card);
        // Return to Hand at end of combat
        Effect returnToHandEffect = new ReturnToHandTargetEffect();
        returnToHandEffect.setTargetPointer(new FixedTarget(permanent, game));
        DelayedTriggeredAbility delayedAbility = new AtTheEndOfCombatDelayedTriggeredAbility(returnToHandEffect);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return player.putCardsOnBottomOfLibrary(cards, game, source, false);
    }

    @Override
    public ArthurMarigoldKnightEffect copy() {
        return new ArthurMarigoldKnightEffect(this);
    }
}
