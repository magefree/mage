
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class BrineElemental extends CardImpl {

    public BrineElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Morph {5}{U}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{5}{U}{U}")));

        // When Brine Elemental is turned face up, each opponent skips their next untap step.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new BrineElementalEffect()));
    }

    private BrineElemental(final BrineElemental card) {
        super(card);
    }

    @Override
    public BrineElemental copy() {
        return new BrineElemental(this);
    }
}

class BrineElementalEffect extends OneShotEffect {

    public BrineElementalEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent skips their next untap step";
    }

    public BrineElementalEffect(final BrineElementalEffect effect) {
        super(effect);
    }

    @Override
    public BrineElementalEffect copy() {
        return new BrineElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId: game.getState().getPlayersInRange(controller.getId(), game)) {
                if (controller.hasOpponent(playerId, game)) {
                    game.getState().getTurnMods().add(new TurnMod(playerId, PhaseStep.UNTAP));
                }
            }
            return true;
        }
        return false;
    }
}
