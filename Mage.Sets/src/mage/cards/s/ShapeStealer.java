
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ShapeStealer extends CardImpl {

    public ShapeStealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // This ability triggers once for each creature blocked by or blocking Shape Stealer.
        // If multiple creatures block it, Shape Stealer's power and toughness will change for
        // each one in succession. The first trigger put on the stack will be the last to resolve,
        // so that will set Shape Stealer's final power and toughness.
        // Whenever Shape Stealer blocks or becomes blocked by a creature, change Shape Stealer's base power and toughness to that creature's power and toughness until end of turn.
        this.addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(new ShapeStealerEffect()));
    }

    private ShapeStealer(final ShapeStealer card) {
        super(card);
    }

    @Override
    public ShapeStealer copy() {
        return new ShapeStealer(this);
    }
}

class ShapeStealerEffect extends OneShotEffect {

    public ShapeStealerEffect() {
        super(Outcome.Detriment);
        this.staticText = "change {this}'s base power and toughness to that creature's power and toughness until end of turn";
    }

    public ShapeStealerEffect(final ShapeStealerEffect effect) {
        super(effect);
    }

    @Override
    public ShapeStealerEffect copy() {
        return new ShapeStealerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller == null || permanent == null) {
            return false;
        }

        ContinuousEffect effect = new SetBasePowerToughnessSourceEffect(permanent.getPower().getValue(), permanent.getToughness().getValue(), Duration.EndOfTurn, SubLayer.SetPT_7b);
        game.addEffect(effect, source);
        return true;
    }
}
