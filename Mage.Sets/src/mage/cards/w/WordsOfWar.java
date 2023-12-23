package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
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
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class WordsOfWar extends CardImpl {

    public WordsOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");


        // {1}: The next time you would draw a card this turn, Words of War deals 2 damage to any target instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WordsOfWarEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private WordsOfWar(final WordsOfWar card) {
        super(card);
    }

    @Override
    public WordsOfWar copy() {
        return new WordsOfWar(this);
    }
}

class WordsOfWarEffect extends ReplacementEffectImpl {

    WordsOfWarEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "The next time you would draw a card this turn, {this} deals 2 damage to any target instead.";
    }

    private WordsOfWarEffect(final WordsOfWarEffect effect) {
        super(effect);
    }

    @Override
    public WordsOfWarEffect copy() {
        return new WordsOfWarEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player player = game.getPlayer(targetPointer.getFirst(game, source));
            if (player != null) {
                player.damage(2, source.getSourceId(), source, game);
                this.used = true;
                discard();
                return true;
            }
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent != null) {
                permanent.damage(2, source.getSourceId(), source, game, false, true);
                this.used = true;
                discard();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used) {
            return source.isControlledBy(event.getPlayerId());
        }
        return false;
    }
}
