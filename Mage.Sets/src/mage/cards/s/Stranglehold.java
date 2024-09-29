package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SkipExtraTurnsAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class Stranglehold extends CardImpl {

    public Stranglehold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");

        // Your opponents can't search libraries.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OpponentsCantSearchLibrariesEffect()));

        // If an opponent would begin an extra turn, that player skips that turn instead.
        this.addAbility(new SkipExtraTurnsAbility(true));
    }

    private Stranglehold(final Stranglehold card) {
        super(card);
    }

    @Override
    public Stranglehold copy() {
        return new Stranglehold(this);
    }
}

class OpponentsCantSearchLibrariesEffect extends ContinuousRuleModifyingEffectImpl {

    OpponentsCantSearchLibrariesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, true, false);
        staticText = "Your opponents can't search libraries";
    }

    private OpponentsCantSearchLibrariesEffect(final OpponentsCantSearchLibrariesEffect effect) {
        super(effect);
    }

    @Override
    public OpponentsCantSearchLibrariesEffect copy() {
        return new OpponentsCantSearchLibrariesEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't search libraries (" + mageObject.getLogName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return EventType.SEARCH_LIBRARY == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && controller.hasOpponent(event.getPlayerId(), game);
    }
}
