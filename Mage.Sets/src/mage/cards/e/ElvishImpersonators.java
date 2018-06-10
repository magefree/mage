
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class ElvishImpersonators extends CardImpl {

    public ElvishImpersonators(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELVES);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Elvish Impersonators enters the battlefield, roll a six-sided die twice. Its base power becomes the first result and its base toughness becomes the second result.
        this.addAbility(new AsEntersBattlefieldAbility(new ElvishImpersonatorsEffect()));
    }

    public ElvishImpersonators(final ElvishImpersonators card) {
        super(card);
    }

    @Override
    public ElvishImpersonators copy() {
        return new ElvishImpersonators(this);
    }
}

class ElvishImpersonatorsEffect extends OneShotEffect {

    public ElvishImpersonatorsEffect() {
        super(Outcome.Neutral);
        staticText = "roll a six-sided die twice. Its base power becomes the first result and its base toughness becomes the second result";
    }

    public ElvishImpersonatorsEffect(final ElvishImpersonatorsEffect effect) {
        super(effect);
    }

    @Override
    public ElvishImpersonatorsEffect copy() {
        return new ElvishImpersonatorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int firstRoll = controller.rollDice(game, 6);
            int secondRoll = controller.rollDice(game, 6);
            game.addEffect(new SetPowerToughnessSourceEffect(firstRoll, secondRoll, Duration.WhileOnBattlefield, SubLayer.SetPT_7b), source);
            return true;
        }
        return false;
    }
}
