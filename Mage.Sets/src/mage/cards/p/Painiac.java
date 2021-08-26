
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class Painiac extends CardImpl {

    public Painiac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.BRAINIAC);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, roll a six-sided die. Painiac gets +X/+0 until end of turn, where X is the result.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new PainiacEffect(), TargetController.YOU, false));
    }

    private Painiac(final Painiac card) {
        super(card);
    }

    @Override
    public Painiac copy() {
        return new Painiac(this);
    }
}

class PainiacEffect extends OneShotEffect {

    public PainiacEffect() {
        super(Outcome.Benefit);
        this.staticText = "Roll a six-sided die. {this} gets +X/+0 until end of turn, where X is the result";
    }

    public PainiacEffect(final PainiacEffect effect) {
        super(effect);
    }

    @Override
    public PainiacEffect copy() {
        return new PainiacEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(outcome, source, game, 6);
            game.addEffect(new BoostSourceEffect(amount, 0, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
