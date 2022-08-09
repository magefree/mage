
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class WidespreadPanic extends CardImpl {

    public WidespreadPanic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // Whenever a spell or ability causes its controller to shuffle their library, that player puts a card from their hand on top of their library.
        this.addAbility(new WidespreadPanicTriggeredAbility());
    }

    private WidespreadPanic(final WidespreadPanic card) {
        super(card);
    }

    @Override
    public WidespreadPanic copy() {
        return new WidespreadPanic(this);
    }
}

class WidespreadPanicTriggeredAbility extends TriggeredAbilityImpl {

    public WidespreadPanicTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WidespreadPanicEffect(), false);
        setTriggerPhrase("Whenever a spell or ability causes its controller to shuffle their library, ");
    }

    public WidespreadPanicTriggeredAbility(final WidespreadPanicTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WidespreadPanicTriggeredAbility copy() {
        return new WidespreadPanicTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LIBRARY_SHUFFLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // event.getSourceId() must be null for default shuffle like mulligan
        if (event.getPlayerId().equals(game.getControllerId(event.getSourceId()))) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }
}

class WidespreadPanicEffect extends OneShotEffect {

    public WidespreadPanicEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player puts a card from their hand on top of their library";
    }

    public WidespreadPanicEffect(final WidespreadPanicEffect effect) {
        super(effect);
    }

    @Override
    public WidespreadPanicEffect copy() {
        return new WidespreadPanicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player shuffler = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (shuffler != null) {
            if (!shuffler.getHand().isEmpty()) {
                TargetCardInHand target = new TargetCardInHand();
                target.setNotTarget(true);
                target.setTargetName("a card from your hand to put on top of your library");
                shuffler.choose(Outcome.Detriment, target, source, game);
                Card card = shuffler.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    shuffler.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, false);

                }
            }
            return true;
        }
        return false;
    }
}
