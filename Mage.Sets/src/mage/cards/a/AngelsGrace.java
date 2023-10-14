package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class AngelsGrace extends CardImpl {

    public AngelsGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Split second (As long as this spell is on the stack, players can't 
        // cast spells or activate abilities that aren't mana abilities.)
        this.addAbility(new SplitSecondAbility());

        // You can't lose the game this turn and your opponents can't win the 
        // game this turn. Until end of turn, damage that would reduce your 
        // life total to less than 1 reduces it to 1 instead.
        this.getSpellAbility().addEffect(new AngelsGraceEffect());
        this.getSpellAbility().addEffect(new AngelsGraceReplacementEffect());
    }

    private AngelsGrace(final AngelsGrace card) {
        super(card);
    }

    @Override
    public AngelsGrace copy() {
        return new AngelsGrace(this);
    }
}

class AngelsGraceEffect extends ContinuousRuleModifyingEffectImpl {

    public AngelsGraceEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, false, false);
        staticText = "You can't lose the game this turn "
                + "and your opponents can't win the game this turn";
    }

    private AngelsGraceEffect(final AngelsGraceEffect effect) {
        super(effect);
    }

    @Override
    public AngelsGraceEffect copy() {
        return new AngelsGraceEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.WINS || event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return (event.getType() == GameEvent.EventType.WINS
                && game.getOpponents(source.getControllerId()).contains(event.getPlayerId()))
                || (event.getType() == GameEvent.EventType.LOSES
                && event.getPlayerId().equals(source.getControllerId()));
    }

}

class AngelsGraceReplacementEffect extends ReplacementEffectImpl {

    public AngelsGraceReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Until end of turn, damage that would reduce your "
                + "life total to less than 1 reduces it to 1 instead";
    }

    private AngelsGraceReplacementEffect(final AngelsGraceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AngelsGraceReplacementEffect copy() {
        return new AngelsGraceReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CAUSES_LIFE_LOSS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null
                    && controller.getLife() > 0
                    && (controller.getLife() - event.getAmount()) < 1) {
                event.setAmount(controller.getLife() - 1);
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return false;
    }

}
