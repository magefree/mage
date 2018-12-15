package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public final class MindMaggots extends CardImpl {

    public MindMaggots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Mind Maggots enters the battlefield, discard any number of creature cards. 
        // For each card discarded this way, put two +1/+1 counters on Mind Maggots.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MindMaggotsEffect()));

    }

    public MindMaggots(final MindMaggots card) {
        super(card);
    }

    @Override
    public MindMaggots copy() {
        return new MindMaggots(this);
    }
}

class MindMaggotsEffect extends OneShotEffect {

    MindMaggotsEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "discard any number of creature cards. For each card discarded this way, put two +1/+1 counters on {this}";
    }

    MindMaggotsEffect(final MindMaggotsEffect effect) {
        super(effect);
    }

    @Override
    public MindMaggotsEffect copy() {
        return new MindMaggotsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int numToDiscard = controller.getAmount(0,
                    controller.getHand().getCards(new FilterCreatureCard(), game).size(),
                    "Discard how many creature cards?",
                    game);
            TargetCardInHand target = new TargetCardInHand(numToDiscard, new FilterCreatureCard());
            if (controller.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                for (UUID targetId : target.getTargets()) {
                    Card card = game.getCard(targetId);
                    if (card != null
                            && controller.discard(card, source, game)) {
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)).apply(game, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
