
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author L_J
 */
public final class ElvishImpersonators extends CardImpl {

    public ElvishImpersonators(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.ELVES);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Elvish Impersonators enters the battlefield, roll a six-sided die twice. Its base power becomes the first result and its base toughness becomes the second result.
        this.addAbility(new AsEntersBattlefieldAbility(new ElvishImpersonatorsEffect()));
    }

    private ElvishImpersonators(final ElvishImpersonators card) {
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
        if (controller == null) {
            return false;
        }
        List<Integer> results = controller.rollDice(outcome, source, game, 6, 2, 0);
        int firstRoll = results.get(0);
        int secondRoll = results.get(1);
        game.addEffect(new SetBasePowerToughnessSourceEffect(firstRoll, secondRoll, Duration.WhileOnBattlefield, SubLayer.SetPT_7b, true), source);
        return true;
    }
}
