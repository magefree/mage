
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class IndenturedDjinn extends CardImpl {

    public IndenturedDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Indentured Djinn enters the battlefield, each other player may draw up to three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IndenturedDjinnEffect(), false));
    }

    private IndenturedDjinn(final IndenturedDjinn card) {
        super(card);
    }

    @Override
    public IndenturedDjinn copy() {
        return new IndenturedDjinn(this);
    }
}

class IndenturedDjinnEffect extends OneShotEffect {

    IndenturedDjinnEffect() {
        super(Outcome.DrawCard);
        this.staticText = "each other player may draw up to three cards";
    }

    private IndenturedDjinnEffect(final IndenturedDjinnEffect effect) {
        super(effect);
    }

    @Override
    public IndenturedDjinnEffect copy() {
        return new IndenturedDjinnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Effect effect = new DrawCardTargetEffect(StaticValue.get(3), false, true);
                    effect.setTargetPointer(new FixedTarget(playerId));
                    effect.setText(player.getLogName() + " may draw up to three cards");
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
