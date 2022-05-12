
package mage.cards.n;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public final class NefariousLich extends CardImpl {

    public NefariousLich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}{B}{B}{B}");

        // If damage would be dealt to you, exile that many cards from your graveyard instead. If you can't, you lose the game.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NefariousLichDamageReplacementEffect()));

        // If you would gain life, draw that many cards instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NefariousLichLifeGainReplacementEffect()));

        // When Nefarious Lich leaves the battlefield, you lose the game.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new LoseGameSourceControllerEffect(), false));
    }

    private NefariousLich(final NefariousLich card) {
        super(card);
    }

    @Override
    public NefariousLich copy() {
        return new NefariousLich(this);
    }
}

class NefariousLichDamageReplacementEffect extends ReplacementEffectImpl {

    private int amount = 0;

    NefariousLichDamageReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "If damage would be dealt to you, exile that many cards from your graveyard instead. If you can't, you lose the game.";
    }

    NefariousLichDamageReplacementEffect(final NefariousLichDamageReplacementEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public NefariousLichDamageReplacementEffect copy() {
        return new NefariousLichDamageReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(event.getPlayerId());
        if (controller != null) {
            Target target = new TargetCardInYourGraveyard(amount, new FilterCard("card in your graveyard"));
            if (target.canChoose(controller.getId(), source, game)) {
                if (controller.choose(Outcome.Exile, target, source, game)) {
                    Set<Card> cards = new HashSet<>(amount);
                    for (UUID targetId : target.getTargets()) {
                        Card card = controller.getGraveyard().get(targetId, game);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                    controller.moveCardsToExile(cards, source, game, true, null, "");
                    return true;
                }
            }
            controller.lost(game);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getControllerId())) {
            this.amount = event.getAmount();
            return true;
        }
        return false;
    }
}

class NefariousLichLifeGainReplacementEffect extends ReplacementEffectImpl {

    NefariousLichLifeGainReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.DrawCard);
        staticText = "If you would gain life, draw that many cards instead";
    }

    NefariousLichLifeGainReplacementEffect(final NefariousLichLifeGainReplacementEffect effect) {
        super(effect);
    }

    @Override
    public NefariousLichLifeGainReplacementEffect copy() {
        return new NefariousLichLifeGainReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(event.getPlayerId());
        if (controller != null) {
            controller.drawCards(event.getAmount(), source, game); // original event is not a draw event, so skip it in params
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAIN_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}
