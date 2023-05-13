
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author andyfries
 */
public final class MaralenOfTheMornsong extends CardImpl {

    public MaralenOfTheMornsong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Players can't draw cards.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MaralenOfTheMornsongEffect()));

        // At the beginning of each player's draw step, that player loses 3 life, searches their library for a card, puts it into their hand, then shuffles their library.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new MaralenOfTheMornsongEffect2(), TargetController.ANY, false));

    }

    private MaralenOfTheMornsong(final MaralenOfTheMornsong card) {
        super(card);
    }

    @Override
    public MaralenOfTheMornsong copy() {
        return new MaralenOfTheMornsong(this);
    }
}

class MaralenOfTheMornsongEffect extends ContinuousRuleModifyingEffectImpl {

    public MaralenOfTheMornsongEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, false, false);
        staticText = "Players can't draw cards";
    }

    public MaralenOfTheMornsongEffect(final MaralenOfTheMornsongEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MaralenOfTheMornsongEffect copy() {
        return new MaralenOfTheMornsongEffect(this);
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

class MaralenOfTheMornsongEffect2 extends OneShotEffect {

    public MaralenOfTheMornsongEffect2() {
        super(Outcome.LoseLife);
        staticText = "that player loses 3 life, searches their library for a card, puts it into their hand, then shuffles";
    }

    public MaralenOfTheMornsongEffect2(final MaralenOfTheMornsongEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID activePlayerId = game.getActivePlayerId();
        Player player = game.getPlayer(activePlayerId);
        if (player != null) {
            player.loseLife(3, game, source, false);
            TargetCardInLibrary target = new TargetCardInLibrary();
            if (player.searchLibrary(target, source, game)) {
                player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public MaralenOfTheMornsongEffect2 copy() {
        return new MaralenOfTheMornsongEffect2(this);
    }

}
