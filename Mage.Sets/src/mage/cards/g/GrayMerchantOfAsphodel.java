package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GrayMerchantOfAsphodel extends CardImpl {

    public GrayMerchantOfAsphodel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Gray Merchant of Asphodel enters the battlefield, each opponent loses X life, where X is your devotion to black. You gain life equal to the life lost this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new GrayMerchantOfAsphodelEffect(), false
        ).addHint(DevotionCount.B.getHint()));
    }

    private GrayMerchantOfAsphodel(final GrayMerchantOfAsphodel card) {
        super(card);
    }

    @Override
    public GrayMerchantOfAsphodel copy() {
        return new GrayMerchantOfAsphodel(this);
    }
}

class GrayMerchantOfAsphodelEffect extends OneShotEffect {

    GrayMerchantOfAsphodelEffect() {
        super(Outcome.GainLife);
        this.staticText = "each opponent loses X life, where X is your devotion to black. "
                + "You gain life equal to the life lost this way. "
                + DevotionCount.B.getReminder();
    }

    private GrayMerchantOfAsphodelEffect(final GrayMerchantOfAsphodelEffect effect) {
        super(effect);
    }

    @Override
    public GrayMerchantOfAsphodelEffect copy() {
        return new GrayMerchantOfAsphodelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int lifeLost = DevotionCount.B.calculate(game, source, this);
        if (lifeLost == 0) {
            return true;
        }
        int totalLifeLost = game
                .getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(opponent -> opponent.loseLife(lifeLost, game, source, false))
                .sum();
        return controller.gainLife(totalLifeLost, game, source) > 0;
    }
}
