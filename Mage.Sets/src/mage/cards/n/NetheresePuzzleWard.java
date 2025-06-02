package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DieRolledEvent;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NetheresePuzzleWard extends CardImpl {

    public NetheresePuzzleWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Focus Beam — At the beginning of your upkeep, roll a d4. Scry X, where X is the result.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new NetheresePuzzleWardEffect()
        ).withFlavorWord("Focus Beam"));

        // Perfect Illumination — Whenever you roll a die's highest natural result, draw a card.
        this.addAbility(new NetheresePuzzleWardTriggeredAbility());
    }

    private NetheresePuzzleWard(final NetheresePuzzleWard card) {
        super(card);
    }

    @Override
    public NetheresePuzzleWard copy() {
        return new NetheresePuzzleWard(this);
    }
}

class NetheresePuzzleWardEffect extends OneShotEffect {

    NetheresePuzzleWardEffect() {
        super(Outcome.Benefit);
        staticText = "roll a d4. Scry X, where X is the result";
    }

    private NetheresePuzzleWardEffect(final NetheresePuzzleWardEffect effect) {
        super(effect);
    }

    @Override
    public NetheresePuzzleWardEffect copy() {
        return new NetheresePuzzleWardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int roll = player.rollDice(outcome, source, game, 4);
        new ScryEffect(roll).apply(game, source);
        return true;
    }
}

class NetheresePuzzleWardTriggeredAbility extends TriggeredAbilityImpl {

    NetheresePuzzleWardTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.withFlavorWord("Perfect Illumination");
        this.setTriggerPhrase("Whenever you roll a die's highest natural result, ");
    }

    private NetheresePuzzleWardTriggeredAbility(final NetheresePuzzleWardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DIE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DieRolledEvent drEvent = (DieRolledEvent) event;
        return isControlledBy(drEvent.getTargetId())
                && drEvent.getNaturalResult() == drEvent.getSides();
    }

    @Override
    public NetheresePuzzleWardTriggeredAbility copy() {
        return new NetheresePuzzleWardTriggeredAbility(this);
    }
}
