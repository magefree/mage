package mage.cards.e;

import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author kleese
 */
public final class ExpeditedInheritance extends CardImpl {

    public ExpeditedInheritance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{R}");

        // Whenever a creature is dealt damage, its controller may exile that many cards from the top of their library. They may play those cards until the end of their next turn.
        this.addAbility(new ExpeditedInheritanceTriggeredAbility(new ExpeditedInheritanceExileEffect()));
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
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
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

class ExpeditedInheritanceExileEffect extends OneShotEffect {

    ExpeditedInheritanceExileEffect() {
        super(Outcome.Benefit);
        staticText = "Exile that many cards from the top of your library. " +
                "Until the end of your next turn, you may play those cards.";
    }

    private ExpeditedInheritanceExileEffect(final ExpeditedInheritanceExileEffect effect) {
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
                        "Exile " + CardUtil.numberToText(impulseDrawAmount) + " cards from the top of your library. Until the end of your next turn, you may play those cards."
                        : "Exile the top card of your library. Until the end of your next turn, you may play that card.";
                if (player != null && player.chooseUse(outcome, message, source, game)) {
                    Set<Card> cards = player.getLibrary().getTopCards(game, impulseDrawAmount);
                    if (!cards.isEmpty()) {
                        player.moveCards(cards, Zone.EXILED, source, game);
                        for (Card card:cards){
                            ContinuousEffect effect = new ExpeditedInheritanceMayPlayEffect(playerId);
                            effect.setTargetPointer(new FixedTarget(card.getId(), game));
                            game.addEffect(effect, source);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ExpeditedInheritanceExileEffect copy() {
        return new ExpeditedInheritanceExileEffect(this);
    }
}

class ExpeditedInheritanceMayPlayEffect extends AsThoughEffectImpl {

    private int triggeredOnTurn = 0;
    private final UUID cardOwnerId;

    ExpeditedInheritanceMayPlayEffect(UUID playerId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the end of your next turn, you may play this card.";
        this.cardOwnerId = playerId;
    }

    private ExpeditedInheritanceMayPlayEffect(final ExpeditedInheritanceMayPlayEffect effect) {
        super(effect);
        triggeredOnTurn = effect.triggeredOnTurn;
        cardOwnerId = effect.cardOwnerId;
    }

    @Override
    public ExpeditedInheritanceMayPlayEffect copy() {
        return new ExpeditedInheritanceMayPlayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        triggeredOnTurn = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return triggeredOnTurn != game.getTurnNum()
                && game.getPhase().getStep().getType() == PhaseStep.END_TURN
                && game.isActivePlayer(cardOwnerId);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        UUID objectIdToCast = CardUtil.getMainCardId(game, sourceId);
        return cardOwnerId != null && cardOwnerId.equals(affectedControllerId)
                && getTargetPointer().getTargets(game, source).contains(objectIdToCast);
    }
}
