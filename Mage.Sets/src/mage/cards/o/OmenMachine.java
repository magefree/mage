
package mage.cards.o;

import java.util.UUID;
import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public final class OmenMachine extends CardImpl {

    public OmenMachine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // Players can't draw cards.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OmenMachineEffect()));

        // At the beginning of each player's draw step, that player exiles the top card of their library. If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new OmenMachineEffect2(), TargetController.ANY, false));
    }

    private OmenMachine(final OmenMachine card) {
        super(card);
    }

    @Override
    public OmenMachine copy() {
        return new OmenMachine(this);
    }
}

class OmenMachineEffect extends ContinuousRuleModifyingEffectImpl {

    public OmenMachineEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, false, false);
        staticText = "Players can't draw cards";
    }

    public OmenMachineEffect(final OmenMachineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OmenMachineEffect copy() {
        return new OmenMachineEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

}

class OmenMachineEffect2 extends OneShotEffect {

    public OmenMachineEffect2() {
        super(Outcome.PlayForFree);
        staticText = "that player exiles the top card of their library. If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able";
    }

    public OmenMachineEffect2(final OmenMachineEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                player.moveCards(card, Zone.EXILED, source, game);
                if (card.isLand(game)) {
                    player.moveCards(card, Zone.BATTLEFIELD, source, game);
                } else {
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                    player.cast(player.chooseAbilityForCast(card, game, true), game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public OmenMachineEffect2 copy() {
        return new OmenMachineEffect2(this);
    }

}
