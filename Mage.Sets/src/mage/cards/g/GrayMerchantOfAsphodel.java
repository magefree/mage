
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class GrayMerchantOfAsphodel extends CardImpl {

    public GrayMerchantOfAsphodel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Gray Merchant of Asphodel enters the battlefield, each opponent loses X life, where X is your devotion to black. You gain life equal to the life lost this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new GrayMerchantOfAsphodelEffect(), 
                false));
    }

    public GrayMerchantOfAsphodel(final GrayMerchantOfAsphodel card) {
        super(card);
    }

    @Override
    public GrayMerchantOfAsphodel copy() {
        return new GrayMerchantOfAsphodel(this);
    }
}

class GrayMerchantOfAsphodelEffect extends OneShotEffect {

    public GrayMerchantOfAsphodelEffect() {
        super(Outcome.GainLife);
        this.staticText = "each opponent loses X life, where X is your devotion to black. "
                + "You gain life equal to the life lost this way. "
                + "<i>(Each {B} in the mana costs of permanents you control "
                + "counts towards your devotion to black.)</i>";
    }

    public GrayMerchantOfAsphodelEffect(final GrayMerchantOfAsphodelEffect effect) {
        super(effect);
    }

    @Override
    public GrayMerchantOfAsphodelEffect copy() {
        return new GrayMerchantOfAsphodelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int totalLifeLost = 0;
            int lifeLost = new DevotionCount(ColoredManaSymbol.B).calculate(game, source, this);
            if (lifeLost > 0) {
                for (UUID playerId : game.getOpponents(source.getControllerId())) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        totalLifeLost += opponent.loseLife(lifeLost, game, false);
                    }
                }
            }
            controller.gainLife(totalLifeLost, game, source);
            return true;
        }
        return false;
    }
}
