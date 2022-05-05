
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.TuskenRaiderToken;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public final class BanthaHerd extends CardImpl {

    public BanthaHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {X}{W}{W}: Monstrosity X.
        this.addAbility(new MonstrosityAbility("{X}{W}{W}", Integer.MAX_VALUE));

        // When Batha Herd becomes monstrous, create X 1/1 white Tusken Raider tokens.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new BathaHerdEffect()));
    }

    private BanthaHerd(final BanthaHerd card) {
        super(card);
    }

    @Override
    public BanthaHerd copy() {
        return new BanthaHerd(this);
    }
}

class BathaHerdEffect extends OneShotEffect {

    public BathaHerdEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create X 1/1 white Tusken Raider tokens";
    }

    public BathaHerdEffect(final BathaHerdEffect effect) {
        super(effect);
    }

    @Override
    public BathaHerdEffect copy() {
        return new BathaHerdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        int xValue = ((BecomesMonstrousSourceTriggeredAbility) source).getMonstrosityValue();

        return new CreateTokenEffect(new TuskenRaiderToken(), xValue).apply(game, source);
    }
}
