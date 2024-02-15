package mage.cards.e;

import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author kleese
 */
public final class ExpeditedInheritance extends CardImpl {

    public ExpeditedInheritance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{R}");

        // Whenever a creature is dealt damage, its controller may exile that many cards from the top of their library. They may play those cards until the end of their next turn.
        this.addAbility(new ExpeditedInheritanceTriggeredAbility(new ExpeditedInheritanceEffect()));
    }

    private ExpeditedInheritance(final ExpeditedInheritance card) {
        super(card);
    }

    @Override
    public ExpeditedInheritance copy() {
        return new ExpeditedInheritance(this);
    }
}

class ExpeditedInheritanceTriggeredAbility extends TriggeredAbilityImpl {

    static final String IMPULSE_DRAW_AMOUNT_KEY = "playerDamage";
    static final String TRIGGERING_CREATURE_KEY = "triggeringCreature";

    public ExpeditedInheritanceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    private ExpeditedInheritanceTriggeredAbility(final ExpeditedInheritanceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        getEffects().setValue(IMPULSE_DRAW_AMOUNT_KEY, event.getAmount());
        getEffects().setValue(TRIGGERING_CREATURE_KEY, new MageObjectReference(event.getTargetId(), game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature is dealt damage, its controller may exile that many cards from the top of their library. They may play those cards until the end of their next turn.";
    }

    @Override
    public ExpeditedInheritanceTriggeredAbility copy() {
        return new ExpeditedInheritanceTriggeredAbility(this);
    }
}

class ExpeditedInheritanceEffect extends OneShotEffect {

    ExpeditedInheritanceEffect() {
        super(Outcome.Benefit);
        staticText = "Exile that many cards from the top of your library. " +
                "Until the end of your next turn, you may play those cards.";
    }

    private ExpeditedInheritanceEffect(final ExpeditedInheritanceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer impulseDrawAmount = (Integer) this.getValue(ExpeditedInheritanceTriggeredAbility.IMPULSE_DRAW_AMOUNT_KEY);
        MageObjectReference mor = (MageObjectReference) this.getValue(ExpeditedInheritanceTriggeredAbility.TRIGGERING_CREATURE_KEY);
        if (impulseDrawAmount != null && mor != null) {
            Permanent creature = mor.getPermanentOrLKIBattlefield(game);
            if (creature != null) {
                UUID playerId = creature.getControllerId();
                Player player = game.getPlayer(playerId);
                String message = impulseDrawAmount > 1 ?
                        "Exile " + impulseDrawAmount + " cards from the top of your library. Until the end of your next turn, you may play those cards."
                        : "Exile the top card of your library. Until the end of your next turn, you may play that card.";
                if (player != null && player.chooseUse(outcome, message, source, game)) {
                    Set<Card> cards = player.getLibrary().getTopCards(game, impulseDrawAmount);
                    if (!cards.isEmpty()) {
                        player.moveCards(cards, Zone.EXILED, source, game);
                        for (Card card:cards){
                            CardUtil.makeCardPlayableWithOwnerDuration(
                                    game, source, card, Duration.UntilEndOfYourNextTurn, playerId
                            );
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ExpeditedInheritanceEffect copy() {
        return new ExpeditedInheritanceEffect(this);
    }
}
