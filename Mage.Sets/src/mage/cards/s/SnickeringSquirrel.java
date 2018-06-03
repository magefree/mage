
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
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

/**
 *
 * @author spjspj
 */
public final class SnickeringSquirrel extends CardImpl {

    public SnickeringSquirrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add("Squirrel");
        this.subtype.add("Advisor");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may tap Snickering Squirrel to increase the result of a die any player rolled by 1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SnickeringSquirrelEffect()));
    }

    public SnickeringSquirrel(final SnickeringSquirrel card) {
        super(card);
    }

    @Override
    public SnickeringSquirrel copy() {
        return new SnickeringSquirrel(this);
    }
}

class SnickeringSquirrelEffect extends ReplacementEffectImpl {

    SnickeringSquirrelEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may tap {this} to increase the result of a die any player rolled by 1";
    }

    SnickeringSquirrelEffect(final SnickeringSquirrelEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null && permanent.canTap() && !permanent.isTapped()) {
                if (controller.chooseUse(Outcome.AIDontUseIt, "Do you want to tap this to increase the result of a die any player rolled by 1?", null, "Yes", "No", source, game)) {
                    permanent.tap(game);
                    // ignore planar dies (dice roll amount of planar dies is equal to 0)
                    if (event.getAmount() > 0) {
                        event.setAmount(event.getAmount() + 1);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DICE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public SnickeringSquirrelEffect copy() {
        return new SnickeringSquirrelEffect(this);
    }
}
