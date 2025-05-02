
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author JRHerlehy
 */
public final class GreenbeltRampager extends CardImpl {

    public GreenbeltRampager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Greenbelt Rampager enters the battlefield, pay {E}{E}. If you can't, return Greenbelt Rampager to its owner's hand and you get {E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GreenbeltRampagerEffect(), false));
    }

    private GreenbeltRampager(final GreenbeltRampager card) {
        super(card);
    }

    @Override
    public GreenbeltRampager copy() {
        return new GreenbeltRampager(this);
    }

    private static class GreenbeltRampagerEffect extends OneShotEffect {

        GreenbeltRampagerEffect() {
            super(Outcome.Neutral);
            this.staticText = "pay {E}{E}. If you can't, return {this} to its owner's hand and you get {E}";
        }

        private GreenbeltRampagerEffect(final GreenbeltRampagerEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null) {
                return false;
            }

            if (!new PayEnergyCost(2).pay(source, game, source, source.getControllerId(), true)) {
                Permanent sourceObject = source.getSourcePermanentIfItStillExists(game);
                if (sourceObject != null) {
                    controller.moveCards(sourceObject, Zone.HAND, source, game);
                    controller.addCounters(CounterType.ENERGY.createInstance(), source.getControllerId(), source, game);
                }
            }
            return true;
        }

        @Override
        public GreenbeltRampagerEffect copy() {
            return new GreenbeltRampagerEffect(this);
        }
    }

}
