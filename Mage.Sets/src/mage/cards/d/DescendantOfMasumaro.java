
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class DescendantOfMasumaro extends CardImpl {

    public DescendantOfMasumaro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, put a +1/+1 counter on Descendant of Masumaro for each card in your hand, then remove a +1/+1 counter from Descendant of Masumaro for each card in target opponent's hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DescendantOfMasumaroEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private DescendantOfMasumaro(final DescendantOfMasumaro card) {
        super(card);
    }

    @Override
    public DescendantOfMasumaro copy() {
        return new DescendantOfMasumaro(this);
    }
}

class DescendantOfMasumaroEffect extends OneShotEffect {

    public DescendantOfMasumaroEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a +1/+1 counter on {this} for each card in your hand, then remove a +1/+1 counter from {this} for each card in target opponent's hand";
    }

    private DescendantOfMasumaroEffect(final DescendantOfMasumaroEffect effect) {
        super(effect);
    }

    @Override
    public DescendantOfMasumaroEffect copy() {
        return new DescendantOfMasumaroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (!controller.getHand().isEmpty()) {
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(controller.getHand().size()), true).apply(game, source);
            }
            Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (targetOpponent != null && !targetOpponent.getHand().isEmpty()) {
                sourcePermanent.removeCounters(CounterType.P1P1.getName(), targetOpponent.getHand().size(), source, game);
                game.informPlayers(controller.getLogName() + " removes " + targetOpponent.getHand().size() + " +1/+1 counters from " + sourcePermanent.getLogName());
            }
            return true;
        }
        return false;
    }
}
