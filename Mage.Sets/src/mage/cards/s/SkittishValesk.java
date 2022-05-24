
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class SkittishValesk extends CardImpl {

    public SkittishValesk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, flip a coin. If you lose the flip, turn Skittish Valesk face down.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SkittishValeskEffect(), TargetController.YOU, false));

        // Morph {5}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{5}{R}")));
    }

    private SkittishValesk(final SkittishValesk card) {
        super(card);
    }

    @Override
    public SkittishValesk copy() {
        return new SkittishValesk(this);
    }
}

class SkittishValeskEffect extends OneShotEffect {

    public SkittishValeskEffect() {
        super(Outcome.Neutral);
        staticText = "flip a coin. If you lose the flip, turn {this} face down";
    }

    public SkittishValeskEffect(SkittishValeskEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null && !controller.flipCoin(source, game, true)) {
            return permanent.turnFaceDown(source, game, source.getControllerId());
        }
        return false;
    }

    @Override
    public SkittishValeskEffect copy() {
        return new SkittishValeskEffect(this);
    }
}
